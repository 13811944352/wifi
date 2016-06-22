<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
	String token = request.getHeader("tt");

	use_info u = new use_info(pageContext);
	String uname = u.getUname(token);
    JSONObject json = new JSONObject();
    JSONArray ja = new JSONArray();
    if(uname == null) {
        json.put("err","no uname");
        out.print(json.toString());
        return;
    }
	user_per up = new user_per(pageContext);
	ArrayList<String> a = up.getDevice(uname,0);
	ArrayList<String> b = up.getDevice(uname,1);
	//read r = new read(pageContext);
	device r = new device(pageContext);
/*
	for(int i = 0; i < a.size(); i++){
        String tmp = a.get(i);
        JSONObject jo = r.getDevice(tmp).d2j();
		jo.put("per",0);
		ja.add(jo);
    }
*/  
    for(int i = 0; i < b.size(); i++){
        String tmp = b.get(i);
		deviceConfig d = r.getDevice(tmp);
		d.devicePer = 1;
		String j = d.d2j(d);
        JSONObject jo = JSONObject.fromObject(j);
		//jo.put("per",1);
		ja.add(jo);
    }

	json.put("device",ja);
	out.print(json.toString());

%> 
