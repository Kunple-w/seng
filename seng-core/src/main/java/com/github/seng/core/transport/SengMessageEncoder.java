package com.github.seng.core.transport;

import com.github.seng.core.serialize.Serializer;
import com.github.seng.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * seng message encoder
 *
 * @author qiankewei
 */
public class SengMessageEncoder extends MessageToByteEncoder<SengMessage> {
    @Override
    public void encode(ChannelHandlerContext ctx, SengMessage msg, ByteBuf out) throws Exception {
        SengProtocolHeader header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        byte b = (byte) (((header.getMsgType() & 15) << 4) + (header.getSerializerId() & 15));
        out.writeByte(b);
        b = (byte) ((header.getStatusCode() & 7) << 3);
        out.writeByte(b);
        out.writeLong(header.getReqId());
        int lengthPos = out.writerIndex();
        out.writeInt(header.getDataLength());
        int bodyStartPos = out.writerIndex();
        Serializer serializer = SerializerFactory.getSerializer(header.getSerializerId());
        out.writeBytes(serializer.serialize(msg.getBody()));
        out.setInt(lengthPos, out.writerIndex() - bodyStartPos);
    }
}