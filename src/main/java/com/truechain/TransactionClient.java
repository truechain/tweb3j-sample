package com.truechain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.RawTransactionManager;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class TransactionClient {
    private static Logger logger = LoggerFactory.getLogger(TransactionClient.class);


    /**
     * @param args
     */
    public static void main(String[] args) {
        getBalanceWithPrivateKey(Environment.toPrivatekey);
        getBalanceWithPrivateKey(Environment.paymentPrivatekey);
        getBalanceWithPrivateKey(Environment.fromPrivateKey);
    }

    /**
     * 获取余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    public static BigInteger getBalance(String address) {
        BigInteger balance = new BigInteger("0");
        try {
            EthGetBalance ethGetBalance = Environment.web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            if (ethGetBalance != null) {
                balance = ethGetBalance.getBalance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("address= " + address + ", balance= " + balance + "wei");
        return balance;
    }

    /**
     * 通过私钥查询对应地址的余额
     *
     * @param privatekey
     * @return
     */
    public static BigInteger getBalanceWithPrivateKey(String privatekey) {
        BigInteger balance = null;
        String address = "";
        try {
            Credentials credentials = Credentials.create(privatekey);
            address = credentials.getAddress();
            balance = getBalance(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }


    /**
     * @param toAddress       接受者地址
     * @param from_privateKey 发送者私钥字符串形式
     */
    public static void sendTransaction(String toAddress, String from_privateKey) {
        try {
            Credentials credentials = Credentials.create(from_privateKey);
            System.out.println("from address: " + credentials.getAddress());
            BigInteger value = new BigInteger("1");
            RawTransactionManager rawTransactionManager = new RawTransactionManager(
                    Environment.web3j, credentials, Environment.CHAINID
            );
            //(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            EthSendTransaction ethSendTransaction = rawTransactionManager.sendTransaction(Environment.GasPrice, Environment.GasLimitOfTransaction,
                    toAddress, "", value);
            if (ethSendTransaction.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethSendTransaction.getError().getMessage());
            }
            String transactionHash = ethSendTransaction.getTransactionHash();
            System.out.println("transactionHash------------------->" + transactionHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于离线生成发送者签名交易后的字符串，其中包含发送者签名信息，
     * 常用于发送给第三方直接用于发送交易
     *
     * @param toAddress
     * @param value           转账金额，单位为wei
     * @param from_privateKey
     * @return
     */
    public static String genRawTransaction(String toAddress, BigInteger value, String from_privateKey) {
        Credentials credentials = Credentials.create(from_privateKey);
        String fromAddress = credentials.getAddress();
        System.out.println("from address: " + fromAddress);
        BigInteger nonce = getTransactionNonce(fromAddress);
        RawTransaction rawTransaction =
                RawTransaction.createEtherTransaction(nonce, Environment.GasPrice, Environment.GasLimitOfTransaction, toAddress, value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Environment.CHAINID, credentials);
        String hexMessage = Numeric.toHexString(signedMessage);
        return hexMessage;
    }


    /**
     * 发送交易
     *
     * @param hexValue 交易签名后的字符串形式
     *                 配合genRawTransaction一起使用
     */
    public static void sendRawTransaction(String hexValue) {
        try {
            EthSendTransaction ethSendTransaction = Environment.web3j.ethSendRawTransaction(hexValue).send();
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction.getError() != null) {
                logger.error(Environment.TRANSACTION_ERROR + ethSendTransaction.getError().getMessage());
            }
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成一个普通交易对象
     *
     * @param fromAddress 放款方
     * @param toAddress   收款方
     * @param nonce       交易序号
     * @param gasPrice    gas 价格
     * @param gasLimit    gas 数量
     * @param value       金额
     * @return 交易对象
     */
    public static Transaction makeTransaction(String fromAddress, String toAddress,
                                              BigInteger nonce, BigInteger gasPrice,
                                              BigInteger gasLimit, BigInteger value) {
        Transaction transaction;
        transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
        return transaction;
    }

    /**
     * 获取普通交易的gas上限
     *
     * @param transaction 交易对象
     * @return gas 上限
     */
    public static BigInteger getTransactionGasLimit(Transaction transaction) {
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            EthEstimateGas ethEstimateGas = Environment.web3j.ethEstimateGas(transaction).send();
            gasLimit = ethEstimateGas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasLimit;
    }

    /**
     * 获取账号交易次数 nonce
     *
     * @param address 钱包地址
     * @return nonce
     */
    public static BigInteger getTransactionNonce(String address) {
        BigInteger nonce = BigInteger.ZERO;
        try {
            EthGetTransactionCount ethGetTransactionCount = Environment.web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonce;
    }

    /**
     * 发送一个普通交易
     *
     * @return 交易 Hash
     */
//    private static String sendTransaction() {
//        String password = "yzw";
//        BigInteger unlockDuration = BigInteger.valueOf(60L);
//        BigDecimal amount = new BigDecimal("0.01");
//        String txHash = null;
//        try {
//            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, password, unlockDuration).send();
//            if (personalUnlockAccount.accountUnlocked()) {
//                BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
//                Transaction transaction = makeTransaction(fromAddress, toAddress,
//                        null, null, null, value);
//                //不是必须的 可以使用默认值
//                BigInteger gasLimit = getTransactionGasLimit(transaction);
//                //不是必须的 缺省值就是正确的值
//                BigInteger nonce = getTransactionNonce(fromAddress);
//                //该值为大部分矿工可接受的gasPrice
//                BigInteger gasPrice = Convert.toWei(defaultGasPrice, Convert.Unit.GWEI).toBigInteger();
//                transaction = makeTransaction(fromAddress, toAddress,
//                        nonce, gasPrice,
//                        gasLimit, value);
//                EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
//                txHash = ethSendTransaction.getTransactionHash();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("tx hash " + txHash);
//        return txHash;
//    }

    //使用 web3j.ethSendRawTransaction() 发送交易 需要用私钥自签名交易 详见ColdWallet.java
}
