package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;

public class read{  
/*      

*/
    void oo(String line) throws java.io.IOException{
        //mPC.getOut().print(line);
    }

	PageContext mPC;
	Connection conn = null;
	//Statement stat = null;

	public read(PageContext pc) throws Exception{
		mPC = pc;
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		String db ="jdbc:mysql://localhost:3306/nal?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";  
		conn= DriverManager.getConnection(db);  
		//stat = conn.createStatement();  
	}



    public JSONObject getNodeInfo(String did,String nid) throws java.io.IOException,SQLException {
        String sql = "select * from node where deviceID ='"+did+"' and nodeID = '"+nid+"'";
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
/*
	public boolean regDeviceInfo(String json) throws java.io.IOException,SQLException {
		deviceConfig d = deviceConfig.j2d(json);
		if(d == null)
			return false;
		String sql = null;
		if(getDeviceInfoString(d.deviceId) == null)
			sql = "insert"
		return true;
	}
*/
/*
	public String getDeviceInfoString(String did) {
		JSONObject jo = null;
		try {
			jo = getDeviceJson(did);
			if(jo != null)
				return jo.toString();
		} catch(Exception e) {
			return null;
		}
		return null;
	}

    public JSONObject getDeviceJson(String did) throws java.io.IOException,SQLException {
        String sql = "select * from device where deviceID = '"+did+"'";    
        //String sql = "select * from device where deviceID=1002";//+index;    
		oo(sql);
        JSONObject jo = null;
        Statement mStat = conn.createStatement();
        ResultSet rs = mStat.executeQuery(sql);

        String[] node = new String[8];
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
                node[i] = rs.getString(tag).trim();
                oo(node[i]+"");
                JSONObject tmp = getNodeInfo(did,node[i]);
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

        rs.close();
        mStat.close();
        return jo;
	}
*/
}  
