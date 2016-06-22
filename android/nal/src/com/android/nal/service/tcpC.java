package com.android.nal.service;

import com.android.nal.deviceConfig;
import com.android.nal.nodeConfig;
import com.android.nal.local.*;
import com.android.nal.UURL;
import com.android.nal.service.proxy.*;
import com.android.nal.utils.l;
import android.content.Context;
import com.android.nal.mainApp;
import java.util.List;

import android.content.SharedPreferences;  
import android.content.SharedPreferences.Editor;
import android.app.Activity;

import java.io.IOException; 
import java.io.DataOutputStream; 
import java.io.DataInputStream; 
import java.net.InetSocketAddress; 
import java.net.Socket; 
import java.nio.ByteBuffer; 
import java.nio.CharBuffer; 
import java.nio.channels.SocketChannel; 
import java.nio.charset.CharacterCodingException; 
import java.nio.charset.Charset; 
import java.nio.charset.CharsetDecoder; 
import android.app.Notification; 
import android.app.NotificationManager; 
import android.app.PendingIntent; 
import android.app.Service; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Binder; 
import android.os.IBinder;
import java.math.BigInteger;

public class tcpC { // extends Thread {
    private static tcpC mC;
    private Socket mS = null; 
	private DataOutputStream mOut;
	private DataInputStream mIn;
	private msgHeartRes mHR;
	private msgRegRes	mRR ;//= new msgRegRes(r);
	private msgQueryRes mQR ;//= new msgRegRes(r);
	private msgSetRes   mSR ;//= new msgRegRes(r);

    public static synchronized tcpC getInstance() {
        if (mC == null) {
            mC = new tcpC();
        }
		return mC;
    }

	private tcpC() {
		log("tcpC create");//e.printStackTrace(); 
	}


    static void Sleep(int usec) {
        try {
            Thread.sleep(usec);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
    } 

	boolean isStart = false;

	public boolean start() {
		boolean ret = connectServer();
		if(!ret)
			return ret;
		ret = doReg();
		if(!ret) {
			sstop();
			return ret;
		}
		startRead();
		Sleep(1000);
		startHeart();
		isStart = true;
		return true;
	}

	public boolean sstop() {
		DisConnectToServer();  
		mS = null;
		mC = null;
		isStart = false;
		return true;
	}

    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }

	private byte[] merge(byte[] a,byte[] b) {
		byte[] ret = new byte[a.length+b.length];
        System.arraycopy(a,0,ret,0,a.length);
        System.arraycopy(b,0,ret,a.length,b.length);
		return ret;
	}

	private Object heartLock = new Object();
	private Object queryLock = new Object();
	private Object setLock = new Object();
	public void lock(Object o,int s) {
        synchronized(o){
			log("now lock:"+o);
			try {
				if(s == 0)
					o.wait();
				else
					o.wait(s*1000);
            } catch (InterruptedException e) {
				log("lock err:"+e);
            }
        }
	}
	public void unlock(Object o) {
			log("now unlock:"+o);
        synchronized(o){
			o.notify();
        }
	}


	private byte[] getHead(int cmd,int len) {
		msgHead head = new msgHead();
		head.magic = 0xa1b2c3d4;
		head.version = 0;
		head.type = 0;
		head.command = cmd;
		head.len =  len;
		return head.toByte();
	}

	String getUname() {
		return localConfig.getInstance().getUname();
	}

	private boolean doReg() {
		msgReg reg = new msgReg();
		reg.devId = localConfig.getInstance().getUname();
		if(reg.devId == null || reg.devId.equals("")) {
			return false;
		}
		byte[] b = reg.toByte();
		byte[] a = getHead(0x01,b.length);
        byte[] ret = merge(a,b);
        System.out.print(binary(ret,16));
		try {
			if(mOut == null)
				return false;
			mOut.write(ret);
			byte[] r = new byte[1024] ;
			if(mIn == null)
				return false;
			mIn.read(r,0,20);
			msgHead head = new msgHead(r);
			mIn.read(r,0,head.len);
			mRR = new msgRegRes(r);
			if(mRR.result == 0) {
				return true;
			}
			return false;
		} catch(IOException e) {
			log("doReg:"+e);
		}
		return true;

	}

	public String doQuery(String did,String var) {
		log("now do Query did == "+did+" var == "+var);
		msgQuery q = new msgQuery(did,var,localConfig.getInstance().getUname());
		byte[] b = q.toByte();
		byte[] a = getHead(0x1a,b.length);
        byte[] ret = merge(a,b);
		try {
			if(mOut == null) {
				log("no mout");
				return null;
			}
			mQR = null;
			mOut.write(ret);
			lock(queryLock,20);
			if(mQR == null) {
				log("no mQR");
				return null;
			}
			if(mQR.error == -1) {
				return null;
			}
			return mQR.value;
		} catch(IOException e) {
			log("doQuery:"+e);//e.printStackTrace(); 
			return null;
		}

	}

