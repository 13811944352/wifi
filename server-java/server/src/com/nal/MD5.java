package com.nal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class MD5 {
	private MD5(){}
	public static final String TAG = MD5.class.getSimpleName();
    private static final char HEX_DIGITS[] =
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


	public final static String md5String(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/*

    public static String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;

        try {
        	File file = new File(filename);
        	if(!file.exists()){
				return null;
			}
            fis= new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead=fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);

        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
	public static String md5sum_native(String filePath) {
		String  cmd = "/system/bin/md5";
		Process process = null;
		String result = "";
        try {
            process = new ProcessBuilder().command(cmd,filePath).redirectErrorStream(true).start();
            InputStream in = process.getInputStream();
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader brout = new BufferedReader(inR);
            String content = brout.readLine();
            if(content != null){
            	String s = brout.readLine();
            	if(s != null){
            		result = s.substring(0, 32);
            	}
            }
            
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return result;
                
            }finally{
            
            if(process != null){
                process.destroy();
            }
        }
		return result;
	}
*/
}
