# tweb3j-sample
tweb3 for java 样例程序 (基于tweb3j 4.5.0)   
环境 idea maven  
运行前提 需要有一个开启RPC或者IPC服务的truehain节点


- [Environment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/Environment.java) 
配置交易相关信息，私钥管理

- [QuickStart](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/QuickStart.java) 
快速开始:判断程序能否连接到truechain网络 rpc节点

- [TransactionClient](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TransactionClient.java) 
true转账相关接口

- [TokenClient](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TokenClient.java) 
token代币相关查询及转账

- [TransactionClientWithPayment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TransactionClientWithPayment.java) token代币相关查询及转账
true转账,gas费代付相关接口

- [TokenClientWithPayment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TokenClientWithPayment.java) eth转账相关接口
token代币转账，gas费代付相关接口