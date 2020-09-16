package com.github.seng.core.transport;

import java.net.InetSocketAddress;

/**
 * 客户端
 *
 * @author wangyongxu
 */
public class Client {
    private InetSocketAddress inetSocketAddress;

    public Client(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    /**
     * 开启客户端
     * @throws Throwable
     */
    public void open() throws Throwable{

    }

    /**
     * 关闭客户端
     * @throws Throwable
     */
    public void close() throws Throwable{

    }

    /**
     * 客户端连接
     * @throws Throwable
     */
    public void connect() throws Throwable{

    }

    /**
     * 客户端断开连接
     * @throws Throwable
     */
    public void disconnect() throws Throwable{

    }
}