	private boolean doHeart() {
		msgHeart h = new msgHeart();
		h.devId = localConfig.getInstance().getUname();
		if(h.devId == null || h.devId.equals("")) {
			return false;
		}
		byte[] b = h.toByte();

		byte[] a = getHead(0x03,b.length);
        byte[] ret = merge(a,b);
		try {
			if(mOut == null)
				return false;
			mHR = null;
			mOut.write(ret);
			lock(heartLock,20);
			if(mHR == null)
				return false;
			log("mHR.result:"+mHR.result+" sec:"+mHR.sec);
			//return mStat;
		} catch(IOException e) {
			log("doHeart:"+e);//e.printStackTrace(); 
			return false;
		}
		return true;
	}

	int doSet(String did,String var,int type,String value) {
		log("now do Query did == "+did+" var == "+var);
		//msgSet q = new msgSet(did,var,localConfig.getInstance().getUname());
		msgSet q = new msgSet();
		q.var = var;
		q.value = value;
		q.did = did;
		q.uname = localConfig.getInstance().getUname();
		q.type = type;
		byte[] b = q.toByte();
		byte[] a = getHead(0x1c,b.length);
        byte[] ret = merge(a,b);
		try {
			if(mOut == null) {
				log("no mout");
				return -2;
			}
			mSR = null;
			mOut.write(ret);
			lock(setLock,20);
			if(mSR == null) {
				log("no mSR");
				return -1;
			}
			return mSR.result;
		} catch(IOException e) {
			log("doQuery:"+e);//e.printStackTrace(); 
			return -1;
		}
		
	}


	private void startHeart() {
		new Thread() {
			@Override
			public void run() {
				boolean stat = false;
				int sec = 0;
				if(!doHeart()) {
					sstop();
					return ;
				}

				while((mHR.result == 0)?true:false) {
					Sleep(mHR.sec*1000);
/*
					try {
						Thread.sleep(mHR.sec*1000);
					}catch(InterruptedException e) {

					}
*/
					if(!doHeart()) {
						sstop();
						return ;
					}
				}
			}
		}.start();
	}
	private void startRead() {
		new Thread() {
			@Override
			public void run() {
				while(true) {
					boolean ret = readPush();
					if(ret == false) {
						sstop();
						break;
					}
				}
			}

		}.start();
	}

	private void dispath(msgHead h,byte[] b) {
		log("dispath:"+h.command);
		if(h.command == 0x04) {
			mHR = new msgHeartRes(b);
			unlock(heartLock);
		}
		if(h.command == 0x1b) {
			mQR = new msgQueryRes(b);
			log("mQR error:"+mQR.error);
			unlock(queryLock);
		}
		if(h.command == 0x1d) {
			mSR = new msgSetRes(b);
			unlock(setLock);

		}
	}

	private boolean readPush() {
		log("now read Push");
		//byte buf[] = new byte[1024]; 
		//buf.clear(); 
		try {
			byte h[] = new byte[20]; 
			int ret = mIn.read(h,0,20);
			if(ret < 0)
				return false;
			msgHead head = new  msgHead(h);
			log("head.len:"+head.len);
			byte b[] = new byte[head.len];
			ret = mIn.read(b,0,head.len);
			if(ret < 0)
				return false;
			dispath(head,b);
		} catch(IOException e) {
			log(""+e);//e.printStackTrace(); 
			return false;
		}
		return true;
//buf.flip(); 
	}


	private boolean connectServer() { 
		log("tcpC connect");//e.printStackTrace(); 
		try { 
			mS = new Socket(UURL.ip, 7900);
			mOut = new DataOutputStream(mS.getOutputStream());
			mIn = new DataInputStream(mS.getInputStream());
			log("tcpC connected");//e.printStackTrace(); 
			return true;
        } catch (IOException e) { 
			log(""+e);//e.printStackTrace(); 
			mS = null;
			mOut = null;
			mIn = null;
			return false;
        } 
    } 
  
	public void DisConnectToServer() { 
		try { 
			if(mS != null) {
				mS.close(); 
				mS = null;
			}
			//mOut == null;
			//mIn == null;
		}catch (IOException e) { 
			e.printStackTrace(); 
		} 
	} 

	private void log(String line) {
		android.util.Log.d("tcpc",line);
	}

}
