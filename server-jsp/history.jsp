<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%

	String did = request.getParameter("did");
	String date = request.getParameter("date");
    JSONObject json = new JSONObject();
    if(did == null || date == null) {
        json.put("err","no arg");
        out.print(json.toString());
        return;
    }
	temp_history u = new temp_history(pageContext);
	String ret = u.reads(did,date);
	if(ret == null) {
        json.put("err","no result");
        out.print(json.toString());
        return;
	} else {
        out.print(ret);
	}
/*
	if(ret.equals("true")) {
        json.put("err","no error");
        out.print(json.toString());
        //return;
	} else {
        json.put("err",ret);
        out.print(json.toString());
        //return;
	}
*/
	//return;
%> 
