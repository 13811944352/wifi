<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%@ page import="java.sql.*"%>  
<%@ page import="org.apache.http.*"%>  
<%@ page language="java" import="com.nal.*"%>  
<%@ page import="net.sf.json.*"%>  
<%@ page import="net.sf.json.JSONObject"%>  
<%
	String phone = request.getParameter("phone");
    HttpPost httpost = new HttpPost("http://www.810086.com.cn/jk.aspx");  
    List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
        //Set<String> keySet = params.keySet();  
        //for(String key : keySet) {  
            nvps.add(new BasicNameValuePair("zh","33323337");  
            nvps.add(new BasicNameValuePair("mm","123456");  
            nvps.add(new BasicNameValuePair("sms_type","42");  
            nvps.add(new BasicNameValuePair("nr","【努奥罗】您的验证码是：123456，请在30分钟内验证!");  
            nvps.add(new BasicNameValuePair("hm","13811944352");  
        //}  
          
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  

		DefaultHttpClient httpclient = new DefaultHttpClient(); 
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

        HttpEntity entity = response.getEntity();  
          
        log.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
        log.info(charset);  
          
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
            log.info(body);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

        //return response;  

          
        //return httpost;  

%> 
