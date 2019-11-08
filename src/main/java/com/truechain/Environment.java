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

//    public static BigDecimal defaultGasPrice = BigDecimal.valueOf(1);

    public static BigInteger GasLimitOfTransaction = new BigInteger("21000");
    public static BigInteger GasLimitOfContract = new BigInteger("400000");
    public static BigInteger GasPrice = new BigInteger("1000000");//1 Gwei

    /**
     * 有true
     * 私钥 BF6615CBE64EC4FBCAA9EE9B98968BB89615E17FE67759BD814D06B784EF50B3
     * 地址 0x32c2ca65cf859327bc8eaaa7981f70dbcbf810f2
     * <p>
     * <p>
     * 有true
     * "3B8D6DAD67F7D3C2D339AA90BC600DA39675F32F89D52C3B5BC5F4908DB651B2"
     * 地址 0x73dbc7712E8578827ce84fB8926A8D6c4480205a
     * <p>
     * er20代币合约地址
     * 0xd912dB46B194eF6cf6DfA90Da4884D02FF1F7fa5
     * 地址 0x73dbc7712E8578827ce84fB8926A8D6c4480205a 有该erc20代币
     */

    public static String fromPrivateKey = "BF6615CBE64EC4FBCAA9EE9B98968BB89615E17FE67759BD814D06B784EF50B3";
    public static String fromAddress = "0x32c2ca65cf859327bc8eaaa7981f70dbcbf810f2";

    public static String paymentPrivatekey = "3B8D6DAD67F7D3C2D339AA90BC600DA39675F32F89D52C3B5BC5F4908DB651B2";
    public static String paymentAddress = "0x73dbc7712e8578827ce84fb8926a8d6c4480205a";
    public static String toAddress = "0x73dbc7712e8578827ce84fb8926a8d6c4480205a";

    //er20代币合约地址
    public static String contractAddress = "0xd912dB46B194eF6cf6DfA90Da4884D02FF1F7fa5";
//    public static String contractAddress = "0x735bCe5ecc8455Eb9Bf8270aA138ce05E069b4c1";
    public static String emptyAddress = "0x0000000000000000000000000000000000000000";

    public static String TRANSACTION_ERROR = "return error message: ";

}
