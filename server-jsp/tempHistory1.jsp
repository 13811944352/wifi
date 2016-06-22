<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%

	//String s = request
	String s = request.getParameter("temp");
	//userInfo info = new userInfo();
	//	info.uname = request.getParameter("uname").trim();
	//String msg = request.getParameter("json");
	//String pwd = request.getParameter("pwd");
    JSONObject json = new JSONObject();
    //JSONArray ja = new JSONArray();
    if(s == null) {
        json.put("err","no arg");
        out.print(json.toString());
        return;
    }
	temp_history u = new temp_history(pageContext);
	//String ret = new temp_history(pageContext).write(info);
	String ret = u.write(s);
	if(ret.equals("true")) {
        json.put("err","no error");
        out.print(json.toString());
        //return;
	} else {
        json.put("err",ret);
        out.print(json.toString());
        //return;
	}
	//return;
%> 
