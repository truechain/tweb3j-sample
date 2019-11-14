# tweb3j-sample
tweb3 for java 样例程序 (基于tweb3j 4.5.0)   
环境 idea maven  
运行前提 需要有一个开启RPC或者IPC服务的truehain节点

从github中下载tweb3j的使用示例 [tweb3j-sample](https://github.com/truechain/tweb3j-sample) , 
如果需要使用tweb3j的代付功能，

1 需要将[org.tweb3j.jar](https://github.com/truechain/tweb3j/tree/master/org.tweb3j.jar)处下载tcore-3.3.1.jar和tcrypto-3.3.1.jar
2 从本地maven仓库的所在文件夹处，如我的目录为Users/name/maven_repository
![](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/img/1.png)

将第一步中下载的tcore-3.3.1.jar和tcrypto-3.3.1.jar分别重命名为core-4.5.0.jar和crypto-4.5.0.jar
覆盖maven中已下载的对应目录下文件的包
![](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/img/2.png)

- [Environment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/Environment.java) 
配置交易相关信息，私钥管理

- [QuickStart](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/QuickStart.java) 
快速开始:判断程序能否连接到truechain网络 rpc节点

- [AddressUtils](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/account/AddressUtils.java) 
私钥地址合法性校验

- [TransactionClient](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TransactionClient.java) 
true转账相关接口

- [TransactionClient](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TransactionClient.java) 
true转账相关接口

- [TokenClient](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TokenClient.java) 
token代币相关查询及转账

- [TransactionClientWithPayment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TransactionClientWithPayment.java) token代币相关查询及转账
true转账,gas费代付相关接口

- [TokenClientWithPayment](https://github.com/truechain/tweb3j-sample/blob/master/src/main/java/com/truechain/TokenClientWithPayment.java) eth转账相关接口
token代币转账，gas费代付相关接口