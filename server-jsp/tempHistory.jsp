<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%

	String s = request.getParameter("temp");
    JSONObject json = new JSONObject();
    if(s == null) {
        json.put("err","no arg");
        out.print(json.toString());
        return;
    }
	temp_history u = new temp_history(pageContext);
	String ret = u.write(s);
	if(ret.equals("true")) {
        json.put("err","no error");
        out.print(json.toString());
	} else {
        json.put("err",ret);
        out.print(json.toString());
	}
%> 
