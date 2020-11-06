package com.github.seng.core.transport;

import com.github.seng.common.exception.SengRuntimeException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author wangyongxu
 */
public class Client {

    private Thread thread;

    private Channel channel;

    private Bootstrap bootstrap;

    private ClientHandler clientHandler = new ClientHandler();

    /**
     * start a client
     *
     * @param inetSocketAddress
     */
    public void start(final InetSocketAddress inetSocketAddress) {
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                connect(inetSocketAddress);
//            }
//        }, "clientStartThread");
//        thread.start();
        connect(inetSocketAddress);
    }

    /**
     * stop a client
     */
    public void stop() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }


    public Response send(Request request) {
        channel.writeAndFlush(request);
        return clientHandler.getResponse(channel);
    }

    public Channel getChannel() {
        return channel;
    }

    public void disConnect() {
        channel.close();
    }


    private void connect(InetSocketAddress inetSocketAddress) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new LoggingHandler());
                    socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, 30L * 3, TimeUnit.SECONDS));
                    socketChannel.pipeline().addLast(new SengMessageEncoder());
                    socketChannel.pipeline().addLast(new SengMessageDecoder());
                    socketChannel.pipeline().addLast(clientHandler);
                }
            }).option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture channelFuture = bootstrap.connect(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
            channel = channelFuture.sync().channel();
        } catch (InterruptedException e) {
            throw new SengRuntimeException("start client falied.", e);
        } catch (Throwable t) {
            throw new SengRuntimeException("unknown error", t);
        }
    }


}
