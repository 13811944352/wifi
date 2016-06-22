package com.android.nal;

public class UURL {
	//public static String ip = "123.57.3.108";
//	public static String ip = "10.100.157.147";
	//public static String ip = "10.100.157.166";
	//public static String ip = "192.168.1.123";
	//public static String ip = "192.168.1.166";
	//public static String ip = "192.168.1.138";
	public static String ip = "182.92.154.195";
	public static String port = "8082";
	public static String url = "http://"+ip+":"+port+"/nal/";
	public static String shop = "http://"+ip+":"+"8081"+"/uu/mobile";

	public static String deviceConfig = url+"a.jsp?";
	public static String getNode= url+"getnode.jsp?";
	public static String setNode= url+"setnode.jsp?";
	public static String reg = url+"reg.jsp?";
	public static String regDev = url+"regDevice.jsp?";
	public static String login = url+"login.jsp?";
	public static String history = url+"history.jsp?";

	public static String makeURL(String u) {
        if(!u.startsWith("http://"))
            return url+u;
		return u;
	}

}
