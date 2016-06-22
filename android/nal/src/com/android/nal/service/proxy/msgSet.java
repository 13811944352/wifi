package com.android.nal.service.proxy;

public class msgSet extends msg{
    public String var = null;
    public int   type = 0;
    public String value;

	public String uname;
	public String did;

    public msgSet(byte[] b) {
        byte[] bb = null;// getByte4(b,0);
        bb = getByte(b,0,32);
        var = byte2string(bb);
        bb = getByte(b,(0+32),4);
        type = byte2int(bb);
        bb = getByte(b,0+32+4,16);
        value = byte2string(bb);
        bb = getByte(b,0+32+4+16,32);
        uname = byte2string(bb);
        bb = getByte(b,0+32+4+16+32,32);
        did = byte2string(bb);
    }   
    public msgSet() {
        //this.var = var;
    }   

    public byte[] toByte() {
        byte[] bb = new byte[32+4+16+32+32];
        byte[] b =null;
        b = string2byte(var);
        System.arraycopy(b,0,bb,0,b.length);
        b = int2byte(type);
        System.arraycopy(b,0,bb,(0+32),b.length);
        b = string2byte(value);
        System.arraycopy(b,0,bb,(0+32+4),b.length);
        b = string2byte(uname);
        System.arraycopy(b,0,bb,(0+32+4+16),b.length);
        b = string2byte(did);
        System.arraycopy(b,0,bb,(0+32+4+16+32),b.length);
        return bb; 
    }  
}
