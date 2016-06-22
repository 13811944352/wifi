<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
	String uname = request.getParameter("uname");
	String pwd = request.getParameter("pwd");
    JSONObject json = new JSONObject();
    //JSONArray ja = new JSONArray();
    if(uname == null) {
        json.put("err","no uname");
        out.print(json.toString());
        return;
    }
    if(pwd == null) {
        json.put("err","no pwd");
        out.print(json.toString());
        return;
    }
	
	//use_info = new use_info(pageContext);
	//String token = u.login(uname,pwd);
	String token = new use_info(pageContext).login(uname,pwd);
	if(token == null) {
        json.put("err","no token");
        out.print(json.toString());
        return;
	}
	json.put("token",token);
	json.put("err","no error");
    out.print(json.toString());
%> 
