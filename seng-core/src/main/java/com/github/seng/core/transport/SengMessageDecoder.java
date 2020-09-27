package com.github.seng.core.transport;

import com.github.seng.core.exception.SengRuntimeException;
import com.github.seng.core.exception.UnsupportedProtocolException;
import com.github.seng.core.serialize.Serializer;
import com.github.seng.core.serialize.SerializerFactory;
import com.github.seng.core.utils.ClassUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * seng message decoder
 *
 * @author qiankewei
 */
public class SengMessageDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(SengMessageDecoder.class);

    public static final byte REQUEST_FLAG = 0x00;
    public static final byte RESPONSE_FLAG = 0x10;
    public static final byte ONE_WAY = 0x20;
    public static final byte PING = 0x30;
    public static final byte PONG = 0x40;

    private static final short SENG_PROTOCOL_MAGIC = (short) 0xFDAC;

    private Class<?> aClass;

    public SengMessageDecoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 17) {
            return;
        }
        if (byteBuf.readableBytes() < (17 + byteBuf.getInt(13))) {
            return;
        }
        short magic = byteBuf.readShort();
        if (magic != SENG_PROTOCOL_MAGIC) {
            throw new UnsupportedProtocolException("magic: " + magic);
        }
        SengProtocolHeader header = new SengProtocolHeader();
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

        Serializer serializer = SerializerFactory.getSerializer(header.getSerializerId());
        if (RESPONSE_FLAG == header.getMsgType()) {
            Invocation o = decodeRequest(body, serializer);
            list.add(new Request(header, o));
        } else if (RESPONSE_FLAG == header.getMsgType()) {
            Object o = decodeResponse(body, serializer);
            list.add(new Response(header, o));
        } else {
            logger.error("not support msg type: {}", header.getMsgType());
        }
    }

    /**
     * 解码客户端的请求
     */
    public Invocation decodeRequest(byte[] body, Serializer serializer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput input = createInput(inputStream);
        String serviceName = input.readUTF();
        String methodName = input.readUTF();
        String argsDesc = input.readUTF();

        Object[] args;
        if (StringUtils.isEmpty(argsDesc)) {
            args = null;
        } else {
            String[] split = argsDesc.split(",");
            args = new Object[split.length];
            for (int i = 0; i < split.length; i++) {
                Class<?> aClass = ClassUtils.getClass(split[i]);
                args[i] = serializer.deserialize((byte[]) input.readObject(), aClass);
            }
        }

        Invocation invocation = new Invocation();
        invocation.setServiceName(serviceName);
        invocation.setMethodName(methodName);
        invocation.setArgs(args);
        return serializer.deserialize(body, Invocation.class);
    }


    /**
     * 解码服务端的响应
     */
    private Object decodeResponse(byte[] body, Serializer serializer) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput input = createInput(inputStream);
        String serviceName = input.readUTF();
        Class<?> aClass = ClassUtils.getClass(serviceName);
        return serializer.deserialize(body, aClass);
    }

    private ObjectInput createInput(InputStream in) {
        try {
            return new ObjectInputStream(in);
        } catch (Exception e) {
            throw new SengRuntimeException(this.getClass().getSimpleName() + " createInputStream error", e);
        }
    }

}
