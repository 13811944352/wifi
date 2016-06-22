package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;

public class user_per extends base {  
	public user_per(PageContext pc) throws Exception{
		super(pc);
	}

	public String del(String uname,String did) {
		String sql = String.format("delete from user_per where uname = '%s' and deviceId = '%s'",uname,did);
		try {
			Statement mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		}catch(SQLException e) {
			return ""+false;
		}
		return "true";
		
	}

	public String del(String uname,String did,int per) {
		String sql = String.format("delete from user_per where uname = '%s' and deviceId = '%s' and per = %d",uname,did,per);
		try {
			Statement mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		}catch(SQLException e) {
			return ""+false;
		}
		return "true";
	}

	public String add(String uname,String deviceId,int per) {
		String sql = null;
		int p = getPer(uname,deviceId);
		if(p == -1)
			sql = String.format("INSERT INTO user_per (uname,deviceId,per) VALUES('%s','%s',%d);",uname,deviceId,per);
		if(p>=per)
			return "exists";
        Statement mStat = null;
		try {
			mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		} catch (SQLException e) {
			return  ""+e;
		} finally {
			close(mStat);
		}
		return "true";
	}

    public int getPer(String uname,String did) { // throws SQLException{
        String sql = "select * from user_per where uname = '"+uname+"' and deviceId= '"+did+" '";    
        int per;
        int max = -1; 
		ResultSet rs = null;// = mStat.executeQuery(sql);
        ArrayList<Integer> index  = null;// = new ArrayList<Integer>();
		Statement mStat= null;// = conn.createStatement();
		try {
			mStat = conn.createStatement();
			if(mStat == null) {
				return -1;
			}
			rs = mStat.executeQuery(sql);
			index =  new ArrayList<Integer>();
			while(rs.next()){
				int i = rs.getInt("per");
				if(i > max)
					max = i;
				index.add(i);
			}
			if(max == 1) {
				del(uname,did,0);
			}
		} catch (SQLException e) {
			return  -1;
		} finally {
			close(rs);
			close(mStat);
		}
        return max;
    } 

    public ArrayList<String> getDevice(String uname,int per) throws java.io.IOException,SQLException{
        String sql = "select * from user_per where uname = '"+uname+"' and per >= '"+per+" '";    
        ArrayList<String> index = new ArrayList<String>();
        int num = 0;
        Statement mStat = conn.createStatement();
        if(mStat == null)
            oo("mstat == null");
        //oo(sql);

        ResultSet rs = mStat.executeQuery(sql);
        while (rs.next()){
            num++;
            String i = rs.getString("deviceID").trim();
            index.add(i);
        }
        rs.close();
        mStat.close();
        return index;
    }

}  
