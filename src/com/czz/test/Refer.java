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
        DemoService demoService = Refer.refer(DemoService.class, "localhost", 8001);
        System.out.println(demoService.say("cccccc"));
    }

    public static <T> T refer(Class<T> interfaceClass, String host, int port){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ProxyDemo(host,port)
        );
    }
}
