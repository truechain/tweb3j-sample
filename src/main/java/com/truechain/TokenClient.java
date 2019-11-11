package com.truechain;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 基于TERC-20的代币
 */
public class TokenClient {
    public static void main(String[] args) throws  Exception{
//		System.out.println(getTokenBalance(Environment.fromAddress, Environment.contractAddress));
//        System.out.println(getTokenName(Environment.contractAddress));
//        System.out.println(getTokenDecimals(Environment.contractAddress));
//        System.out.println(getTokenSymbol(Environment.contractAddress));
//        System.out.println(getTokenTotalSupply(Environment.contractAddress));
//        System.out.println(getTokenBalance(Environment.fromAddress, Environment.contractAddress));
//		sendTokenTransaction(Environment.contractAddress, Environment.toAddress, Environment.fromPrivateKey);
//		Thread.sleep(1000);
//        System.out.println(getTokenBalance(Environment.fromAddress, Environment.contractAddress));
    }

    /**
     * 查询TERC-20代币余额
     */
    public static BigInteger getTokenBalance(String fromAddress, String contractAddress) {

        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(fromAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);

        BigInteger balanceValue = BigInteger.ZERO;
        try {
            EthCall ethCall = Environment.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            if (ethCall.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethCall.getError().getMessage());
            }
            balanceValue = (BigInteger) results.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balanceValue;
    }

    /**
     * 查询TERC-20代币名称
     *
     * @param contractAddress
     * @return
     */
    public static String getTokenName(String contractAddress) {
        String methodName = "name";
        String name = null;
        String fromAddr = Environment.emptyAddress;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        try {
            EthCall ethCall = Environment.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            name = results.get(0).getValue().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 查询TERC-20代币符号
     *
     * @param contractAddress
     * @return
     */
    public static String getTokenSymbol(String contractAddress) {
        String methodName = "symbol";
        String symbol = null;
        String fromAddr = Environment.emptyAddress;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        try {
            EthCall ethCall = Environment.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            symbol = results.get(0).getValue().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return symbol;
    }

    /**
     * 查询TERC-20代币精度
     *
     * @param contractAddress
     * @return
     */
    public static int getTokenDecimals(String contractAddress) {
        String methodName = "decimals";
        String fromAddr = Environment.emptyAddress;
        int decimal = 0;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        try {
            EthCall ethCall = Environment.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            decimal = Integer.parseInt(results.get(0).getValue().toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return decimal;
    }

    /**
     * 查询TERC-20代币发行总量
     *
     * @param contractAddress
     * @return
     */
    public static BigInteger getTokenTotalSupply(String contractAddress) {
        String methodName = "totalSupply";
        String fromAddr = Environment.emptyAddress;
        BigInteger totalSupply = BigInteger.ZERO;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        try {
            EthCall ethCall = Environment.web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            totalSupply = (BigInteger) results.get(0).getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return totalSupply;
    }

    /**
     * TERC-20代币转账
     *
     * @param contractAddress 代币合约地址
     * @param toAddress       接受者地址
     * @param from_privateKey 发送者私钥
     */
    public static void sendTokenTransaction(String contractAddress, String toAddress, String from_privateKey) {
        try {
            Credentials from_credentials = Credentials.create(from_privateKey);
            String from_address = from_credentials.getAddress();
            BigInteger amount = new BigInteger("10");
            String methodName = "transfer";
            List<Type> inputParameters = new ArrayList<>();
            List<TypeReference<?>> outputParameters = new ArrayList<>();

            Address tAddress = new Address(toAddress);

            Uint256 value = new Uint256(amount);
            inputParameters.add(tAddress);
            inputParameters.add(value);

            TypeReference<Bool> typeReference = new TypeReference<Bool>() {
            };
            outputParameters.add(typeReference);
            Function function = new Function(methodName, inputParameters, outputParameters);
            String data = FunctionEncoder.encode(function);

            EthGetTransactionCount ethGetTransactionCount = Environment.web3j
                    .ethGetTransactionCount(from_address, DefaultBlockParameterName.PENDING).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, Environment.GasPrice,
                    Environment.GasLimitOfContract, contractAddress, data);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Environment.CHAINID, from_credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = Environment.web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethSendTransaction.getError());
            }
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
