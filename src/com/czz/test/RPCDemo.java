package com.czz.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-09-12 17:14:00
 * @description :  RPC
 */
public class RPCDemo {
    public static void main(String[] args) {
        DemoService demoService = new DemoServiceImpl();
        export(8001, demoService);

    }


    public static void export(int port, Object o){
        try {
            BlockingQueue blockingQueue = new LinkedBlockingQueue();
            ExecutorService executorService =new ThreadPoolExecutor(
                    10,
                    20,
                    60,
                    TimeUnit.SECONDS,
                    blockingQueue);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            Thread thread = new Thread(new TaskDemo(socket, o));
            thread.start();
//            executorService.submit(new TaskDemo(socket, o));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


class TaskDemo implements Runnable{
    private Socket socket;
    private Object o;

    public TaskDemo(Socket socket, Object o) {
        this.socket = socket;
        this.o = o;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) input.readObject(); //参数类型
            Object[] arguments = (Object[]) input.readObject(); //参数
            Method method = socket.getClass().getMethod(methodName, parameterTypes);  //找到方法
            Object result = method.invoke(o, arguments); //调用方法
            //返回结果
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}