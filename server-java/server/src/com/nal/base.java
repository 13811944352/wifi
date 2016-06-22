package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;

public class base{  
	PageContext mPC;
	Connection conn = null;

    void oo(String line) throws java.io.IOException{
        //mPC.getOut().print(line);
    }

    void o(String line) {
		try {
			if(mPC != null)
				mPC.getOut().print(line);
		} catch(java.io.IOException e) {
			;
		}
    }

	public base(PageContext pc) throws Exception{
		mPC = pc;
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		String db ="jdbc:mysql://localhost:3306/nuociss_wifi?user=huanhe&password=huanhewifi&useUnicode=true&characterEncoding=utf-8";  
		//String db ="jdbc:mysql://localhost:3306/nal?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";  
		//String db ="jdbc:mysql://127.0.0.1:3306/nal?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";  
		conn= DriverManager.getConnection(db);  
	}
	void close(ResultSet s) {
		if(s == null)
			return ;
		try {
			s.close();
		} catch (SQLException e) {

		}
	}

	void close(Connection s) {
		if(s == null)
			return ;
		try {
			s.close();
		} catch (SQLException e) {

		}
	}

	void close(Statement s) {
		if(s == null)
			return ;
		try {
			s.close();
		} catch (SQLException e) {

		}
	}

	void close() {
		close(conn);
	}
}  
