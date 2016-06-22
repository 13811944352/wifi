package com.nal.msg;

public class msgHeart extends msg{
	public String devId = null;

	public msgHeart(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte32(b,0);
		devId= byte2string(bb);
		//len = getByte4(b,20);
		//version = byte2int(bb);
	}

	//magHead(byte[20] b) {

	//magHead(byte[20] b) {
		
	//} 

}
