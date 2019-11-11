package com.truechain;

import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.io.IOException;


/**
 * 快速开始
 */
public class QuickStart {

	public static void main(String[] args) {
		Web3ClientVersion web3ClientVersion = null;
		try {
			web3ClientVersion = Environment.web3j.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
