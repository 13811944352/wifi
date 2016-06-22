package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;

public class use_info extends base{  
	public use_info(PageContext pc) throws Exception{
		super(pc);
	}

	public String getUname(String token) { //throws Exception {
        String sql = "select uname from use_info where token='"+token+"'";// and nodeID = '"+nid+"'";
		Statement stmt = null;//conn.createStatement();
		ResultSet rs = null;//stmt.executeQuery(sql);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				String uname = rs.getString("uname").trim();
				return uname;
			}
		} catch (SQLException e) {
			return null;
		} finally {
			close(rs);
			close(stmt);
		}
		return null;
		
	}

	private String getToken(String uname,String hash) {
		String s = MD5.md5String(uname).toLowerCase();
		return s+hash;
	}

	public String reg(userInfo info) throws Exception {
        String sql = "select * from use_info where uname='"+info.uname+"'";// and nodeID = '"+nid+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
			return "uname have";
		}
		String token = getToken(info.uname,info.hash);
		sql = "INSERT INTO use_info (`uname`, `mail`, `phone_no`, `province`, `city`, `county`, `addr`, `id_no`, `sex`, `hash`, `token`) VALUES ('"+info.uname+"', '"+info.mail+"', '"+info.phone_no+"', '"+info.province+"', '"+info.city+"', '"+info.county+"', '"+info.addr+"', '"+info.id_no+"', '"+info.sex+"', '"+info.hash+"', '"+token+"')";
		stmt.executeUpdate(sql);
		return token;
	}

	public String login(String u,String p) throws Exception {
        String sql = "select * from use_info where uname='"+u+"'";// and nodeID = '"+nid+"'";
        String token = null;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            String pwd = rs.getString("hash").trim();
            String t = rs.getString("token").trim();
			if(p.equals(pwd))
				token = t;
        }
        rs.close();
        stmt.close();
        return token;
	}

}  
