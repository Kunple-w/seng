package com.github.seng.core.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.List;

/**
 * 编解码器
 *
 * @author wangyongxu
 */
public class DefaultCodec extends CombinedChannelDuplexHandler implements Codec {

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
            if (byteBuf.readableBytes() < 136) {
                return;
            }
            SengProtocolHeader sengProtocolHeader = new SengProtocolHeader();
            sengProtocolHeader.setMagic(byteBuf.readShort());
            sengProtocolHeader.setVersion(byteBuf.readByte());
            byte b = byteBuf.readByte();
            sengProtocolHeader.setMsgType((byte) ((b & 240) >>> 4));
            sengProtocolHeader.setSerializerId((byte) (b & 15));
            b = byteBuf.readByte();
            sengProtocolHeader.setStatusCode((byte) ((b & 248) >>> 3));
            sengProtocolHeader.setReqId(byteBuf.readLong());
            int bodyLength = byteBuf.readInt();
            sengProtocolHeader.setDataLength(bodyLength);
            list.add(sengProtocolHeader);
            if (byteBuf.readableBytes() < bodyLength) {
                return;
            }
            ByteBuf body = byteBuf.readBytes(bodyLength);
            list.add(body);

        }
    }
}
