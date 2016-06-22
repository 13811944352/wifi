package com.nal;

import com.nal.work.TaskThread;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelHandlerContext;
import com.nal.msg.*;
public class NettyChannel {
	//public SocketChannel s;
	public static int MAX =  10240;
	public String id;
	//public ArrayList<String> status;
	//public String query;
	public ChannelHandlerContext query;
	public int    type;
    byte[] d; 
	int start;
	int end;
	public TaskThread tt;
    //byte[] d2; 
    NettyChannel() {
        d = new byte[MAX];
		start = 0;
		end = 0;
    }

	static void log(String s) {
		//System.out.println("NettyChannel:"+s);
	}


	int len() {
		if(end == start )
			return 0;
		if(end > start )
			return end - start;
		if(end < start) 
			return end + MAX - start;
		return 0;	
	}

	public boolean check() {
		int len = len();
		if(len < 20)
			return false;
		byte[] b = getT(20);
		msgHead head = new msgHead(b);
		if(len < (20+head.len))
			return false;
		return true;
	}

	public void add(byte[] b) {
		log("set start start:"+start+" end:"+end);
		int len = b.length;
		if(start == end) {
			start = 0;
			end = 0;
		}
		if(end+len > MAX) {
			System.arraycopy(b,0,d,end,(MAX-end));
			System.arraycopy(b,(MAX-end),d,0,(len-MAX+end));
			end += len;
			end -= MAX;
			//end = len-MAX+end;
			log("start:"+start+" end:"+end);
			return ;
		}
		System.arraycopy(b,0,d,end,len);
		end += len;
		log("set end start:"+start+" end:"+end);
	}

	private byte[] getT(int len) {
		log("get start start:"+start+" end:"+end + "len:"+len);
		byte[] b;
		if(start == end) {
			log("get fail start == end");
			return null;
		}
		if(end > start) {
			if(len > (end-start))
				return null;
			b = new byte[len];
			System.arraycopy(d,start,b,0,len);
			return b;
		}
		if((start+len) < (end+MAX))
			return null;
		b = new byte[len];
		System.arraycopy(d,start,b,0,(MAX-start));
		System.arraycopy(d,0,b,(MAX-start),(len - (MAX-start)));
		return b;

	}

	public byte[] get(int len) {
		log("get start start:"+start+" end:"+end + "len:"+len);
		byte[] b;
		if(start == end) {
			log("get fail start == end");
			return null;
		}
		if(end > start) {
			if(len > (end-start))
				return null;
			b = new byte[len];
			System.arraycopy(d,start,b,0,len);
			start += len;
			log("get end start:"+start+" end:"+end);
			return b;
		}
		if((start+len) < (end+MAX))
			return null;
		b = new byte[len];
		System.arraycopy(d,start,b,0,(MAX-start));
		System.arraycopy(d,0,b,(MAX-start),(len - (MAX-start)));
		start += len;
		start -= MAX;
		log("get end start:"+start+" end:"+end);
		return b;
	}

        //public getChannel
}
