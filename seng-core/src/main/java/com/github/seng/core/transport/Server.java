package com.github.seng.core.transport;

import com.github.seng.core.exception.SengRuntimeException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 服务端
 *
 * @author wangyongxu
 */
public class Server {

    private Thread thread;

    /**
     * start a server
     * @param inetSocketAddress
     */
    public void start(InetSocketAddress inetSocketAddress) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bind(inetSocketAddress);
            }
        }, "serverStartThread");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * close a server
     */
    public void stop() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }


    private void bind(InetSocketAddress inetSocketAddress) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS));
                    socketChannel.pipeline().addLast(new DefaultCodec());
                    socketChannel.pipeline().addLast(new ServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind(inetSocketAddress.getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new SengRuntimeException("start server fail.", e);
        } catch (Throwable t) {
            throw new SengRuntimeException("unknown error", t);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
