package com.github.seng.core.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * seng message decoder
 *
 * @author qiankewei
 */
public class SengMessageDecoder extends ByteToMessageDecoder {

    private static final short SENG_PROTOCOL_MAGIC = (short) 0xFDAC;

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        System.out.println("---------------");
        // TODO: 2020-09-16 05:14:05 编码器 by wangyongxu
        if (byteBuf.readableBytes() < 17) {
            return;
        }
        if (byteBuf.readableBytes() < (17 + byteBuf.getInt(13))) {
            return;
        }
        Short magic = byteBuf.readShort();
        if (magic != SENG_PROTOCOL_MAGIC) {
            //TODO 解决非法协议问题
        }
        SengProtocolHeader header = new SengProtocolHeader();
        header.setMagic(magic);
        header.setVersion(byteBuf.readByte());
        byte b = byteBuf.readByte();
        header.setMsgType((byte) ((b & 240) >>> 4));
        header.setSerializerId((byte) (b & 15));
        b = byteBuf.readByte();
        header.setStatusCode((byte) ((b & 248) >>> 3));
        header.setReqId(byteBuf.readLong());
        int bodyLength = byteBuf.readInt();
        header.setDataLength(bodyLength);
        byte[] body = new byte[bodyLength];
        byteBuf.getBytes(byteBuf.readerIndex(), body, 0, bodyLength);
        SengMessage sengMessage = new SengMessage(header, body);
        list.add(sengMessage);
    }
}
