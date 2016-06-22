<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
	//String token = request.getHeader("tt");
	//use_info u = new use_info(pageContext);
	//String uname = u.getUname(token);
    JSONObject json = new JSONObject();
    //JSONArray ja = new JSONArray();
    //if(uname == null) {
    //    json.put("err","no uname");
    //    out.print(json.toString());
    //    return;
    //}
	//user_per up = new user_per(pageContext);
	//ArrayList<String> a = up.getDevice(uname,0);
	//ArrayList<String> b = up.getDevice(uname,1);
	//read r = new read(pageContext);
	String node = request.getParameter("node").trim();
	//String nid = request.getParameter("nid").trim();
	//device rd = new device(pageContext);
	node rn = new node(pageContext);
	//deviceConfig d = rd.getDevice(did);
    //for(int i = 0; i < 8; i++){
	//nodeConfig n = rn.getNode(d.deviceId,d.nodeId[i]);
	nodeConfig n = nodeConfig.j2n(node);
	if(n == null) {
		json.put("err","node err");
		out.print(json.toString());
		return ;
	}
	String ret = rn.setNode(n);
	//out.print("n == "+n.n2j(n));
/*
		out.print("n == "+n.deviceId);
		out.print("n == "+n.nodeId);
		out.print("n == "+n.nodeTemp);
*/		
	//String j = n.n2j(n);
    //    JSONObject jo = JSONObject.fromObject(j);
	//	ja.add(jo);
        //JSONObject jo = JSONObject.fromObject(j);
		//jo.put("per",1);
        //String tmp = b.get(i);
		//deviceConfig d = r.getDevice(tmp);
		//d.devicePer = 1;
		//String j = d.d2j(d);
        //JSONObject jo = JSONObject.fromObject(j);
		//jo.put("per",1);
		//ja.add(jo);
    //}

	json.put("err",ret);
	out.print(json.toString());

%> 
