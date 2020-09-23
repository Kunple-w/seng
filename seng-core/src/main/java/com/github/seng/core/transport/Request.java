package com.github.seng.core.transport;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangyongxu
 */

@Data
public class Request {

    private static final AtomicLong REQUEST_ID = new AtomicLong(0);
    private SengProtocolHeader header;
    private Object body;

    public Request(SengProtocolHeader header, Object body) {
        this.header = header;
        this.body = body;
    }

    public Request(Object body) {
        this.header = sengProtocolHeader(newReqId());
        this.body = body;
    }

    private SengProtocolHeader sengProtocolHeader(long reqId) {
        SengProtocolHeader sengProtocolHeader = new SengProtocolHeader();
        sengProtocolHeader.setMsgType(SengProtocolHeader.REQUEST);
        sengProtocolHeader.setSerializerId((byte) 1);
        sengProtocolHeader.setReqId(reqId);
        return sengProtocolHeader;
    }

    private long newReqId() {
        return REQUEST_ID.getAndIncrement();
    }
}
