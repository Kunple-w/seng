package com.github.seng.core.transport;

import com.github.seng.core.serialize.Serializer;
import com.github.seng.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * seng message encoder
 *
 * @author qiankewei
 */
public class SengMessageEncoder extends MessageToByteEncoder<Request> {
    private static final Logger logger = LoggerFactory.getLogger(SengMessageEncoder.class);

    @Override
    public void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        logger.info("encode: {}", msg);
        SengProtocolHeader header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        byte b = (byte) (((header.getMsgType() & 15) << 4) + (header.getSerializerId() & 15));
        out.writeByte(b);
        b = (byte) ((header.getStatusCode() & 7) << 3);
        out.writeByte(b);
        // requestId
        out.writeLong(header.getReqId());
        // body length
        out.writeInt(header.getDataLength());
        // serializer body
        Serializer serializer = SerializerFactory.getSerializer(header.getSerializerId());
        // write body
        out.writeBytes(serializer.serialize(msg.getBody()));
    }
}
