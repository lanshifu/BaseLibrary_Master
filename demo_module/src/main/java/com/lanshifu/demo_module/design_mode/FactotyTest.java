package com.lanshifu.demo_module.design_mode;

public class FactotyTest {

    interface Product{
        void method();
    }

    /**
     * 产品a
     */
    static class ProductA implements Product{
        @Override
        public void method() {
//            LogUtils.debugInfo("ProductA");
        }
    }

    static class ProductB implements Product{
        @Override
        public void method() {
//            LogUtils.debugInfo("ProductB");
        }
    }

    /**
     * 工厂类
     */
    static class Factory{
        //创建产品
        public static Product createProduct(){
            return new ProductA();
        }

        public static Product createProductB(){
            return new ProductB();
        }
    }

    void test(){
        Product product = Factory.createProduct();
        product.method();

        Product productb = Factory.createProductB();
        productb.method();

    }
}
