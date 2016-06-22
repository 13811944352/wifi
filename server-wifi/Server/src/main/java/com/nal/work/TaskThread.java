package com.nal.work;

import com.nal.HttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import java.math.BigInteger;
import io.netty.channel.ChannelHandlerContext;
import com.nal.nodeConfig;
import com.nal.node;
import java.util.Timer;   
import java.util.TimerTask;
import com.nal.NettyChannelMap;


public class TaskThread { //extends Thread {
	private String mId;
	private ChannelHandlerContext mC;
	private nodeConfig[] mN = new nodeConfig[8];
	private Timer mT;
	private task mTask = new task(); 

	public TaskThread(String id,ChannelHandlerContext c) {
		mId = id;
		mC = c;
		mT = new Timer();
	}

	public void start() {
		mT.schedule(mTask,1*60*60,1*1000*60*60);
	}

	public void stop() {
		mT.cancel();
	}

	private void freshConfig() {
		node nn = null;
		try {
			nn = new node();
		} catch(Exception e) {
			log("get node err:"+e);
			return ;
		}
		for(int i = 0;i<8;i++) {
			int m = i+1;
			//nodeConfig n = nn.getNode(mId,""+m);
			if(mId.equals("deviceId")) {
				log(mId+"true");
			}else{
				log(mId+"false");
			}
			nodeConfig n = nn.getNode(mId.trim(),""+m);
			mN[i] = n;
		}

/*
		//http://127.0.0.1:8082/nal/getnode.jsp?did=deviceId&nid=1
		for(int i = 0;i<8;i++) {
		String url = "http://127.0.0.1:8082/nal/getnode.jsp?did="+mId+"&nid="+(i+1);
		log(url);
        String r = HttpUtil.httpGet(url);
		//String s = 
		log(r);
		}
		//return null;
*/
	}

	private int getTemp(nodeConfig n) {
		int type = n.nodeType;
        if(n.nodeTemp == -101) { //safe
			if(type == 0)
				return 20;
			if(type == 1)
				return 18;
			if(type == 2)
				return 18;
			if(type == 3)
				return 16;
			if(type == 4)
				return 18;
        }
        if(n.nodeTemp == -102) { //jieneng
			if(type == 0)
				return 18;
			if(type == 1)
				return 16;
			if(type == 2)
				return 16;
			if(type == 3)
				return 14;
			if(type == 4)
				return 18;
        }

        if(n.nodeTemp == -103) { //fangdong
            return 5;
        }
		return 5;
	}

	private void doTask(ChannelHandlerContext c,int id,nodeConfig n) {
		int temp = getTemp(n);
		byte[] set = NettyChannelMap.getSetWifi("settemp"+id,1,""+temp);
		NettyChannelMap.sendData(c,set);
	}

	class task extends TimerTask { 
		@Override  
		public void run() {  
			//System.out.println("dddd");  
			freshConfig();
			for(int i = 0;i<8;i++) {
				nodeConfig n = mN[i];
				if(n != null) {
					log("now i == "+i);
					doTask(mC,i+1,n);
					NettyChannelMap.sleep(1);
				} else {
					log("now n == null and i == "+i);
				}
			}
		} 
	}

    static void log(String line) {
            System.out.println(line);
    } 
/*
	@Override
	public void run () { 
		do {
		}while(true);
	}
*/
}

