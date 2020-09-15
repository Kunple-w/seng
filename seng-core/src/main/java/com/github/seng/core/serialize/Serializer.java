package com.github.seng.core.serialize;

import java.io.IOException;

/**
 * 序列化接口
 * 将对象序列化为对象或者反序列化为byte数组
 *
 * @author wangyongxu
 */
public interface Serializer {
    /**
     * 将对象序列化为二进制
     *
     * @param obj : 要序列化的对象
     * @return byte[] : byte[]
     * @throws java.io.NotSerializableException 序列化失败时抛出异常
     * @throws IOException                      反序列化失败时抛出异常
     * @author wangyongxu
     */
    byte[] serialize(Object obj) throws IOException;

    /**
     * 将byte[]反序列化为对象
     *
     * @param bytes : byte数组
     * @param clazz : 反序列化的类
     * @return T
     * @throws IOException 反序列化失败时抛出异常
     * @author wangyongxu
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
