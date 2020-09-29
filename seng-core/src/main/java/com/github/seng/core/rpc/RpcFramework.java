package com.github.seng.core.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class RpcFramework {
    /**
     * 暴露服务
     *
     * @param service 服务实现
     * @param port    服务端口
     * @throws Exception
     */
    public static void export(final Object service, int port) throws Exception {
        if (service == null) {
            throw new IllegalArgumentException("service instance == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
        // 建立Socket服务端
        ServerSocket server = new ServerSocket(port);
        for (; ; ) {
            try {
                // 监听Socket请求
                final Socket socket = server.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                /* 获取请求流，Server解析并获取请求*/
                                // 构建对象输入流，从源中读取对象到程序中
                                ObjectInputStream input = new ObjectInputStream(
                                        socket.getInputStream());
                                try {

                                    System.out.println("\nServer解析请求 ： ");
                                    String methodName = input.readUTF();
                                    System.out.println("methodName : " + methodName);
                                    // 泛型与数组是不兼容的，除了通配符作泛型参数以外
                                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                                    System.out.println(
                                            "parameterTypes : " + Arrays.toString(parameterTypes));
                                    Object[] arguments = (Object[]) input.readObject();
                                    System.out.println("arguments : " + Arrays.toString(arguments));


                                    /* Server 处理请求，进行响应*/
                                    ObjectOutputStream output = new ObjectOutputStream(
                                            socket.getOutputStream());
                                    try {
                                        // service类型为Object的(可以发布任何服务)，故只能通过反射调用处理请求
                                        // 反射调用，处理请求
                                        Method method = service.getClass().getMethod(methodName,
                                                parameterTypes);
                                        Object result = method.invoke(service, arguments);
                                        System.out.println("\nServer 处理并生成响应 ：");
                                        System.out.println("result : " + result);
                                        output.writeObject(result);
                                    } catch (Throwable t) {
                                        output.writeObject(t);
                                    } finally {
                                        output.close();
                                    }
                                } finally {
                                    input.close();
                                }
                            } finally {
                                socket.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 引用服务
     *
     * @param <T>            接口泛型
     * @param interfaceClass 接口类型
     * @param host           服务器主机名
     * @param port           服务器端口
     * @return 远程服务，返回代理对象
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port)
            throws Exception {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("Interface class == null");
        }
        // JDK 动态代理的约束，只能实现对接口的代理
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException(
                    "The " + interfaceClass.getName() + " must be interface class!");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null!");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        System.out.println(
                "Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);

        // JDK 动态代理
        T proxy = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new InvocationHandler() {
                    // invoke方法本意是对目标方法的增强，在这里用于发送RPC请求和接收响应
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] arguments)
                            throws Throwable {
                        // 创建Socket客户端，并与服务端建立链接
                        Socket socket = new Socket(host, port);
                        try {
                            /* 客户端像服务端进行请求，并将请求参数写入流中*/
                            // 将对象写入到对象输出流，并将其发送到Socket流中去
                            ObjectOutputStream output = new ObjectOutputStream(
                                    socket.getOutputStream());
                            try {
                                // 发送请求
//                                System.out.println("\nClient发送请求 ： ");
                                output.writeUTF(method.getName());
//                                System.out.println("methodName : " + method.getName());
                                output.writeObject(method.getParameterTypes());
//                                System.out.println("parameterTypes : " + Arrays.toString(method
//                                        .getParameterTypes()));
                                output.writeObject(arguments);
//                                System.out.println("arguments : " + Arrays.toString(arguments));


                                /* 客户端读取并返回服务端的响应*/
                                ObjectInputStream input = new ObjectInputStream(
                                        socket.getInputStream());
                                try {
                                    Object result = input.readObject();
                                    if (result instanceof Throwable) {
                                        throw (Throwable) result;
                                    }
                                    System.out.println("\nClient收到响应 ： ");
                                    System.out.println("result : " + result);
                                    return result;
                                } finally {
                                    input.close();
                                }
                            } finally {
                                output.close();
                            }
                        } finally {
                            socket.close();
                        }
                    }
                });
        return proxy;
    }
}