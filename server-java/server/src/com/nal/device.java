package com.nal;  

import java.sql.*;
import java.util.ArrayList;
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

	public String delDevice(String u,String json) throws Exception{
        deviceConfig d = deviceConfig.j2d(json);
		return delDevice(u,d);// throws Exception{
	}

	public String delDevice(String u,deviceConfig  d) throws Exception{
		Connection conn=getCon();
		if(d == null)
			return "deviceConfig.j2d err";
        String sql = null;

		String ret = new user_per(mPC).del(u,d.deviceId);
		sql = String.format("delete from device where deviceID='%s'",d.deviceId);
        Statement mStat = null;//conn.createStatement();
        try {
            mStat = conn.createStatement();
            mStat.executeUpdate(sql);
        }catch(SQLException e) {
            return ""+false;
        }finally{
        	closeAll(null, mStat, conn);
		}
        return "true";
	}

	public String writeDevice(deviceConfig  d) throws Exception{
		Connection conn=getCon();
        //deviceConfig d = deviceConfig.j2d(json);
        if(d == null) {
            return "deviceConfig.j2d err"; 
        }
        String sql = null;
		deviceConfig old = getDevice(d.deviceId);

		if(old != null) {
				//return "deviceConfig is in";
		} else {


		//if(old == null) 
        sql = String.format("INSERT INTO device (type,deviceID,deviceName,deviceDesc,deviceAddr,deviceTempIn,deviceTempOut,deviceSpec,deviceGap,deviceMaterial) VALUES(%d,'%s','%s','%s','%s',%d,%d,%d,%d,%d);",d.deviceType,d.deviceId,d.deviceName,d.deviceDesc,d.deviceAddr,d.deviceTempIn,d.deviceTempOut,d.deviceSpec,d.deviceGap,d.deviceMaterial);
		System.out.println(sql);
        //if(old.equals(d))
        //    return "deviceConfig is in";
        Statement mStat = null;//conn.createStatement();
		
		try {
			mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		} catch(SQLException e) {
			return ""+e;
		} finally {
			closeAll(null, mStat, conn);
		}
		}
        HttpServletRequest hr = (HttpServletRequest)mPC.getRequest();
        String token = hr.getHeader("tt");
        String uname = new use_info(mPC).getUname(token);
        oo("writeDevice name:"+uname);
		String ret = new user_per(mPC).add(uname,d.deviceId,1);
		oo("writeDevice ret:"+ret);
        return "true";

    }

    public deviceConfig getDevice(String did) { //SQLException {
    	Connection conn=getCon();
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
				d.deviceTempIn= rs.getInt("deviceTempIn");
				d.deviceTempOut= rs.getInt("deviceTempOut");
				d.deviceSpec= rs.getInt("deviceSpec");
				d.deviceGap= rs.getInt("deviceGap");
				d.deviceMaterial= rs.getInt("deviceMaterial");
				
				d.deviceId = rs.getString("deviceID").trim();
				d.deviceName = rs.getString("deviceName").trim();
				d.deviceDesc = rs.getString("deviceDesc").trim();
				d.deviceAddr= rs.getString("deviceAddr").trim();
				for(int i = 0;i<8;i++) {
					String tag = "n"+(i+1);
					d.nodeId[i] = rs.getString(tag).trim();
				}
				return d; 
			}
		} catch (SQLException e) {
			return null;
		} finally {
			closeAll(rs, mStat, conn);
		}
        return d;
    }

}  
