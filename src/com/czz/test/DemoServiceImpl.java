package com.czz.test;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2020-09-12 17:16:00
 * @description :
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String say(String name) {
        return "你好"+name;
    }
}
