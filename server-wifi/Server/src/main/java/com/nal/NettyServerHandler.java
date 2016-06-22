package com.nal;

//import com.yao.module.*;
import com.nal.msg.msg;
import com.nal.msg.*;
import com.nal.msg.msgHead;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import java.math.BigInteger;  


public class NettyServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive:"+ctx);
        NettyChannelMap.remove(ctx);
    }

	ChannelFutureListener mLi = new ChannelFutureListener() {
		public void operationComplete(ChannelFuture future) throws Exception {
			if (future.isSuccess()) {
				System.out.println("callback : su" );
            } else {
                System.out.println("callback: fail");
            }
        }
    };


	public static void log(String line) {
		System.out.println(line);
	}

    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  

    @Override
	public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        log("channelRead:"+ctx);
		ByteBuf in = (ByteBuf)o;
        System.out.println("codec"+in.toString());
        byte[] h = new byte[in.readableBytes()];
        in.readBytes(h);//, 0, in.capacity()); 

        NettyChannelMap.add(ctx,h);
        while(true){
			byte[] ret = NettyChannelMap.getRes(ctx);
			if(ret == null) {
				log("**************************");
				return ;
			}
			log(binary(ret,16));
			ByteBuf b = ctx.alloc().buffer();  
			b.writeBytes(ret);  
			ctx.channel().writeAndFlush(b).addListener(mLi);
		}
	}
}
