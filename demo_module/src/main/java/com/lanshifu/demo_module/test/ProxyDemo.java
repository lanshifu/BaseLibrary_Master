package com.lanshifu.demo_module.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 静态代理 和动态代理
 * Created by lanshifu on 2018/11/21.
 */

public class ProxyDemo {


    interface Subject{
        void request();
    }

    class RealSubject implements Subject{
        @Override
        public void request() {
            System.out.println("RealSubject request");
        }
    }

    /**
     * 静态代理
     * 就是对实现类再次包装的类。
     */
    class StaticProxy implements Subject{
        RealSubject mRealSubject;
        public StaticProxy(RealSubject realSubject){
         this.mRealSubject = realSubject;
        }
        @Override
        public void request() {
            mRealSubject.request();
        }
    }


    /**
     * 动态代理
     */
    public class DynamicFactory{
        public Object target;

        public DynamicFactory(Object target) {
            this.target = target;
        }

        public Object getProxyInstance(){
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        /**关联的这个实现类的方法被调用时将被执行InvocationHandler接口的invoke方法，
                         * proxy表示代理，method表示原对象被调用的方法，args表示方法的参数*/
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("调用目标方法前");
                            //调用目标方法
                            Object ret = method.invoke(target, args);
                            System.out.println("调用目标方法后");
                            return ret;
                        }
                    });
        }

    }


    public void test(){
        RealSubject realSubject = new RealSubject();
        Subject proxyInstance = (Subject) new DynamicFactory(realSubject).getProxyInstance();
        proxyInstance.request();
    }




}
