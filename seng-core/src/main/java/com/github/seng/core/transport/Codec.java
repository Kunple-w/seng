package com.github.seng.core.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

/**
 * 编解码接口
 *
 * @author wangyongxu
 */
public interface Codec {

    void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws IOException;

    Object decode(Channel channel, ByteBuf input) throws IOException;

}
