package com.android.nal.service.proxy;

import java.lang.reflect.Array;

public class msgHeartRes extends msg{
	public int result;
	public int sec;

	public msgHeartRes(byte[] b) {
        byte[] bb = null;// getByte4(b,0);
        bb = getByte4(b,0);
        result = byte2int(bb);
        bb = getByte4(b,4);
        sec = byte2int(bb);
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
