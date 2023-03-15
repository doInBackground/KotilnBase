package com.wcl.ktb.kotlin;

public class MyJavaClass {

    /**
     * 测试方法: 供Kotlin调用.
     *
     * @return 返回字符串类型.
     */
    public String testGetString() {
        return null;
    }


    /**
     * @Author WCL
     * @Date 2023/3/14 18:44
     * @Version
     * @Description 饿汉式线程安全单例.
     */
    public static class SingletonJavaClass1 {
        private static final SingletonJavaClass1 INSTANCE = new SingletonJavaClass1();//私有化类对象引用.

        private SingletonJavaClass1() {//私有化构造.
        }

        public static SingletonJavaClass1 getInstance() { //对外提供静态公有方法获取单例类对象.
            return INSTANCE; //单例对象.
        }
    }

    /**
     * @Author WCL
     * @Date 2023/3/14 18:44
     * @Version
     * @Description 懒汉式线程安全单例.
     */
    public static class SingletonJavaClass {
        private volatile static SingletonJavaClass INSTANCE;//私有化类对象引用.

        private SingletonJavaClass() {//私有化构造.
        }

        public static SingletonJavaClass getInstance() { //对外提供静态公有方法获取单例类对象.
            if (INSTANCE == null) {
                synchronized (SingletonJavaClass.class) { //同步锁,保证线程安全.
                    INSTANCE = new SingletonJavaClass();
                }
            }
            return INSTANCE; //单例对象.
        }
    }

}
