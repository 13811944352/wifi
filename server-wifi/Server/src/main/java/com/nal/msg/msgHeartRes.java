package com.nal.msg;

import java.lang.reflect.Array;

public class msgHeartRes extends msg{
	public int result;
	public int sec;

	public msgHeartRes() {
	}

	public byte[] toByte() {
		byte[] bb = new byte[8];
        byte[] b =null;
        b =  int2byte(result);
        System.arraycopy(b,0,bb,0,4);
        b =  int2byte(sec);
        System.arraycopy(b,0,bb,4,4);
		return bb;
	}

}
