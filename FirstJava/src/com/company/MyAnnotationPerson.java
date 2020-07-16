package com.company;

/**
 * Person类。它会使用MyAnnotation注解。
 */
public class MyAnnotationPerson {
    @Deprecated
    public void empty() {
        System.out.println("empty");
    }

    @MyAnnnotion(value = {"girl", "boy"}, age = 18)
    public void somebody(String name, int age) {
        System.out.println("sombody:name=" + name + " age=" + age);
    }
}
