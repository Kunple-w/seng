package com.github.seng.core.transport;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * netty服务端处理器
 *
 * @author wangyongxu
 */
public class ServerHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("收到消息: {}", msg);
        SengMessage message = (SengMessage) msg;
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SengMessage sengMessage = new SengMessage();
            sengMessage.setHeader(new SengProtocolHeader());
            sengMessage.setBody(new byte[0]);
            ctx.writeAndFlush(sengMessage);
            logger.info("write response: {}", sengMessage);
        }).start();
        //TODO 解析请求，异步变同步
        super.channelRead(ctx, msg);
    }
}
