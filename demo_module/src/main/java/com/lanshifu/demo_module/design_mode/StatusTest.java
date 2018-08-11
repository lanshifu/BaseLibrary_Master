package com.lanshifu.demo_module.design_mode;


/**
 * 状态模式
 * 遥控器例子
 */
public class StatusTest {

    interface TvStatus{
        void pre();
        void next();
    }

    static class OpenStatus implements TvStatus{

        @Override
        public void pre() {
//            LogUtils.debugInfo("pre");
        }

        @Override
        public void next() {
//            LogUtils.debugInfo("next");
        }

    }

    /**
     * 关闭状态按钮没反应
     */
    static class CloseStatus implements TvStatus{

        @Override
        public void pre() {
        }

        @Override
        public void next() {
        }
    }

    /**
     * 开关接口
     */
    interface IpowerController{
        void on();
        void off();
    }

    /**
     * 遥控器
     */
    static class TvController implements IpowerController{
        TvStatus tvStatus = new CloseStatus();

        @Override
        public void on() {
            tvStatus = new OpenStatus();
        }

        @Override
        public void off() {
            tvStatus = new CloseStatus();
        }

        void next(){
            tvStatus.next();
        }

        void pre(){
            tvStatus.pre();
        }
        //responsibility 责任链模式
    }
}
