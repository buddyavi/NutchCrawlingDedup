package com.usc.crawler.filter;

import java.security.MessageDigest;

public class Md5Checker {

	public static void main(String args[]) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		String st1 = "avi";
		String st2 = "avi";
		String st3 = "asadads";
		byte[] data1 = st1.getBytes();
		byte[] data2 = st2.getBytes();
		byte[] data3 = st3.getBytes();

		md.update(data1);

		byte[] mdbytes = md.digest();

	}

}
