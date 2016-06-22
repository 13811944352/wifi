package com.nal.msg;

public class msgSetRes extends msg{
	public int   result = 0;

	public msgSetRes(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte(b,0,4);
		result = byte2int(bb);
	}
	public msgSetRes(int result ) {
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
