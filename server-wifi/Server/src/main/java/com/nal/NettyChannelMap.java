package com.nal;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import com.nal.msg.*;
import java.math.BigInteger;
import java.util.Map.Entry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import net.sf.json.*;

import com.nal.work.TaskThread;

import io.netty.channel.ChannelHandlerContext;

public class NettyChannelMap {
	public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }

   private static Map<ChannelHandlerContext,NettyChannel> mMap =new ConcurrentHashMap<ChannelHandlerContext, NettyChannel>();

	public static void dump() {
		for (Entry<ChannelHandlerContext,NettyChannel> entry : mMap.entrySet()) {
			ChannelHandlerContext s = (ChannelHandlerContext)entry.getKey();
			NettyChannel n = (NettyChannel)entry.getValue();
			log("mMap:"+n.id);
			//if(n.id.equals(id)) {
			//	return s;
			//}
		}	

	}

	public static ChannelHandlerContext getSocketChannel(String id) {
	//public static List<ChannelHandlerContext> getSocketChannel(String id) {
		log("getSocketChannel:"+id);
		ArrayList<ChannelHandlerContext> list = null;//= new ArrayList<>
		for (Entry<ChannelHandlerContext,NettyChannel> entry : mMap.entrySet()) {
			ChannelHandlerContext s = (ChannelHandlerContext)entry.getKey();
			NettyChannel n = (NettyChannel)entry.getValue();
			if(n.id.equals(id)) {
				return s;
			}
		}	
		return null;
	}
    public static void remove(ChannelHandlerContext socketChannel){
		log("remove channel");
        for (Map.Entry entry:mMap.entrySet()){
			log("mMap key:"+entry.getKey());
            if (entry.getKey()==socketChannel){
				NettyChannel n = (NettyChannel)entry.getValue();
				if(n.tt != null)
					n.tt.stop();
                mMap.remove(entry.getKey());
            }
        }
		dump();
    }

    public static void add(ChannelHandlerContext s){
		log("add channel");
		NettyChannel n = mMap.get(s);
		if(n == null) 
			return ;
        mMap.put(s,n);
		dump();
    }

    public static void add(ChannelHandlerContext s,String id){
		log("add s + id:"+id);
		NettyChannel n = mMap.get(s);
		if(n == null)
			return ;
		log("update id");
		n.id = id;
        mMap.put(s,n);
		dump();
    }

