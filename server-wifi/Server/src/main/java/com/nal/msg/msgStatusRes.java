package com.nal.msg;


public class msgStatusRes extends msg{
	public int result;

	public msgStatusRes() {
	}

	public byte[] toByte() {
		byte[] bb = new byte[4];
        byte[] b =null;
        b =  int2byte(result);
        System.arraycopy(b,0,bb,0,4);
		return bb;
	}

}
