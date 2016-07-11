<%@ page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
	String token = request.getHeader("tt");
	//out.print("token:"+token);
	String json =request.getParameter("json").trim();
	System.out.println(json);
	//json=URLDecoder.decode(json,"UTF-8");
	//System.out.println(json);
    //out.print(json.toString());
	String ret = new device(pageContext).writeDevice(json);
	JSONObject j = new JSONObject();
    j.put("err",""+ret);
    out.print(j.toString());
%> 
