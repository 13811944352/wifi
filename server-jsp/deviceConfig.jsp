<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%@ page import="java.util.ArrayList"%>  



<%! 
	PageContext mPC;
	//Statement mStat;
	Connection conn; 
	void oo(String line) throws java.io.IOException{
		mPC.getOut().print(line);
	}

    ArrayList<Integer> getReadDevice(String uid,int per) throws java.io.IOException,SQLException{
        //String sql = "select * from nal.user_per where uid = '"+uid+"' and per= '"+per+" '";    
        String sql = "select * from nal.user_per where uid = 101 and per= 0";    
        ArrayList<Integer> index = new ArrayList<Integer>();
        int num = 0;
		Statement mStat = conn.createStatement();  
		if(mStat == null)
			oo("mstat == null");
		oo(sql);

        ResultSet rs = mStat.executeQuery(sql);    
        while (rs.next()){
            num++;
            int i = rs.getInt("deviceIndex");
            index.add(i);
        }
		rs.close();
		mStat.close();
        return index;
    }	
	JSONObject getNodeInfo(int index) throws java.io.IOException,SQLException {
        String sql = "select * from node where index ="+index;    
		JSONObject jo = null;
		Statement mStat = conn.createStatement();  
        ResultSet rs = mStat.executeQuery(sql);    
        if(rs.next()){
			int nodeType = rs.getInt("nodeType");
			int nodeTemp = rs.getInt("nodeTemp");
			String nodeID = rs.getString("nodeID").trim();
			String nodeName = rs.getString("nodeName").trim();
			String nodeDesc = rs.getString("nodeDesc").trim();
			JSONArray ja = new JSONArray();
			jo.put("nodeType",nodeType);
			jo.put("nodeTemp",nodeTemp);
			jo.put("nodeID",nodeID);
			jo.put("nodeName",nodeName);
			jo.put("nodeDesc",nodeDesc);
		}
		rs.close();
		mStat.close();
		return jo;
		
	}

	JSONObject getDeviceInfo(int index) throws java.io.IOException,SQLException {
        //String sql = "select * from device where index = "+index;    
        String sql = "select * from device where index = 1001";//+index;    
		JSONObject jo = null;
		Statement mStat = conn.createStatement();  
        ResultSet rs = mStat.executeQuery(sql);    
/*
		int[] node = new int[8];
        if(rs.next()){
			int type  = rs.getInt("type");
			String deviceID = rs.getString("deviceID").trim();
			String deviceName = rs.getString("deviceName").trim();
			String deviceDescription = rs.getString("deviceDescription").trim();
			String deviceAddress = rs.getString("deviceAddress").trim();
			jo = new JSONObject();
			JSONArray ja = new JSONArray();
			for(int i = 0;i<8;i++) {
				String tag = "n"+(i+1);
				oo(tag);
				node[i] = rs.getInt(tag);
				oo(node[i]+"");
				JSONObject tmp = getNodeInfo(node[i]);
				if(tmp != null) {
					ja.add(tmp);
				}
			}

			jo.put("deviceType",type);
			jo.put("deviceID",deviceID);
			jo.put("deviceName",deviceName);
			jo.put("deviceDescription",deviceDescription);
			jo.put("deviceAddress",deviceAddress);
			jo.put("nodeList",ja);
			return jo;
        }
*/  
		rs.close();
		mStat.close();
      return jo;
	}
	
%> 


<%  
	mPC = pageContext; 
	String uid = request.getParameter("uid"); 
	JSONObject json = new JSONObject();
	if(uid == null) {
		json.put("err","no uid");
		out.print(json.toString());
		return;
	}
	Class.forName("com.mysql.jdbc.Driver").newInstance();  
    String db ="jdbc:mysql://localhost:3306/nal?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";  
    conn= DriverManager.getConnection(db);  
    //Statement mStat = conn.createStatement();  
	ArrayList<Integer> a = getReadDevice(uid,0);
	ArrayList<Integer> b = getReadDevice(uid,1);

    for(int i = 0; i < a.size(); i++){
		int tmp = a.get(i);
		//getDeviceInfo(tmp);
        oo("a:"+a.get(i))  ;
    }
	
    for(int i = 0; i < b.size(); i++){
		int tmp = b.get(i);
		//getDeviceInfo(tmp);
        oo("b:"+b.get(i))  ;
    }
		getDeviceInfo(1001);

/*	
	String sql = "";
	//if(limit != null)
		//sql = "select * from task limit "+limit+" order by pos asc";  
	sql = "select * from user_per where uid ='"+uid+"'";
	//else
	//	sql = "select * from task order by pos asc";  
    ResultSet rs = stat.executeQuery(sql);    
	int type;
	//int show; 
	String deviceName,deviceDescription,deviceAddress,deviceID;
	int[] node = new int[8];
	int num = 0;
	while (rs.next()){
		num ++;
		type  = rs.getInt("type");
		deviceID = rs.getString("deviceID").trim();
		deviceName = rs.getString("deviceName").trim();
		deviceDescription = rs.getString("deviceDescription").trim();
		deviceAddress = rs.getString("deviceAddress").trim();
		for(int i = 0;i<8;i++) {
			String tag = "n"+(i+1);
			out.print(tag);
			node[i] = rs.getInt(tag);
			out.print(node[i]);
			out.print("\n");
		}
		JSONObject jo = new JSONObject();
		jo.put("type",type);
		jo.put("deviceID",deviceID);
		jo.put("deviceName",deviceName);
		jo.put("deviceDescription",deviceDescription);
		jo.put("deviceAddress",deviceAddress);
		//jo.put("url",url);
		//jo.put("type",type);
		ja.add(jo);
	}
	if(num == 0) {
		json.put("err","no such device");
		out.print(json.toString());
		return;
	}
	json.put("task",ja);
	json.put("num",num);
	out.print(json.toString());
*/
%> 
