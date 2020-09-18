package com.github.seng.core.transport;

import com.github.seng.core.serialize.JacksonSerializer;
import com.github.seng.core.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

/**
 * @author wangyongxu
 */
public class SengCodec implements Codec {
    private Serializer serializer = new JacksonSerializer();

    @Override
    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws IOException {

    }

    @Override
    public Object decode(Channel channel, ByteBuf input) throws IOException {
        return null;
    }
}
