package com.troila.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
	
	// 业务处理逻辑
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf readBuffer = (ByteBuf) msg;
		// 创建�?个字节数组，用于保存缓存中的数据�?
		byte[] tempDatas = new byte[readBuffer.readableBytes()];
		// 将缓存中的数据读取到字节数组中�??
		readBuffer.readBytes(tempDatas);
		String message = new String(tempDatas, "UTF-8");
		System.out.println("from client : " + message);
		String line = "server message to client!";
		// 写操作自动释放缓存，避免内存溢出问题�?
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
	}
	

	// 异常处理逻辑
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		// cause.printStackTrace();
		ctx.close();
	}

}
