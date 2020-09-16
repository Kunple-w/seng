package com.github.seng.core.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.List;

/**
 * 编解码器
 *
 * @author wangyongxu
 */
public class DefaultCodec implements Codec {

    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws IOException {

    }

    public Object decode(Channel channel, ByteBuf input) throws IOException {
        return null;
    }

    private class Encode extends MessageToByteEncoder<Object> {
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        }
    }

    private class Decode extends ByteToMessageDecoder {
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

            // TODO: 2020-09-16 05:14:05 编码器 by wangyongxu
        }
    }
}
