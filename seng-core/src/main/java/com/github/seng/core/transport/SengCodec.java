//package com.github.seng.core.transport;
//
//import com.github.seng.core.exception.SengRuntimeException;
//import com.github.seng.core.serialize.Serializer;
//import com.github.seng.core.serialize.SerializerFactory;
//import com.github.seng.core.utils.ClassUtils;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.*;
//
///**
// * @author wangyongxu
// */
//public class SengCodec implements Codec {
//
//    public static final byte REQUEST_FLAG = 0x00;
//    public static final byte RESPONSE_FLAG = 0x10;
//    public static final byte ONE_WAY = 0x20;
//    public static final byte PING = 0x30;
//    public static final byte PONG = 0x40;
//
//    @Override
//    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws IOException {
//        if (msg instanceof Request) {
//            Request request = (Request) msg;
//            byte[] bytes = encodeRequest(request.getBody(), getSerializer(request.getHeader()));
//        } else if (msg instanceof Response) {
//
//        }
//    }
//
//    private Serializer getSerializer(SengProtocolHeader sengProtocolHeader) {
//        byte serializerId = sengProtocolHeader.getSerializerId();
//        return SerializerFactory.getSerializer(serializerId);
//    }
//
//    /**
//     * 编码调用方的请求
//     */
//    public byte[] encodeRequest(Invocation invocation, Serializer serializer) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ObjectOutput output = createOutput(outputStream);
//
//        output.writeUTF(invocation.getServiceName());
//        output.writeUTF(invocation.getMethodName());
//        output.writeUTF(invocation.getArgsDesc());
//
//        Object[] args = invocation.getArgs();
//
//        if (args != null) {
//            for (Object arg : args) {
//                if (arg == null) {
//                    output.writeObject(null);
//                } else {
//                    output.writeObject(serializer.serialize(arg));
//                }
//            }
//        }
//        byte[] bytes = outputStream.toByteArray();
//        output.close();
//        return bytes;
//    }
//
//    private ObjectInput createInput(InputStream in) {
//        try {
//            return new ObjectInputStream(in);
//        } catch (Exception e) {
//            throw new SengRuntimeException(this.getClass().getSimpleName() + " createInputStream error", e);
//        }
//    }
//
//    private ObjectOutput createOutput(OutputStream outputStream) {
//        try {
//            return new ObjectOutputStream(outputStream);
//        } catch (Exception e) {
//            throw new SengRuntimeException(this.getClass().getSimpleName() + " createOutputStream error", e);
//        }
//    }
//
//
//    /**
//     * 编码服务端的响应
//     */
//    private byte[] encodeResponse(Object body, Serializer serializer) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ObjectOutput output = createOutput(outputStream);
//        output.writeUTF(body.getClass().getName());
//        output.writeObject(serializer.serialize(body));
//        return outputStream.toByteArray();
//    }
//
//
//
//
//    @Override
//    public Object decode(Channel channel, ByteBuf input) throws Exception {
//        SengProtocolHeader header = new SengProtocolHeader();
//        header.setVersion(input.readByte());
//        byte b = input.readByte();
//        header.setMsgType((byte) ((b & 240) >>> 4));
//        header.setSerializerId((byte) (b & 15));
//        b = input.readByte();
//        header.setStatusCode((byte) ((b & 248) >>> 3));
//        header.setReqId(input.readLong());
//        int bodyLength = input.readInt();
//        header.setDataLength(bodyLength);
//        byte[] body = new byte[bodyLength];
//        input.getBytes(input.readerIndex(), body, 0, bodyLength);
//        Serializer serializer = SerializerFactory.getSerializer(header.getSerializerId());
//        if (header.getMsgType() == RESPONSE_FLAG) {
//            return decodeResponse(body, serializer);
//        } else if (header.getMsgType() == REQUEST_FLAG) {
//            return decodeRequest(body, serializer);
//        }
//        throw new IllegalArgumentException("not support msg type: " + header.getMsgType());
//    }
//}
