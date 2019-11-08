package com.truechain;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * 运行配置项
 */
public class Environment {
    public static String RPC_URL = "https://rpc.truescan.net";
    //	public static String RPC_URL_TestNet = "https://rpc.truescan.net/testnet";
    public static Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
    public static int CHAINID = 19330; //truechain 主网chainId


    public static BigInteger GasLimitOfTransaction = new BigInteger("21000");
    public static BigInteger GasLimitOfContract = new BigInteger("400000");
    public static BigInteger GasPrice = new BigInteger("1000000");//1 Gwei



    public static String fromPrivateKey = "fromPrivateKeyStr";
    public static String fromAddress = "fromAddress";

    public static String paymentPrivatekey = "paymentPrivatekeyStr";
    public static String paymentAddress = "paymentAddress";
    public static String toAddress = "toAddress";

    //er20代币合约地址
    public static String contractAddress = "contractAddress";
    public static String emptyAddress = "0x0000000000000000000000000000000000000000";
    public static String TRANSACTION_ERROR = "return error message: ";

}
