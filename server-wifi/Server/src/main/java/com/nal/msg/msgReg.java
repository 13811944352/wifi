package com.nal.msg;

public class msgReg extends msg{
	public String devName = null;
	public String devPwd = null; 
	public String devId = null;
	public int devType;
	public int devFacId;
	public String FacName = null;
	public int softVer;

	//byte[4] 

	public msgReg(byte[] b) {
		byte[] bb = null;// getByte4(b,0);
		bb = getByte32(b,0);
		devName = byte2string(bb);
		bb = getByte32(b,32);
		devPwd = byte2string(bb);
		bb = getByte32(b,64);
		devId = byte2string(bb);
		bb = getByte4(b,96);
		devType = byte2int(bb);
		bb = getByte4(b,100);
		devFacId = byte2int(bb);
		bb = getByte32(b,104);
		FacName = byte2string(bb);
		bb = getByte4(b,136);
		softVer = byte2int(bb);
		//len = getByte4(b,20);
		//version = byte2int(bb);
	}

	//magHead(byte[20] b) {

	//magHead(byte[20] b) {
		
	//} 

}
