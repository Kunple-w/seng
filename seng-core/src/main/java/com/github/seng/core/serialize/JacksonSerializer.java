package com.github.seng.core.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seng.common.exception.SengRuntimeException;

import java.io.IOException;

/**
 * @author wangyongxu
 */
public class JacksonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        try {
            return getObjectMapper().writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new SengRuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(bytes, clazz);
        } catch (IOException e) {
            throw new SengRuntimeException(e);
        }
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
