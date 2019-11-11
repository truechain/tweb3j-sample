package com.truechain;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTrueTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于TERC-20的代币
 */
public class TokenClientWithPayment {
    public static void main(String[] args) throws Exception {
        System.out.println(TokenClient.getTokenBalance(Environment.fromAddress, Environment.contractAddress));

        sendTokenTransactionWithPayment(Environment.contractAddress, Environment.toAddress,
                 Environment.fromPrivateKey,Environment.paymentPrivatekey);
    }


    /**
     * TERC-20代币转账
     *
     * @param contractAddress 代币合约地址
     * @param toAddress       接受者地址
     * @param from_privateKey 发送者私钥
     */
    public static void sendTokenTransactionWithPayment(String contractAddress, String toAddress,
                                                       String from_privateKey, String payment_privateKey) {
        try {
            Credentials from_credentials = Credentials.create(from_privateKey);
            String from_address = from_credentials.getAddress();
            Credentials payment_credentials = Credentials.create(payment_privateKey);
            String payment_address = payment_credentials.getAddress();
            System.out.println("payment_credentials=" + payment_credentials + ", payment_address=" + payment_address);
            BigInteger amount = new BigInteger("10"); //TERC-20代币转账数量,单位为wei
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

            TrueRawTransaction trueRawTransaction = TrueRawTransaction.createTransaction(
                    nonce,
                    Environment.GasPrice,
                    Environment.GasLimitOfContract,
                    contractAddress,
                    BigInteger.ZERO,
                    data,
                    BigInteger.ZERO,
                    payment_address);
            byte[] signedMessage = TrueTransactionEncoder.signMessage_fromAndPayment(trueRawTransaction, Environment.CHAINID, from_credentials, payment_credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTrueTransaction ethSendTransaction = Environment.web3j.ethSendTrueRawTransaction(hexValue).sendAsync().get();
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction.getError() != null) {
                System.out.println(Environment.TRANSACTION_ERROR + ethSendTransaction.getError().getMessage());
            }
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
