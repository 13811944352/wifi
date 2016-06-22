package com.android.nal.service.proxy;

public class msgHeart extends msg{
	public String devId = null;

	public msgHeart(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte32(b,0);
		devId= byte2string(bb);
		//len = getByte4(b,20);
		//version = byte2int(bb);
	}
	public msgHeart() {
	}

	public byte[] toByte() {
        byte[] bb = new byte[32];
        byte[] b =null;
        b = string2byte(devId);
        System.arraycopy(b,0,bb,0,b.length);
		return bb;
	}


	//magHead(byte[20] b) {
		
	//} 

}
