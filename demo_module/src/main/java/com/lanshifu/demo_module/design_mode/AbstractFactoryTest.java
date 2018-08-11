package com.lanshifu.demo_module.design_mode;


/**
 * 演示抽象工厂模式
 */
public class AbstractFactoryTest {

    /**
     * 引擎接口
     */
    interface IEngine {
        void engine();
    }

    /**
     * 轮胎接口
     */
    interface ITire {
        void tire();
    }

    /**
     * 普通引擎A
     */
    static class EngineA implements IEngine {

        @Override
        public void engine() {
//            LogUtils.debugInfo("EngineA");
        }
    }

    /**
     * 高级引擎B
     */
    static class EngineB implements IEngine {

        @Override
        public void engine() {
//            LogUtils.debugInfo("EngineB");
        }
    }

    static class TireA implements ITire{

        @Override
        public void tire() {
//            LogUtils.debugInfo("TireA");
        }
    }

    static class TireB implements ITire{

        @Override
        public void tire() {
//            LogUtils.debugInfo("TireB");
        }
    }

    /**
     * 抽象车工厂
     */
    static abstract class CarFactory {

        abstract IEngine createEngine();

        abstract ITire createTire();
    }

    /**
     * 工厂A
     */
    static class CarFactoryA extends CarFactory{

        @Override
        IEngine createEngine() {
            return new EngineA();
        }

        @Override
        ITire createTire() {
            return new TireA();
        }
    }

    /**
     * 工厂B
     */
    static class CarFactoryB extends CarFactory{

        @Override
        IEngine createEngine() {
            return new EngineB();
        }

        @Override
        ITire createTire() {
            return new TireB();
        }
    }


    void test(){
//        CarFactory factory = new CarFactoryA();
        CarFactory factory = new CarFactoryB();
        factory.createEngine();
        factory.createTire();
    }
}
