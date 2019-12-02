package com.lanshifu.demo_module.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ReentranLockTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.incrementAndGet();//自增

        ReentrantLock reentrantLock = new ReentrantLock(true);
        Thread thread1 = new Thread_tryLock(reentrantLock);
        thread1.setName("thread1");
        thread1.start();
        Thread thread2 = new Thread_tryLock(reentrantLock);
        thread2.setName("thread2");
        thread2.start();


//        Thread_Condition thread_condition = new Thread_Condition();
//        thread_condition.setName("测试Condition的线程");
//        thread_condition.start();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread_condition.singal();

    }

    static class Thread_tryLock extends Thread {
        ReentrantLock reentrantLock;

        public Thread_tryLock(ReentrantLock reentrantLock) {
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            try {
                System.out.println("try lock:" + Thread.currentThread().getName());
                boolean tryLock = reentrantLock.tryLock(3, TimeUnit.SECONDS);
                if (tryLock) {
                    System.out.println("try lock success :" + Thread.currentThread().getName());
                    System.out.println("睡眠一下：" + Thread.currentThread().getName());
                    Thread.sleep(5000);
                    System.out.println("醒了：" + Thread.currentThread().getName());
                } else {
                    System.out.println("try lock 超时 :" + Thread.currentThread().getName());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("unlock:" + Thread.currentThread().getName());
                reentrantLock.unlock();
            }
        }
    }


    static class Thread_Condition extends Thread {

        @Override
        public void run() {
            await();
        }

        private ReentrantLock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();

        public void await() {
            try {
                System.out.println("lock");
                lock.lock();
                System.out.println(Thread.currentThread().getName() + ":我在等待通知的到来...");
//                condition.await();//await 和 signal 对应
                condition.await(2, TimeUnit.SECONDS); //设置超时
                System.out.println(Thread.currentThread().getName() + ":等到通知了，我继续执行>>>");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("unlock");
                lock.unlock();
            }
        }

        public void singal() {
            try {
                System.out.println("lock");
                lock.lock();
                System.out.println("我要通知在等待的线程，condition.signal()");
                condition.signal();//await 和 signal 对应
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("unlock");
                lock.unlock();
            }
        }
    }
}
