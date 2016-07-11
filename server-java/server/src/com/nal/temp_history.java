package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;

public class temp_history extends base{  
	public temp_history(PageContext pc) throws Exception{
		super(pc);
	}

	public String write(String json) throws Exception{
        temp_historyConfig d = temp_historyConfig.j2d(json);
		return write(d);// throws Exception{
	}

	public String write(temp_historyConfig d) throws Exception{
        //temp_historyConfig d = temp_historyConfig.j2d(json);
		Connection conn=getCon();
        if(d == null) {
            return "temp_historyConfig.j2d err"; 
        }
        String sql = null;

        sql = String.format("INSERT INTO temp_h (did,temp) VALUES('%s','%s');",d.deviceId.trim(),d.temp.trim());
        Statement mStat = null;//conn.createStatement();
		
		try {
			mStat = conn.createStatement();
			mStat.executeUpdate(sql);
		} catch(SQLException e) {
			return ""+e;
		} finally {
			closeAll(null, mStat, conn);
		}
        return "true";

    }

	Timestamp String2Timestamp(String s) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
            try {   
                ts = Timestamp.valueOf(s);   
				return ts;
                //System.out.println(ts);   
            } catch (Exception e) {   
                e.printStackTrace();   
            }  
		return null;
	}

	String Timestamp2String(Timestamp t) {
		String s = "";   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");   
        try {   
            //方法一   
            s= sdf.format(t);   
			return s;
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
		return null;
	}


	public String reads(String did,String time) {
		ArrayList<temp_historyConfig> result = read(did,time);
		if(result == null)
			return null;
		JSONObject json = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i = 0; i<result.size(); i++){
			temp_historyConfig d = result.get(i);
			JSONObject jo = JSONObject.fromObject(d.d2j(d));
			ja.add(jo);
		}
		json.put("error","no error");
		json.put("result",ja);
		return json.toString();
	}

    public ArrayList<temp_historyConfig> read(String did,String time) { 
    	Connection conn=getCon();
        String sql = "select * from temp_h where did = '"+did+"' and add_time >= '"+time+"'" ;    
        JSONObject jo = null;
        Statement mStat = null;//conn.createStatement();
        ResultSet rs = null;//mStat.executeQuery(sql);
		temp_historyConfig d = null;
		ArrayList<temp_historyConfig> result = null;//new ArrayList<temp_historyConfig>();
		try {
			mStat = conn.createStatement();
			rs = mStat.executeQuery(sql);
			while(rs.next()){
				if(result == null)
					result = new ArrayList<temp_historyConfig>();
				d = new temp_historyConfig();
				d.deviceId = rs.getString("did").trim();
				d.time= rs.getString("add_time").trim();
				d.temp=rs.getString("temp").trim();
				result.add(d);
			}
		} catch (SQLException e) {
			//return result;
			//continue;
		} finally {
			closeAll(rs, mStat, conn);
		}
        return result;
   }

}  
