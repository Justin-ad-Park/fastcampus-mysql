package com.example.javaLang.trywithresources;

import org.junit.jupiter.api.Test;

public class TryWithResourceTest {

    @Test
    void manualClose() {
        MyResource myResource = new MyResource();
        try {
            myResource.doSomething();
        } finally {
            if(myResource != null)
                myResource.close();
        }
    }
    /**
     * [Console output]
     * Doing something
     * Closed MyResource
     *
     * java.lang.IllegalStateException
     * 	at com.example.javaLang.trywithresources.MyResource.close(MyResource.java:6)
     * 	at com.example.javaLang.trywithresources.TryWithResourceTest.manualClose(TryWithResourceTest.java:14)
     */

    @Test
    void tryWithResourceClose() {
        try (MyResource myResource = new MyResource()) {
            myResource.doSomething();
        }
    }
    /**
     * [Console output]
     * Doing something
     * Closed MyResource
     *
     * java.lang.IllegalStateException
     * 	at com.example.javaLang.trywithresources.MyResource.doSomething(MyResource.java:11)
     * 	at com.example.javaLang.trywithresources.TryWithResourceTest.tryWithResourceClose(TryWithResourceTest.java:30)
     */


    @Test
    void manualCloseWithMultiple() {
        MyResource resource1 = null;
        MyResource resource2 = null;

        try {
            resource1 = new MyResource();
            resource2 = new MyResource();
            resource1.doSomething();
            resource2.doSomething();
        } finally {
            if (resource1 != null) {
                resource1.close();  //IllegalStateException 발생
            }

            // 예외로 인해 resource2가 close되지 않음
            if (resource2 != null) {
                resource2.close();
            }
        }
    }

    @Test
    void manualCloseWithMultiple2() {
        MyResource resource1 = null;
        MyResource resource2 = null;

        try {
            resource1 = new MyResource();
            resource2 = new MyResource();
            resource1.doSomething();
            resource2.doSomething();
        } finally {
            if (resource1 != null) {
                try {
                    resource1.close();  //IllegalStateException 발생
                } catch (IllegalStateException e) {
                    System.out.println("예외 catch IllegalStateException");
                }
            }

            if (resource2 != null) {
                try {
                    resource2.close();  //IllegalStateException 발생
                } catch (IllegalStateException e) {
                    System.out.println("예외 catch IllegalStateException");
                }
            }
        }
    }

    @Test
    void tryWithResourceCloseWithMultiple() {
        try (MyResource resource1 = new MyResource();
             MyResource resource2 = new MyResource()) {
            resource1.doSomething();
            resource2.doSomething();
        }
    }
}
