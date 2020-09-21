package com.github.seng.core.transport;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * netty服务端处理器
 *
 * @author wangyongxu
 */
public class ServerHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SengMessage message = (SengMessage)msg;
        //TODO 解析请求，异步变同步
        super.channelRead(ctx, msg);
    }
}
