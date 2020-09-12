package com.czz.test;

import java.lang.reflect.Proxy;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-09-12 17:54:00
 * @description :
 */
public class Refer {
    public static void main(String[] args) {
        Refer.refer(DemoService.class, "localhost", 8001);
        new DemoServiceImpl().say();
    }
    public static <T> T refer(Class<T> interfaceClass, String host, int port){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ProxyDemo(host,port)
        );
    }
}
