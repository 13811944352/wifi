package com.android.nal.net;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import com.android.nal.deviceConfig;
import com.android.nal.temp_historyConfig;
import com.android.nal.nodeConfig;
import com.android.nal.UURL;
import com.android.nal.utils.HttpUtil;
import com.android.nal.utils.l;
import com.android.nal.utils.MD5;
import com.android.nal.local.localConfig;
import android.content.Context;

public class netConfig {
	private Context mC = null;
    private static netConfig mLc;

    public static synchronized netConfig getInstance() {
        if (mLc == null) {
            mLc = new netConfig();
        }
        return mLc;
    }


	private netConfig() {

	}


	public ArrayList<temp_historyConfig> getHistory(String did,String time) {
		String ret = HttpUtil.httpGet(UURL.history+"did="+did+"&date="+time);
		log("http history result:"+ret);
		JSONObject json = null;
		JSONArray ja = null;
		try {
			json = new JSONObject(ret);
			String err = json.getString("error");
			if(!err.equals("no error"))
				return null;
			log("history no err");
			ja = json.getJSONArray("result");
		} catch (Exception e) {
			log("his:"+e);
			return null;
		}
		if(ja == null) {
			log("jar null");
			return null;
		}
		log("jar len:"+ja.length());
		ArrayList<temp_historyConfig> list = new ArrayList<temp_historyConfig>();
		for(int i = 0;i < ja.length();i++) {
			try {
				JSONObject o = ja.getJSONObject(i);
				temp_historyConfig dc = temp_historyConfig.j2d(o.toString());
				log("add:"+dc.d2j(dc));
				list.add(dc);
			}catch(Exception e) {
				continue;
			}
		}
		return list;
	}

	public String login(String uname,String pwd) {
		String token = null;
		String md5 = MD5.md5String(pwd);
		log("md5:"+md5);
		String result = HttpUtil.httpGet(UURL.login+"uname="+uname+"&pwd="+md5.toLowerCase());
		log("http login result:"+result);
		JSONObject json = null;// = JSONObject.fromObject(ret)
		try {
			json = new JSONObject(result);
			String err = json.getString("err");
			if(!err.equals("no error"))
				return null;
			token  = json.getString("token");
		} catch (Exception e) {
			return null;
		}
		return token;
	}

	public boolean capt(String uname) {
		String result = HttpUtil.httpGet(UURL.capt+"phone="+uname);
		log("http capt result:"+result);
		JSONObject json = null;// = JSONObject.fromObject(ret)
		try {
			json = new JSONObject(result);
			String err = json.getString("err");
			if(!err.equals("no error"))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;

	}
	public String reg(String uname,String pwd,String capt) {
		String token = null;
		String md5 = MD5.md5String(pwd);
		log("md5:"+md5);
		String result = HttpUtil.httpGet(UURL.reg+"uname="+uname+"&hash="+md5.toLowerCase()+"&capt="+capt);
		log("http reg result:"+result);
		JSONObject json = null;  
		try {
			json = new JSONObject(result);
			String err = json.getString("err");
			if(!err.equals("no error"))
				return null;
			token  = json.getString("token");
		} catch (Exception e) {
			return null;
		}
		return token;
	}

	public String reg(String uname,String pwd) {
		String token = null;
		String md5 = MD5.md5String(pwd);
		log("md5:"+md5);
		String result = HttpUtil.httpGet(UURL.reg+"uname="+uname+"&hash="+md5.toLowerCase());
		log("http reg result:"+result);
		JSONObject json = null; 
		try {
			json = new JSONObject(result);
			String err = json.getString("err");
			if(!err.equals("no error"))
				return null;
			token  = json.getString("token");
		} catch (Exception e) {
			return null;
		}
		return token;
	}


	nodeConfig json2nodeConfig(JSONObject o) throws Exception{
		return nodeConfig.j2n(o.toString());
	}

	deviceConfig json2deviceConfig(JSONObject o) {
		return deviceConfig.j2d(o.toString());
	}

	public ArrayList<deviceConfig> getDeviceList() {
		String ret = HttpUtil.httpGetT(UURL.deviceConfig);//+"token="+localConfig.getInstance().getToken());
		log(ret);
	
		JSONObject json = null;// = JSONObject.fromObject(ret)
		JSONArray ja = null;
		try {
			//json = JSONObject.fromObject(ret);
			json = new JSONObject(ret);
			ja = json.getJSONArray("device");
		} catch (Exception e) {
			return null;
		}
		if(ja == null)
			return null;
		ArrayList<deviceConfig> list = new ArrayList<deviceConfig>();
		for(int i = 0;i < ja.length();i++) {
			try {
				JSONObject o = ja.getJSONObject(i);
				deviceConfig dc = json2deviceConfig(o);
				list.add(dc);
			}catch(Exception e) {
				continue;
			}
		}
		return list;
	}

	public boolean delDeviceConfig(deviceConfig d){
		String json = d.d2j(d);
		String result = HttpUtil.httpGetT(UURL.delDev+"json="+json);
		log("http del result:"+result);
		try {
			JSONObject jo = new JSONObject(result);
			String err = jo.getString("err");
			if(err.equals("true"))
				return true;
		}catch(Exception e) {
			return false;
		}
		
		return false;

	}


	public boolean regDeviceConfig(deviceConfig d) {
		String json = d.d2j(d);
		String result = HttpUtil.httpGetT(UURL.regDev+"json="+json);
		log("http reg result:"+result);
		try {
			JSONObject jo = new JSONObject(result);
			String err = jo.getString("err");
			if(err.equals("true"))
				return true;
		}catch(Exception e) {
			return false;
		}
		
		return false;
	}

	public boolean setNodeConfig(nodeConfig n) {
		String ret = HttpUtil.httpGetT(UURL.setNode+"node="+n.n2j(n));
		log(ret);
		JSONObject json = null;
		JSONObject jo = null;
		try {
			json = new JSONObject(ret);
			String o = json.getString("err");
			if(o.equals("no err"))
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public nodeConfig getNodeConfig(String did,String nid) {
		String ret = HttpUtil.httpGetT(UURL.getNode+"did="+did+"&nid="+nid);//+"token="+localConfig.getInstance().getToken());
		log(ret);
		JSONObject json = null;// = JSONObject.fromObject(ret)
		JSONObject jo = null;// = JSONObject.fromObject(ret)
		try {
			json = new JSONObject(ret);
			String o = json.getString("node");
			return nodeConfig.j2n(o);
		} catch (Exception e) {
			return null;
		}
	}

	static void log(String line) {
        if(line == null)
            l.d("log null");
        else
            l.d(line);
    } 
}
