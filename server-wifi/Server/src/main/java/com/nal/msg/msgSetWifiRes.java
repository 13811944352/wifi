package com.nal.msg;

public class msgSetWifiRes extends msg{
	public int   result = 0;

	public msgSetWifiRes(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,4);
		result = byte2int(bb);
	}
	public msgSetWifiRes(int result ) {
		this.result= result;
	}

	public byte[] toByte() {
        byte[] bb = new byte[4];
        byte[] b =null;
		b = int2byte(result);
        System.arraycopy(b,0,bb,0,b.length);
		return bb;
	}
}
