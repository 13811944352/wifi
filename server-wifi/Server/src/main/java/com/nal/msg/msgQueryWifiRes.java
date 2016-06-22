package com.nal.msg;

public class msgQueryWifiRes extends msg{
	public String var = null;
	public int   type = 0;
	public String value;

	public msgQueryWifiRes(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,32);
		var = byte2string(bb);
		bb = getByte(b,32,4);
		type = byte2int(bb);
		bb = getByte(b,0+32+4,16);
		value = byte2string(bb);
	}
	public msgQueryWifiRes(String var) {
		this.var = var;
	}

	public byte[] toByte() {
        byte[] bb = new byte[32+4+16];
        byte[] b =null;
        b = string2byte(var);
        System.arraycopy(b,0,bb,0,b.length);
		b = int2byte(type);
        System.arraycopy(b,0,bb,32,b.length);
        b = string2byte(value);
        System.arraycopy(b,0,bb,32+4,b.length);
		return bb;
	}
}
