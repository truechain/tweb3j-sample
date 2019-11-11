package com.truechain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class TransactionClientWithPayment {
    private static Logger logger = LoggerFactory.getLogger(TransactionClientWithPayment.class);


    public static void main(String[] args) {
        PaymentTransaction_sample1();
        PaymentTransaction_sample2();
    }

    public static void PaymentTransaction_sample1() {
        try {
            TransactionClient.getBalance(Environment.paymentAddress);
            sendPaymentTransaction(Environment.toAddress,
                    Environment.fromPrivateKey,
                    Environment.paymentPrivatekey);
            Thread.sleep(3000);//等待几秒，待交易执行完成查询代付地址的余额对比
            TransactionClient.getBalance(Environment.paymentAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PaymentTransaction_sample2() {
        try {
            TransactionClient.getBalance(Environment.paymentAddress);
            //发送者签名
            String rawTransaction = genTrueRawTransaction(Environment.toAddress, Environment.paymentAddress, Environment.fromPrivateKey);
            //代付者签名
            String trueRawTransaction = SignRawTransactionWithPayment(rawTransaction, Environment.paymentPrivatekey);
            //发送代付交易
            sendPaymentTransaction(trueRawTransaction);
            Thread.sleep(3000);//等待几秒，待交易执行完成查询代付地址的余额对比
            TransactionClient.getBalance(Environment.paymentAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param toAddress          接受者地址
     * @param from_privateKey    发送者私钥字符串形式
     * @param payment_privateKey 代付者私钥字符串形式
     */
    public static void sendPaymentTransaction(String toAddress, String from_privateKey, String payment_privateKey) {
        try {
            //发送者私钥
            Credentials from_credentials = Credentials.create(from_privateKey);
            String from_address = from_credentials.getAddress();

            //代付者私钥
            Credentials payment_credentials = Credentials.create(payment_privateKey);
            String payment_address = payment_credentials.getAddress();
            System.out.println("from address: " + from_address + "payment address: " + payment_address);

            BigInteger nonce = TransactionClient.getTransactionNonce(from_address);
            TrueRawTransaction trueRawTransaction = TrueRawTransaction.createEtherTransaction(
                    nonce,
                    Environment.GasPrice,
                    Environment.GasLimitOfTransaction,
                    toAddress,
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    payment_address);

            byte[] signedMessage = TrueTransactionEncoder.signMessage_fromAndPayment(
                    trueRawTransaction, Environment.CHAINID, from_credentials, payment_credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTrueTransaction ethSendTrueTransaction = Environment.web3j.ethSendTrueRawTransaction(hexValue).send();
            if (ethSendTrueTransaction.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethSendTrueTransaction.getError());
            }
            String txHash = ethSendTrueTransaction.getTransactionHash();
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送者签名
     * 用于离线生成发送者签名交易后的十六进制字符串，其中包含发送者签名信息，
     * 常用于发送给第三方直接用于发送交易
     *
     * @param toAddress
     * @param from_privateKey
     * @return
     */
    public static String genTrueRawTransaction(String toAddress, String paymentAddress, String from_privateKey) {
        try {
            Credentials credentials = Credentials.create(from_privateKey);
            String fromAddress = credentials.getAddress();
            System.out.println("from address: " + fromAddress);

            BigInteger nonce = TransactionClient.getTransactionNonce(fromAddress);
            BigInteger value = new BigInteger("0");//转账金额



            TrueRawTransaction trueRawTransaction = TrueRawTransaction.createTransaction(
                    nonce,
                    Environment.GasPrice,
                    Environment.GasLimitOfTransaction,
                    toAddress,
                    value,
                    "",
                    BigInteger.ZERO,
                    paymentAddress);

            byte[] signedMessage = TrueTransactionEncoder.signMessage(trueRawTransaction, Environment.CHAINID, credentials);
            String hexMessage = Numeric.toHexString(signedMessage);
            return hexMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代付者签名
     *
     * @param trueRawTransaction 发送者对交易签名后的十六进制字节码
     * @param payment_privateKey 代付者私钥
     * @return
     */
    public static String SignRawTransactionWithPayment(String trueRawTransaction, String payment_privateKey) {
        try {
            Credentials payment_credentials = Credentials.create(payment_privateKey);

            SignedTrueRawTransaction signtrueRawTransaction = (SignedTrueRawTransaction) TrueTransactionDecoder.decode(trueRawTransaction);
            Sign.SignatureData decode_signatureData = signtrueRawTransaction.getSignatureData();
            TrueRawTransaction decode_trueRawTransaction = new TrueRawTransaction(signtrueRawTransaction);

            byte[] signedMessageWithPayment = TrueTransactionEncoder.signMessage_payment(decode_trueRawTransaction, decode_signatureData,
                    Environment.CHAINID, payment_credentials);
            String hexMessageWithPayment = Numeric.toHexString(signedMessageWithPayment);
            return hexMessageWithPayment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendPaymentTransaction(String trueRawTransactionWithPayment) {
        try {
            EthSendTrueTransaction ethSendTrueTransaction = Environment.web3j.ethSendTrueRawTransaction(trueRawTransactionWithPayment).send();

            if (ethSendTrueTransaction.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethSendTrueTransaction.getError().getMessage());
            }
            String txHash = ethSendTrueTransaction.getTransactionHash();
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
