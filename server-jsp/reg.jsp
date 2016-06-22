<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
		userInfo info = new userInfo();
		info.uname = request.getParameter("uname").trim();
		info.mail = request.getParameter("main");
		info.phone_no = request.getParameter("phone_no");
		info.province = request.getParameter("province");
		info.city = request.getParameter("city");
		info.county = request.getParameter("county");
		info.addr = request.getParameter("addr");
		info.id_no = request.getParameter("id_no");
		info.sex = request.getParameter("sex");
		if(info.sex == null)
			info.sex = "0";
		info.hash = request.getParameter("hash");
	//String msg = request.getParameter("json");
	//String pwd = request.getParameter("pwd");
    JSONObject json = new JSONObject();
    //JSONArray ja = new JSONArray();
    if(info.uname == null) {
        json.put("err","no uname");
        out.print(json.toString());
        return;
    }
    if(info.hash == null) {
        json.put("err","no pwd");
        out.print(json.toString());
        return;
	}
	String token = null ;
	token = new use_info(pageContext).reg(info);
	if(token == null) {
        json.put("err","no token");
        out.print(json.toString());
        return;
	}
	if(token.equals("uname have")) {
        json.put("err","uname have");
        out.print(json.toString());
        return;
	}
	json.put("token",token);
	json.put("err","no error");
    out.print(json.toString());
%> 
