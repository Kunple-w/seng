package com.github.seng.core.transport;

/**
 * customize protocol
 * <p>
 * =======================================================================================
 * magic | version | msg_type | serializer_id | status_code| remain | req_id | data_length
 * 16    | 8       | 4        | 4             | 5          | 3      | 64     | 32
 *
 * </p>
 *
 * @author qiankewei
 */
public class SengProtocolHeader {
    private short magic;
    private byte version;
    private byte msgType;
    private byte serializerId;
    private byte statusCode;
    private long reqId;
    private int dataLength;

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte getSerializerId() {
        return serializerId;
    }

    public void setSerializerId(byte serializerId) {
        this.serializerId = serializerId;
    }

    public byte getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(byte statusCode) {
        this.statusCode = statusCode;
    }

    public long getReqId() {
        return reqId;
    }

    public void setReqId(long reqId) {
        this.reqId = reqId;
    }
}
