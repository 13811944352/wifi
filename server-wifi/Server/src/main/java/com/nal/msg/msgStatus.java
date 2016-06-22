package com.nal.msg;

import java.util.ArrayList;

public class msgStatus extends msg{
	public String devId = null;

	public class status {
		public String name;
		public String value;
		public int type;
	}
	public ArrayList<status> list;

	public msgStatus(byte[] b) {
		byte[] bb = null;
		bb = getByte(b,0,32);
		devId= byte2string(bb);
		int len = b.length-32;
		int index = len/(32+16+4);
		list = new ArrayList<status>();
		for(int i = 0;i<index;i++) {
			status s = new status();
			bb = getByte(b,32+i*(32+4+16)+0,32);
			s.name= byte2string(bb);
			bb = getByte(b,32+i*(32+4+16)+32,4);
			s.type = byte2int(bb);
			bb = getByte(b,32+i*(32+4+16)+(32+4),16);
			s.value = byte2string(bb);
			list.add(s);
		}
/*
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
*/
	}

	//magHead(byte[20] b) {

	//magHead(byte[20] b) {
		
	//} 

}
