package com.android.nal.service.proxy;

public class msgHead extends msg{
	//static public int MAGIC = 0x1a2b3c4d;
	//byte[] mB ;//= new byte;
	public int magic;
	public int version; 
	public int type;
	public int command;
	public int len; 

	public msgHead(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte4(b,0);
		magic = byte2int(bb);
		bb = getByte4(b,4);
		version = byte2int(bb);
		bb = getByte4(b,8);
		type = byte2int(bb);
		bb = getByte4(b,12);
		command= byte2int(bb);
		bb = getByte4(b,16);
		len = byte2int(bb);
	}

	public msgHead() {
		;
	}

	public byte[] toByte() {
		byte[] bb = new byte[20];
		byte[] b =null;
		b =  int2byte(magic);
		System.arraycopy(b,0,bb,0,4);
		b =  int2byte(version);
		System.arraycopy(b,0,bb,4,4);
		b =  int2byte(type);
		System.arraycopy(b,0,bb,8,4);
		b =  int2byte(command);
		System.arraycopy(b,0,bb,12,4);
		b =  int2byte(len);
		System.arraycopy(b,0,bb,16,4);
		return bb;
	}
		
	

}
