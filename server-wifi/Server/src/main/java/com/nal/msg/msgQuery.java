package com.nal.msg;

public class msgQuery extends msg{
	public String devId = null;
	public String var = null;
	public String uname = null;

	public msgQuery(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,32);
		devId = byte2string(bb);
		bb = getByte(b,32,32);
		var = byte2string(bb);
		bb = getByte(b,32+32,32);
		uname = byte2string(bb);
	}
	public msgQuery(String did,String var) {
		this.devId = did;
		this.var = var;
	}

	public byte[] toByte() {
        byte[] bb = new byte[32+32+32];
        byte[] b =null;
        b = string2byte(devId);
        System.arraycopy(b,0,bb,0,b.length);
        b = string2byte(var);
        System.arraycopy(b,0,bb,32,b.length);
        b = string2byte(uname);
        System.arraycopy(b,0,bb,32+32,b.length);
		return bb;
	}
}
