package com.github.seng.core.transport;

import com.github.seng.core.serialize.Serializer;
import com.github.seng.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * 编解码器
 *
 * @author wangyongxu
 */
public class DefaultCodec extends CombinedChannelDuplexHandler implements Codec {

    private static final short SENG_PROTOCOL_MAGIC = (short) 0xFDAC;
    private static final byte BUSSINESS_REQ = 0;
    private static final byte BUSSINESS_RES = 1;
    private static final byte ONE_WAY = 2;
    private static final byte HANDSHAKE_REQ = 3;
    private static final byte HANDSHAKE_RES = 4;
    private static final byte HEARTBEAT_REQ = 5;
    private static final byte HEARTBEAT_RES = 6;

    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof SengMessage) {
            Encode encode = new Encode();
            encode.encode(ctx, (SengMessage) msg, out);
        }

    }

    public Object decode(Channel channel, ByteBuf input) throws Exception {
        return null;
    }

    private class Encode extends MessageToByteEncoder<SengMessage> {
        protected void encode(ChannelHandlerContext ctx, SengMessage msg, ByteBuf out) throws Exception {
            SengProtocolHeader header = msg.getHeader();
            out.writeShort(header.getMagic());
            out.writeByte(header.getVersion());
            byte b = (byte) (((header.getMsgType() & 15) << 4) + (header.getSerializerId() & 15));
            out.writeByte(b);
            b = (byte) ((header.getStatusCode() & 7) << 5);
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

    private class Decode extends ByteToMessageDecoder {
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

            // TODO: 2020-09-16 05:14:05 编码器 by wangyongxu
            if (byteBuf.readableBytes() < 136) {
                return;
            }
            if (byteBuf.readableBytes() < (136 + byteBuf.getInt(104))) {
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
            byte[] body = byteBuf.readBytes(bodyLength).array();
            SengMessage sengMessage = new SengMessage(header, body);
            list.add(sengMessage);
        }
    }
}
