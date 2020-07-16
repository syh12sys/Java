package com.company;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler<T> implements InvocationHandler {
    private T target;

    public StuInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理名字:" + proxy.getClass().getName());
        System.out.println("代理父类:" + proxy.getClass().getSuperclass());
//        Method[] proxyMethods = proxy.getClass().getMethods();
//        for (int i = 0; i < proxyMethods.length; ++i) {
//            System.out.println("代理方法列表:" + proxyMethods[i].getName());
//        }

        System.out.println("代理执行:" + method.getName() + "方法");

        // 代理过程插入检测方法，计算该方法耗时
        MonitorUtil.start();
        Object result = method.invoke(target, args);
        MonitorUtil.finish(method.getName());
        return result;
    }
}
