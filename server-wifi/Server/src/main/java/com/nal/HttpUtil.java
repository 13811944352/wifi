package com.nal;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtil {
	private HttpUtil(){}

	public static String getHttpResult(InputStream is){
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line = br.readLine()) != null) {
	        	sb.append(line);
	        }
	        br.close();
		} catch (IOException e) {
		}
		return sb.toString();
	}

	//2015-7-29 Add method to download file
	public static String httpGetFile( final String uri, final String path){
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();

			//Set connection
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//conn.setRequestProperty("Connection", "close");
			int code = conn.getResponseCode();
			log( "http code = " + code);
			if(code==200){
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(path);
				int len = 0;
				byte[] buf = new byte[8192];
				while(true){
					len = is.read(buf);
					if(len<=0){
						break;
					}
					fos.write(buf, 0, len);
				}
				fos.close();
				is.close();
				result = "OK";
			}
		}catch (RuntimeException e) {
			log( "e : " + e);
		} catch (IOException e) {
			log( "e : " + e);
		} finally {
			if (conn != null) conn.disconnect();
		}
		return result;
	}


	public static String httpGetT(final String uri){
		log("httpGetT:"+uri);
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			//String token = localConfig.getInstance().getToken();
			//log("httpGetT set tt:"+token);
			//conn.setRequestProperty("tt",token);
			//Set connection
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//conn.setRequestProperty("Connection", "close");
			int code = conn.getResponseCode();
			log( "http code = " + code);
			if(code==200){//如果上报成功, 才删除所有归档数据. 否则应保留归档数据,等待下次上报
				result = getHttpResult(conn.getInputStream());
			}
		}catch (RuntimeException e) {
			log( "e : " + e);
		} catch (IOException e) {
			log( "e : " + e);
		} finally {
			if (conn != null) conn.disconnect();
		}
		return result;
	}
	
	//2014-9-25
	public static String httpGet(final String uri){
		log("httpGet:"+uri);
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			//String token = localConfig.getInstance().getToken();
			//conn.setRequestProperty("tt",token);
			//Set connection
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//conn.setRequestProperty("Connection", "close");
			int code = conn.getResponseCode();
			log( "http code = " + code);
			if(code==200){//如果上报成功, 才删除所有归档数据. 否则应保留归档数据,等待下次上报
				result = getHttpResult(conn.getInputStream());
			}
		}catch (RuntimeException e) {
			log( "e : " + e);
		} catch (IOException e) {
			log( "e : " + e);
		} finally {
			if (conn != null) conn.disconnect();
		}
		return result;
	}
	
	//2014-9-25
	public static String httpPost( final String uri, final String body, final String desc){
		String tag = "HttpUtil.httpPost";
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			byte[] buf = body.getBytes("utf-8");

			//Set connection
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(buf.length));
			//if(!TextUtils.isEmpty(desc)){//2014-11-27 使用EngineDescriptionV2
			//	conn.setRequestProperty("EngineDescriptionV2", desc);
			//}
			conn.setDoOutput(true);

			OutputStream outStream = conn.getOutputStream();
			outStream.write(buf);
			outStream.flush();
			outStream.close();
			
			int code = conn.getResponseCode();
			log( "http code = " + code);
			if(code==200){//如果上报成功, 才删除所有归档数据. 否则应保留归档数据,等待下次上报
				result = getHttpResult(conn.getInputStream());
			}
		}catch (RuntimeException e) {
			log( "e : " + e);
		} catch (IOException e) {
			log( "e : " + e);
		} finally {
			if (conn != null) conn.disconnect();
		}
		return result;
	}

	//Get URL query string
	public static String encode(String value){
		String str = "";
        try {
			str = (value==null?"":URLEncoder.encode(value,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log("encode error : " + e);
		}
		return str;
	}

    static void log(String line) {
//        if(line == null)
//            l.d("log null");
//        else
//            l.d(line);
		System.out.println(line);
    }
}
