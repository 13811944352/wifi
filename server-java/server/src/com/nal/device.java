package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;

public class device extends base{  
	public device(PageContext pc) throws Exception{
		super(pc);
	}

	public String writeDevice(String json) throws Exception{
        deviceConfig d = deviceConfig.j2d(json);
		return writeDevice(d);// throws Exception{
		
	}

	public String writeDevice(deviceConfig  d) throws Exception{
        //deviceConfig d = deviceConfig.j2d(json);
        if(d == null) {
            return "deviceConfig.j2d err"; 
        }
        String sql = null;
		deviceConfig old = getDevice(d.deviceId);

		if(old != null) {
			if(old.equals(d))
				return "deviceConfig is in";
		}


		if(old == null)
            sql = String.format("INSERT INTO device (type,deviceID,deviceName,deviceDesc,deviceAddr) VALUES(%d,'%s','%s','%s','%s');",d.deviceType,d.deviceId,d.deviceName,d.deviceDesc,d.deviceAddr);
		//if(old.equals(d))
        //    return "deviceConfig is in";
        Statement mStat = null;//conn.createStatement();
		
		try {
			mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		} catch(SQLException e) {
			return ""+e;
		} finally {
			close(mStat);
			//close(conn);
		}
        HttpServletRequest hr = (HttpServletRequest)mPC.getRequest();
        String token = hr.getHeader("tt");
        //oo("token:"+hr.getHeader("tt"));
        String uname = new use_info(mPC).getUname(token);
        oo("writeDevice name:"+uname);
		String ret = new user_per(mPC).add(uname,d.deviceId,1);
		oo("writeDevice ret:"+ret);
        return "true";

    }

    public deviceConfig getDevice(String did) { //SQLException {
        String sql = "select * from device where deviceID = '"+did+"'";    
		
        JSONObject jo = null;
        Statement mStat = null;//conn.createStatement();
        ResultSet rs = null;//mStat.executeQuery(sql);
        //String[] node = null; new String[8];
		deviceConfig d = null;
		try {
			mStat = conn.createStatement();
			rs = mStat.executeQuery(sql);
			if(rs.next()){
				d = new deviceConfig();
				d.deviceType = rs.getInt("type");
				d.deviceId = rs.getString("deviceID").trim();
				d.deviceName = rs.getString("deviceName").trim();
				d.deviceDesc = rs.getString("deviceDesc").trim();
				d.deviceAddr= rs.getString("deviceAddr").trim();
				//jo = new JSONObject();
				//JSONArray ja = new JSONArray();
				//node = new String[8];
				for(int i = 0;i<8;i++) {
					String tag = "n"+(i+1);
					//oo(tag);
					d.nodeId[i] = rs.getString(tag).trim();
					//oo(node[i]+"");
					//JSONObject tmp = getNodeInfo(did,node[i]);
					//if(tmp != null) {
					//	ja.add(tmp);
					//}
				//}
				//jo.put("deviceType",type);
				//jo.put("deviceID",deviceID);
				//jo.put("deviceName",deviceName);
				//jo.put("deviceDescription",deviceDescription);
				//jo.put("deviceAddress",deviceAddress);
				//jo.put("nodeList",ja);
				}
				return d; 
			}
		} catch (SQLException e) {
			return null;
		} finally {
			close(rs);
			close(mStat);
		}
        return d;
    }

}  