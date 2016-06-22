package com.android.nal.service.proxy;

public class msgQueryRes extends msg{
	public String var = null;
	public int type = 0;
	public String value = null;
	public int error = 0;

	public msgQueryRes(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,32);
		var = byte2string(bb);
		bb = getByte(b,0+32,4);
		type = byte2int(bb);
		bb = getByte(b,0+32+4,16);
		value = byte2string(bb);
		bb = getByte(b,0+32+4+16,4);
		error = byte2int(bb);
		//version = byte2int(bb);
	}
	public msgQueryRes() {
	}

	public byte[] toByte() {
        byte[] bb = new byte[0+32+4+16+4];
        byte[] b =null;
        b = string2byte(var);
        System.arraycopy(b,0,bb,0,b.length);
		b = int2byte(type);
        System.arraycopy(b,0,bb,32,b.length);
		b = string2byte(value);
        System.arraycopy(b,0,bb,0+32+4,b.length);
		b = int2byte(error);
        System.arraycopy(b,0,bb,0+32+4+16,b.length);
		return bb;
	}

}
