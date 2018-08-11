package com.lanshifu.demo_module.design_mode;

import java.util.HashMap;

/**
 * 观察者模式简单演示
 */
public class ObserverTest {

    /**
     * 观察者
     */
    interface Observer {
        void update();
    }

    /**
     * 被观察者
     */
    interface Observable {
        void register(Observer observer);

        void unRegister(Observer observer);

        void notifyChange();
    }


    static class MyOberver1 implements Observer {

        @Override
        public void update() {
            System.out.println("MyOberver1 update");
        }
    }

    static class MyOberver2 implements Observer {

        @Override
        public void update() {
            System.out.println("MyOberver2 update");
        }
    }

    static class MyObservable implements Observable {

        HashMap<String,Observer> map = new HashMap<>();

        @Override
        public void register(Observer observer) {
            System.out.println("MyObservable register " +observer);
            if (!map.containsKey(observer.toString())) {
                map.put(observer.toString(),observer);
            }
        }

        @Override
        public void unRegister(Observer observer) {
            System.out.println("MyObservable unRegister " +observer);
            if (map.containsKey(observer.toString())) {
                map.remove(observer.toString());
            }
        }

        @Override
        public void notifyChange() {
            System.out.println("MyObservable notifyChange ");
            for (String s : map.keySet()) {
                Observer observer = map.get(s);
                observer.update();
            }
        }
    }


    public void test() {
        MyOberver1 myOberver1 = new MyOberver1();
        MyOberver2 myOberver2 = new MyOberver2();
        MyObservable myObservable = new MyObservable();
        //订阅
        myObservable.register(myOberver1);
        myObservable.register(myOberver2);

        myObservable.notifyChange();
        myObservable.unRegister(myOberver1);
        myObservable.notifyChange();
    }
}
