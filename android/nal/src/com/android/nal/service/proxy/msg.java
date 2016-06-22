package com.android.nal.service.proxy;

public class msg{
	static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位   
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位   
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位   
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。   
		return targets;
	}
	public static int  byte2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)| ((res[2] << 24) >>> 8) | (res[3] << 24);   
		return targets;
	}

	static String byte2string(byte[] res) {
		try {
			return new String(res,"UTF-8");
		} catch(Exception e) {
			return null;
		}
	}
		
	static byte[] string2byte(String res) {
		//return res.getBytes("UTF-8");
		return res.getBytes();
	}

	static byte[] getByte4(byte[] b,int index) {
/*
		byte[] bb = new byte[4];
		for(int i = 0;i<4;i++)
			bb[i] = b[index+i];
		return bb;
*/
		return getByte(b,index,4);
	}

	static byte[] getByte32(byte[] b,int index) {
/*
		byte[] bb = new byte[32];
		for(int i = 0;i<32;i++)
			bb[i] = b[index+i];
		return bb;
*/
		return getByte(b,index,32);
	}

	static byte[] getByte(byte[] b,int index,int len) {
		byte[] bb = new byte[len];
		int min = len;
		if(min > b.length)
			min = b.length;
		for(int i = 0;i<min;i++)
			bb[i] = b[index+i];
		return bb;

	}
}
