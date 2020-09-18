package com.github.seng.core.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author wangyongxu
 */
public class JacksonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        return getObjectMapper().writeValueAsBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        return getObjectMapper().readValue(bytes, clazz);
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
