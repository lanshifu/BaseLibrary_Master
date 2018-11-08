package com.lanshifu.demo_module.design_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 手写生产者消费者模式
 * Created by lanshifu on 2018/11/6.
 */

public class ProducerConsumerTest {



    /**
     * 仓库：提供生产和消费方法
     * @param <T>
     */
    public class Storage<T>{
        //记录仓库当前数量
        private int index = 0;
        private static final int MAX = 10;//最大容量
        private List<T> storages = new ArrayList<T>(MAX);//存储集合

        /**
         * 生产
         * @param item
         */
        public synchronized void produce(T item){

            while (index > MAX){

                System.out.println("【生产】仓库满了，暂停生产");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("【生产】仓库不满了，继续生产");
            }

            System.out.println("【生产】正在生产 ->" + item.toString());

            storages.add(item);
            index ++;
            notify(); //唤醒在此对象的的等待的线程（消费者）
        }

        /**
         * 消费
         * @return
         */
        public synchronized T consume(){
            if (index == 0){
                System.out.println("【消费】东西卖完了，等待仓库生产...");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("【消费】有货了，继续卖...");

            }

            index --;
            T remove = storages.remove(0);
            System.out.println("【消费】正在卖出->" +remove.toString());
            notify(); //卖出一个就唤醒正在等待的生产线程
            return remove;

        }



    }

    /**
     * 产品是手机
     */
    public class Phone {

        private int id;// 手机编号

        public Phone(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "手机编号：" + id;
        }
    }


    /**
     * 生产者
     */
    public class Producer implements Runnable{
        Storage<Phone> storage;

        public  Producer(Storage<Phone> storage){
            this.storage = storage;
        }
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                storage.produce(new Phone(i));
                try {
                    Thread.sleep(100); //100 毫秒生产一个
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    /**
     * 消费者
     */
    public class Consumer implements Runnable{
        Storage<Phone> storage;

        public  Consumer(Storage<Phone> storage){
            this.storage = storage;
        }
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                storage.consume();
                try {
                    Thread.sleep(1000); //一秒卖一个
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Test(){
        Storage<Phone> storage = new Storage<>();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
