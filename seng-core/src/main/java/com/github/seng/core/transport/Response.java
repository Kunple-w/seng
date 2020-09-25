package com.github.seng.core.transport;

import lombok.Data;

/**
 * @author wangyongxu
 */
@Data
public class Response {
    private SengProtocolHeader header;
    private Object body;

    public Response() {
    }

    public Response(SengProtocolHeader header, Object body) {
        this.header = header;
        this.body = body;
    }

    public boolean isResponse() {
        return header.getMsgType() == SengProtocolHeader.RESPONSE;
    }

    public long getRequestId() {
        return header.getReqId();
    }
}