/*
    public static boolean doRes(ChannelHandlerContext s,byte[] b){
		boolean ret = add(s,b);
		if(ret) {
			return getRes(s);
		}
		return null;
	}
*/
    public static void add(ChannelHandlerContext s,byte[] b){
    //private static boolean add(ChannelHandlerContext s,byte[] b){
		log("add s+byte");
		NettyChannel n = mMap.get(s);
		log("n =="+n);
		if(n == null) {
			n = new NettyChannel();
			int magic = com.nal.msg.msg.byte2int(b);
			System.out.println("magic=="+magic);
			if(magic == 0x1a2b3c4d)
				n.type = 0;//wifi
			else if(magic == 0xa1b2c3d4)
				n.type = 1;//phoneo
			else if(magic == 0x4d3c2b1a)
				n.type = 2;//web server
			else {
				log("n invalid not add");
				return ;
			}
		}
        mMap.put(s,n);
		n.add(b);
		dump();
    }

	static byte[] merge(byte[] a,byte[] b) {
        byte[] ret = new byte[a.length+b.length];
        System.arraycopy(a,0,ret,0,a.length);
        System.arraycopy(b,0,ret,a.length,b.length);
        return ret;
	}

    static byte[] getRegRes(int magic) {
        msgHead h = new msgHead();
        //h.magic = 0x1a2b3c4d;
        h.magic = magic;
        h.version = 0;
        h.type = 0;
        h.command = 0x02;
        h.len = 8;

        msgRegRes rr = new msgRegRes();
        rr.result = 0;
        rr.sec = 5;
        byte[] brr = rr.toByte();
        byte[] bh = h.toByte();
        byte[] ret = new byte[20+8];
        System.arraycopy(bh,0,ret,0,20);
        System.arraycopy(brr,0,ret,20,8);
        System.out.print("body:\n");
        System.out.print(binary(ret,16));
        System.out.print("\n");
        return ret;

    }

    static byte[] getHeartRes(int magic) {
        msgHead h = new msgHead();
        //h.magic = 0x1a2b3c4d;
        h.magic = magic;
        h.version = 0;
        h.type = 0;
        h.command = 0x04;
        h.len = 8;

        msgHeartRes rr = new msgHeartRes();
        rr.result = 0;
        rr.sec = 200;
        byte[] brr = rr.toByte();
        byte[] bh = h.toByte();
        byte[] ret = new byte[20+8];
        System.arraycopy(bh,0,ret,0,20);
        System.arraycopy(brr,0,ret,20,8);
		
        //System.out.print("body:\n");
        //System.out.print(binary(ret,16));
        System.out.print("getHeartRes \n");
        return ret;
    }

	static byte[] getHead(int magic ,int command,int len) {
		msgHead h = new msgHead();
		h.magic = magic;
		h.command = command;
		h.version = 0;
		h.type = 0;
		h.len = len;
		return h.toByte();
	}	


	public static void sendData(ChannelHandlerContext s,byte[] ret) {
		ByteBuf b = Unpooled.buffer(ret.length);
		b.writeBytes(ret);  
		s.channel().writeAndFlush(b);//.addListener(mLi);
	}

    static byte[] getStatusRes() {
		//byte[] head = getHead(0x1a2b3c4d,0x06,4);
        msgHead h = new msgHead();
        h.magic = 0x1a2b3c4d;
        h.version = 0;
        h.type = 0;
        h.command = 0x06;
        h.len = 4;

        msgStatusRes rr = new msgStatusRes();
        rr.result = 0;
        byte[] brr = rr.toByte();
        byte[] bh = h.toByte();
        byte[] ret = new byte[20+4];
        System.arraycopy(bh,0,ret,0,20);
        System.arraycopy(brr,0,ret,20,4);
        System.out.print("body:\n");
        System.out.print(binary(ret,16));
        System.out.print("\n");
        return ret;
    }

	public static byte[] getSetRes(msgSetRes msg) {
		byte[] b = msg.toByte();
		byte[] a = getHead(0xa1b2c3d4,0x1d,b.length);
		return merge(a,b);
	}

	public static  byte[] getQueryRes(msgQueryRes msg) {
		byte[] b = msg.toByte();
		byte[] a = getHead(0xa1b2c3d4,0x1b,b.length);
		return merge(a,b);
	}

	public static byte[] getSetWifi(String var,int type,String value) {
		msgSetWifi msg = new msgSetWifi(var,type,value);
		byte[] b = msg.toByte();
		byte[] a = getHead(0x1a2b3c4d,0x09,b.length);
		return merge(a,b);
		//return msg.toByte[];
	}

	public static byte[] getQueryWifi(String var) {
		msgQueryWifi msg = new msgQueryWifi(var);
		byte[] b = msg.toByte();
		byte[] a = getHead(0x1a2b3c4d,0x07,b.length);
		return merge(a,b);
		//return msg.toByte[];
	}

	public static void sleep(int s) {
		try {
            Thread.sleep(1000*s);
        } catch (InterruptedException e) {
            e.printStackTrace(); 
        }
	}

	public static byte[] getRes(ChannelHandlerContext s) {
		log("getRes");
		NettyChannel n = mMap.get(s);
		if(n == null) {
			log("get n null");
			return null;
		}

		if(!n.check()) {
			return null;
		}

		byte[] h = n.get(20);
		if(h == null) {
			log("get h null");
			return null;
		}

		msgHead head = new msgHead(h);
		byte[] b = null;//n.get(head.len);
		while(true) {
			b  = n.get(head.len);
			if(b == null)
				sleep(1);
			else
				break;
		}

		log("head.command == "+head.command);

		if(head.command == 0x1c) {
			msgSet set = new msgSet(b);
			String uname = set.uname;
			String did = set.did;
			String var = set.var;
			int type = set.type;
			String value = set.value;
			ChannelHandlerContext wifi = getSocketChannel(did);
			if(wifi == null) {
				log("set wifi == null");
				msgSetRes msg = new msgSetRes(-1);
				byte[] ret = getSetRes(msg);
				return ret;
			} else {
				log("set wifi != null");
				NettyChannel nc = mMap.get(wifi);
				nc.query = s;
				byte[] ret = getSetWifi(var,type,value);
				sendData(wifi,ret);
			}
		}

		if(head.command == 0x1a) {
			msgQuery query = new msgQuery(b);
			String uname = query.uname;
			String did = query.devId;
			String var = query.var;
			log("msgQuery :"+uname + "-"+did+"-"+var);
			ChannelHandlerContext wifi = getSocketChannel(did);
			//NettyChannel n = mMap.get(wifi);
			//n.query = 
			if(wifi == null) {
				log("wifi == null");
				msgQueryRes msg = new msgQueryRes(-1);
				byte[] ret = getQueryRes(msg);
				return ret;
			} else {
				log("wifi != null");
				NettyChannel nc = mMap.get(wifi);
				nc.query = s;
				log("nc ph == "+nc.query);
				byte[] ret = getQueryWifi(var);
				sendData(wifi,ret);
			}
		}

		if(head.command == 0x0a) {
			NettyChannel nc = mMap.get(s);
			ChannelHandlerContext ph = nc.query;//= getSocketChannel(nc.query);
			log("0a nc  == "+nc);
			log("nc ph == "+ph);
			if(ph == null) {
				log("nc ph == null");
				return null;
			}
			msgSetWifiRes tmp= new msgSetWifiRes(b);
			msgSetRes msg = new msgSetRes(tmp.result);
			byte[] ret = getSetRes(msg);
			sendData(ph,ret);
			nc.query = null;
			mMap.put(s,nc);
		}

		if(head.command == 0x08) {
			NettyChannel nc = mMap.get(s);
			log("08 nc  == "+nc);
			ChannelHandlerContext ph = nc.query;//= getSocketChannel(nc.query);
			msgQueryWifiRes tmp= new msgQueryWifiRes(b);
			log("tmp:"+tmp.var);
			log("tmp:"+tmp.type);
			log("tmp:"+tmp.value);
			msgQueryRes msg = new msgQueryRes(tmp.var,tmp.type,tmp.value);
			log("msg :"+msg.var);
			log("msg :"+msg.type);
			log("msg :"+msg.value);
			log("msg :"+msg.error);
			byte[] ret = getQueryRes(msg);
			sendData(ph,ret);
			nc.query = null;
			mMap.put(s,nc);
		}

        if(head.command == 0x01) {
            msgReg reg = new msgReg(b);
            System.out.println("devName:"+reg.devName);
            System.out.println("devPwd:"+reg.devPwd);
            System.out.println("devId:"+reg.devId);
            System.out.println("devType:"+reg.devType);
            System.out.println("devFacId:"+reg.devFacId);
            System.out.println("FacName:"+reg.FacName);
            System.out.println("softVer:"+reg.softVer);
			add(s,reg.devId);


			NettyChannel nc = mMap.get(s);
			if(head.magic == 0x1a2b3c4d) {
				TaskThread tt = new TaskThread(reg.devId,s);
				tt.start();
				nc.tt = tt;
				mMap.put(s,nc);
			}

			byte[] ret = getRegRes(head.magic);
			return ret;
        }

        if(head.command == 0x03) {
            msgHeart heart = new msgHeart(b);
            System.out.println("heart devName:"+heart.devId);
            byte[] ret = getHeartRes(head.magic);
            //out.add(ret);
			return ret;
            //ByteBuf b = ctx.alloc().buffer();
            //b.writeBytes(ret);
            //ctx.channel().writeAndFlush(b).addListener(mLi);
        }
        if(head.command == 0x05) {
            msgStatus status = new msgStatus(b);
            System.out.println("status devName:"+status.devId);

			JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();


			for(int i = 0; i<status.list.size(); i++){
				msgStatus.status ss = status.list.get(i);
				log("name:"+ss.name+" value:"+ss.value);
                JSONObject o = new JSONObject();
                o.put(ss.name,ss.value);
				System.out.println("ja add:"+o.toString());
                ja.add(o);
			}
            jo.put("temp",ja);
            jo.put("deviceId",status.devId);
			String url = "http://127.0.0.1:8082/nal/tempHistory.jsp?temp="+jo.toString().trim();
			String r = HttpUtil.httpGet(url);
            System.out.println("status url:"+url);
            System.out.println("status ret:"+r);
            byte[] ret = getStatusRes();
            //out.add(ret);
			return ret;
            //ByteBuf b = ctx.alloc().buffer();
            //b.writeBytes(ret);
            //ctx.channel().writeAndFlush(b).addListener(mLi);
        }
		return null;
	}

	static void log(String line) {
            System.out.println(line);
	}

}
