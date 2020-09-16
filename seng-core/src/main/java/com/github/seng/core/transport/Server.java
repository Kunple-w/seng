package com.github.seng.core.transport;

import java.net.InetSocketAddress;

/**
 * 服务端
 *
 * @author wangyongxu
 */
public class Server {

    private InetSocketAddress inetSocketAddress;

    public Server(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    /**
     * 开启服务端
     * @throws Throwable
     */
    public void open() throws Throwable {

    }


    /**
     * 关闭服务端
     * @throws Throwable
     */
    public void close() throws Throwable {

    }

}
