package com.czz.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-09-12 17:17:00
 * @description :
 */
public class ProxyDemo implements InvocationHandler {
    private int port;
    private String host;

    public ProxyDemo(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            Socket socket = new Socket(host,port);

            //传输方法
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            //传输方法名
            output.writeUTF(method.getName());
            //传输方法参数类型
            output.writeObject(method.getGenericParameterTypes());
            //传输方法参数值
            output.writeObject(args);

            //读取结果
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Object result = input.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
