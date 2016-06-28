package com.nal;  

import java.sql.*;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import net.sf.json.*;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;

public class node extends base{  
	public node(PageContext pc) throws Exception{
		super(pc);
	}

	public node() throws Exception{
		super(null);
	}

    private static final String TABLE_NODE = "node";
	private static final String D_ID   = "d_did";
    private static final String N_ID    = "n_id";
    private static final String N_NAME  = "n_name";
    private static final String N_DESC  = "n_desc";

    private static final String N_CONFIG= "n_config";
    private static final String N_TYPE  = "n_type";
    private static final String N_TIME  = "n_time";
    private static final String N_TEMP  = "n_temp";
    private static final String N_S = "n_start";
    private static final String N_E = "n_end";
    private static final String N_TS = "n_temps";
    private static final String N_TE = "n_tempe";

    private static final String N_HTYPE= "n_homeType";
    private static final String N_HDIRE  = "n_homeDire";
    private static final String N_HFLOOR  = "n_homeFloor";
    private static final String N_HFITM  = "n_homeFitment";

	private String getString(ResultSet rs,String id) {
		try {
			return rs.getString(id);
		} catch (SQLException e) {
			return "";
		}
	}

	private int getInt(ResultSet rs,String id) {
		try {
			return rs.getInt(id);
		} catch (SQLException e) {
			return 0;
		}
	}

	public String setNode(nodeConfig n) {
        if(n == null) {
            return "nodeConfig.j2n err"; 
        }
		nodeConfig old = getNode(n.deviceId,n.nodeId);
		String sql = null;
        sql = String.format("INSERT INTO node (d_did,n_id,n_name,n_desc,n_config,n_type,n_time,n_temp,n_start0,n_start1,n_start2,n_start3,n_start4,n_start5,n_start6,n_end0,n_end1,n_end2,n_end3,n_end4,n_end5,n_end6,n_temps0,n_temps1,n_temps2,n_temps3,n_temps4,n_temps5,n_temps6,n_tempe0,n_tempe1,n_tempe2,n_tempe3,n_tempe4,n_tempe5,n_tempe6 ,n_homeType,n_homeDire,n_homeFloor,n_homeFitment) VALUES('%s','%s','%s','%s',%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d)",n.deviceId,n.nodeId,n.nodeName,n.nodeDesc,n.nodeConfig,n.nodeType,n.nodeTime,n.nodeTemp,n.nodeStart[0],n.nodeStart[1],n.nodeStart[2],n.nodeStart[3],n.nodeStart[4],n.nodeStart[5],n.nodeStart[6],n.nodeEnd[0],n.nodeEnd[1],n.nodeEnd[2],n.nodeEnd[3],n.nodeEnd[4],n.nodeEnd[5],n.nodeEnd[6],n.nodeTempS[0],n.nodeTempS[1],n.nodeTempS[2],n.nodeTempS[3],n.nodeTempS[4],n.nodeTempS[5],n.nodeTempS[6],n.nodeTempE[0],n.nodeTempE[1],n.nodeTempE[2],n.nodeTempE[3],n.nodeTempE[4],n.nodeTempE[5],n.nodeTempE[6],n.homeType,n.homeDire,n.homeFloor,n.homeFitment);

		o("sql = "+ sql);
/*
            n.deviceId, 
            n.nodeType,
            n.nodeConfig,  
            n.nodeId,
            n.nodeName, 
            n.nodeTemp,
            n.nodeDesc,
			n.day[0],
			n.day[1],
			n.day[2],
			n.day[3],
			n.day[4],
			n.day[5],
			n.day[6],
            n.s_s[0],
            n.s_s[1],
            n.s_e[0],
            n.s_e[1],
            n.j_s[0],
            n.j_s[1],
            n.j_e[0],
            n.j_e[1],
            n.f_s[0],
            n.f_s[1],
            n.f_e[0],
            n.f_e[1]);
*/  
      Statement mStat = null;//conn.createStatement();
		try {
			mStat = conn.createStatement();
			String a = "delete  from node where d_did = '"+n.deviceId+"' and n_id ='"+n.nodeId+"'";    
			if(old == null)
				mStat.executeUpdate(sql);
			else {
				mStat.executeUpdate(a);
				mStat.executeUpdate(sql);
			}
		} catch(SQLException e) {
			return ""+e;
		} finally {
			close(mStat);
		}
		return "no err";
	}

    public nodeConfig getNode(String did,String nid) { //SQLException {
        String sql = "select * from node where d_did = '"+did+"' and n_id = '"+nid+"'";    
		System.out.println("getNode sql:"+sql);
        //JSONObject jo = null;
        Statement mStat = null;//conn.createStatement();
        ResultSet cr = null;//mStat.executeQuery(sql);
        //String[] node = null; new String[8];
		nodeConfig n = null;
		try {
			mStat = conn.createStatement();
			System.out.println("getNode sql2:" + sql);
			cr = mStat.executeQuery(sql);
			System.out.println("getNode cr :"+cr);
			cr.beforeFirst();
			if(cr.next()){
				n = new nodeConfig();
				n.deviceId = getString(cr,D_ID);
				n.nodeType = getInt(cr,N_TYPE);
	            n.nodeConfig = getInt(cr,N_CONFIG);
		        n.nodeId = getString(cr,N_ID);
			    n.nodeName = getString(cr,N_NAME);
	            n.nodeTemp= getInt(cr,N_TEMP);
		        n.nodeDesc= getString(cr,N_DESC);
		        n.nodeTime= getInt(cr,N_TIME);
		        n.homeType= getInt(cr,N_HTYPE);
		        n.homeDire= getInt(cr,N_HDIRE);
		        n.homeFloor= getInt(cr,N_HFLOOR);
		        n.homeFitment= getInt(cr,N_HFITM);
			    for(int i = 0;i<7;i++) {
				    n.nodeStart[i] = getInt(cr,N_S+i);
				    n.nodeEnd[i] = getInt(cr,N_E+i);
				    n.nodeTempS[i] = getInt(cr,N_TS+i);
				    n.nodeTempE[i] = getInt(cr,N_TE+i);
				}
				System.out.println("getNode json:"+n.n2j(n));
				return n;
			} else {
				System.out.println("cr next : false");
			}
		} catch (SQLException e) {
			System.out.println("getNode sql e:"+e);
			return null;
		} finally {
			close(cr);
			close(mStat);
		}
        return n;
    }

}  
