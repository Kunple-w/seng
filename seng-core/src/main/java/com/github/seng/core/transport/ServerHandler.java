package com.github.seng.core.transport;

import com.github.seng.core.exception.SengRuntimeException;
import com.github.seng.core.rpc.exception.ServiceNotRegisterException;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty服务端处理器
 *
 * @author wangyongxu
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    public void registerService(Object service) {
        serviceMap.put(service.getClass().getInterfaces()[0].getName(), service);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("收到消息: {}", msg);
        if (msg instanceof Request) {
            Request request = (Request) msg;
            Object body = handleRequest(request);
            Response response = new Response(request, body);
            ctx.channel().writeAndFlush(response);
            return;
        }
//        SengMessage message = (SengMessage) msg;
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(4);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            SengMessage sengMessage = new SengMessage();
//            sengMessage.setHeader(new SengProtocolHeader());
//            sengMessage.setBody(new byte[0]);
//            ctx.writeAndFlush(sengMessage);
//            logger.info("write response: {}", sengMessage);
//        }).start();
        //TODO 解析请求，异步变同步
        super.channelRead(ctx, msg);
    }

    private Object handleRequest(Request request) {
        Invocation invocation = request.getBody();
        String serviceName = invocation.getServiceName();
        Method method = Invocations.parseMethod(invocation);
        Object service = getService(serviceName);
        if (service == null) {
            throw new ServiceNotRegisterException(serviceName + " not existed");
        }
        try {
            logger.info("service find: {}", service);
            Object result = method.invoke(service, invocation.getArgs());
            logger.info("service invoke result: {}", result);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SengRuntimeException(e);
        }
    }

    private Object getService(String serviceName) {
        return serviceMap.get(serviceName);
    }
}
