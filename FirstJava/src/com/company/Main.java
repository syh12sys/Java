package com.company;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        System.out.println("FirstJava Application");

        Person zhangsan = new Student("张三");
        InvocationHandler stuHandler = new StuInvocationHandler<Person>(zhangsan);

        Person stuProxy = (Person)Proxy.newProxyInstance(
                Person.class.getClassLoader(), new Class<?>[]{Person.class}, stuHandler);
        // 交班费
        stuProxy.giveMemory();
        // 交作业
        stuProxy.giveJob();

        // 注解测试代码
        MyAnnotationPerson annotationPerson = new MyAnnotationPerson();
        annotationPerson.somebody("Lucy", 19);

//        Method[] proxyMethods = c.getMethods();
//        for (int i = 0; i < proxyMethods.length; ++i) {
//            System.out.println("方法列表:" + proxyMethods[i].getName());
//        }
        try {
            Class<MyAnnotationPerson> c = MyAnnotationPerson.class;
            Method sombodyMethod = c.getMethod("somebody", new Class[]{String.class, int.class});
            MyAnnnotion myAnnnotion = sombodyMethod.getAnnotation(MyAnnnotion.class);
            System.out.println("className=" + myAnnnotion.getClass().getName());
            System.out.println("superClassName=" + myAnnnotion.getClass().getSuperclass());
            System.out.println("value=" + myAnnnotion.value().toString());
            System.out.println("age=" + myAnnnotion.age());

            sombodyMethod.invoke(annotationPerson, new Object[]{"lily", 18});
        } catch (Exception e) {
            System.out.println("获取somebody方法异常: msg=" + e.toString());
        }

    }
}
