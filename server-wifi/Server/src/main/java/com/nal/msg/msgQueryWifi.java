package com.nal.msg;

public class msgQueryWifi extends msg{
	public String var = null;

	public msgQueryWifi(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,32);
		var = byte2string(bb);
	}
	public msgQueryWifi(String var) {
		this.var = var;
	}

	public byte[] toByte() {
        byte[] bb = new byte[32];
        byte[] b =null;
        b = string2byte(var);
        System.arraycopy(b,0,bb,0,b.length);
		return bb;
	}
}
