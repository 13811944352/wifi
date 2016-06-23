package com.nal;

import net.sf.json.*;
public final class deviceConfig{
	public int			deviceType;
	public String		deviceId;
	public String		deviceName;
	public String		deviceDesc;
	public String		deviceAddr;
	public int			devicePer;

	public int          deviceTempIn,deviceTempOut;
	public int          deviceSpec;
	public int          deviceGap;
	public int          deviceMaterial;

	public String[]		nodeId;

	public deviceConfig(){
		nodeId = new String[8];
	}

	public boolean equals(deviceConfig d) {
		if(deviceType != d.deviceType)
			return false;
		if(!deviceId.equals(d.deviceId))
			return false;
		if(!deviceName.equals(d.deviceName))
			return false;
		if(!deviceDesc.equals(d.deviceDesc))
			return false;
		if(!deviceAddr.equals(d.deviceAddr))
			return false;
		return true;
	}


	public static deviceConfig j2d(String json) {
		deviceConfig d = null;
        JSONArray ja = null;//o.getJSONArray("nodeList");
		try {
			d = new deviceConfig();
			JSONObject o = JSONObject.fromObject(json); 
			//JSONObject o = new JSONObject(json);
			d.deviceType = o.getInt("deviceType");
			d.deviceId = o.getString("deviceId");
			d.deviceDesc = o.getString("deviceDesc");
			d.deviceAddr = o.getString("deviceAddr");
			d.deviceName = o.getString("deviceName");
			d.devicePer = o.getInt("devicePer");
			d.deviceTempIn = o.getInt("deviceTempIn");
			d.deviceTempOut = o.getInt("deviceTempOut");
			d.deviceSpec= o.getInt("deviceSpec");
			d.deviceGap= o.getInt("deviceGap");
			d.deviceMaterial= o.getInt("deviceMaterial");
			ja = o.getJSONArray("nodeList");
			for(int i = 0;i < ja.size();i++) {
				try {
					JSONObject oo = ja.getJSONObject(i);
					String n = oo.getString("nodeId");
					d.nodeId[i] = n;
				}catch(Exception e) {
					continue;
				}
			}
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	public static String d2j(deviceConfig d) {
        try {
            JSONObject jo = new JSONObject();
            jo.put("deviceType",d.deviceType);
            jo.put("deviceId",d.deviceId);
            jo.put("deviceDesc",d.deviceDesc);
            jo.put("deviceAddr",d.deviceAddr);
            jo.put("deviceName",d.deviceName);
            jo.put("devicePer",d.devicePer);
            jo.put("deviceTempIn",d.deviceTempIn);
            jo.put("deviceTempOut",d.deviceTempOut);
            jo.put("deviceSpec",d.deviceSpec);
            jo.put("deviceGap",d.deviceGap);
            jo.put("deviceMaterial",d.deviceMaterial);

			JSONArray ja = new JSONArray();
			for(int i = 0;i<8;i++) {
				JSONObject o = new JSONObject();
				o.put("nodeId",d.nodeId[i]);
				ja.add(o);
			}
			jo.put("nodeList",ja);
            return jo.toString();
        } catch (Exception e) {
            return null;
        }
	}

}
