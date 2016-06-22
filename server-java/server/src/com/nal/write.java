package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;

public class write{  
    void oo(String line) throws java.io.IOException{
        mPC.getOut().print(line);
    }

	PageContext mPC;
	Connection conn = null;

	public write(PageContext pc) throws Exception{
		mPC = pc;
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		String db ="jdbc:mysql://localhost:3306/nal?user=root&password=123456&useUnicode=true&characterEncoding=utf-8";  
		conn= DriverManager.getConnection(db);  
	}

}  
