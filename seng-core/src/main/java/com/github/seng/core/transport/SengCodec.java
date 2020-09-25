package com.github.seng.core.transport;

import com.github.seng.core.exception.SengRuntimeException;
import com.github.seng.core.serialize.JacksonSerializer;
import com.github.seng.core.serialize.Serializer;
import com.github.seng.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author wangyongxu
 */
public class SengCodec implements Codec {

    @Override
    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws IOException {
        if (msg instanceof Request) {
            byte[] bytes = encodeRequest((Request) msg);

        }

    }

    /**
     * 编码调用方的请求
     */
    public byte[] encodeRequest(Request request) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput output = createOutput(outputStream);
        Invocation invocation = request.getBody();

        output.writeUTF(invocation.getServiceName());
        output.writeUTF(invocation.getMethodName());
        output.writeUTF(invocation.getArgsDesc());

        Serializer serializer = SerializerFactory.getSerializer(request.getHeader().getSerializerId());
        Object[] args = invocation.getArgs();

        if (args != null) {
            for (Object arg : args) {
                if (arg == null) {
                    output.writeObject(null);
                } else {
                    output.writeObject(serializer.serialize(arg));
                }
            }
        }
        byte[] bytes = outputStream.toByteArray();
        output.close();
        return bytes;
    }

    private ObjectInput createInput(InputStream in) {
        try {
            return new ObjectInputStream(in);
        } catch (Exception e) {
            throw new SengRuntimeException(this.getClass().getSimpleName() + " createInputStream error", e);
        }
    }

    private ObjectOutput createOutput(OutputStream outputStream) {
        try {
            return new ObjectOutputStream(outputStream);
        } catch (Exception e) {
            throw new SengRuntimeException(this.getClass().getSimpleName() + " createOutputStream error", e);
        }
    }


    /**
     * 编码服务端的响应
     */
    private byte[] encodeResponse(Object body, Serializer serializer) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput output = createOutput(outputStream);
        output.writeUTF(body.getClass().getName());
        output.writeObject(serializer.serialize(body));
        return outputStream.toByteArray();
    }

    /**
     * 解码客户端的请求
     */
    public Object decodeRequest(byte[] body, Serializer serializer) throws IOException, ClassNotFoundException {
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
    private Object decodeResponse(byte[] body, Serializer serializer, long requestId) throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput input = createInput(inputStream);
        String serviceName = input.readUTF();
        serializer.deserialize(byte,)


    }


    @Override
    public Object decode(Channel channel, ByteBuf input) throws IOException {
        return null;
    }
}
