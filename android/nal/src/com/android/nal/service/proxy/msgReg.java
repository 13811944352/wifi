package com.android.nal.service.proxy;

public class msgReg extends msg{
	public String devName = null;
	public String devPwd = null; 
	public String devId = null;
	public int devType;
	public int devFacId;
	public String FacName = null;
	public int softVer;

	//byte[4] 
    public byte[] toByte() {
        byte[] bb = new byte[140];
        byte[] b =null;
        b =  string2byte(devName);
        System.arraycopy(b,0,bb,0,b.length);
        b =  string2byte(devPwd);
        System.arraycopy(b,0,bb,32,b.length);
        b =  string2byte(devId);
        System.arraycopy(b,0,bb,64,b.length);
        b =  int2byte(devType);
        System.arraycopy(b,0,bb,96,b.length);
        b =  int2byte(devFacId);
        System.arraycopy(b,0,bb,100,b.length);
        b =  string2byte(FacName);
        System.arraycopy(b,0,bb,104,b.length);
        b =  int2byte(softVer);
        System.arraycopy(b,0,bb,136,b.length);
        return bb; 
    } 

	public msgReg() {
		devName = "";
		devPwd = "";//byte2string(bb);
		devId = "";//byte2string(bb);
		devType = 0;//byte2int(bb);
		devFacId = 0;//byte2int(bb);
		FacName = "";//byte2string(bb);
		softVer = 0;//byte2int(bb);
	}


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
