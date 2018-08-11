package com.lanshifu.demo_module.design_mode;


/**
 * 命令模式demo
 */
public class CommandTest {

    static class Machine {

        public void toLeft() {
//            LogUtils.debugInfo("toLeft");
        }

        public void toRight() {
//            LogUtils.debugInfo("toRight");
        }

    }

    interface ICommand{
        void excute();
    }


    static class LeftCommand implements ICommand{

        private Machine machine;

        public LeftCommand(Machine machine){
            this.machine = machine;
        }

        @Override
        public void excute() {
            machine.toLeft();
        }
    }

    static class RightCommand implements ICommand{

        private Machine machine;

        public RightCommand(Machine machine){
            this.machine = machine;
        }

        @Override
        public void excute() {
            machine.toRight();
        }
    }
}
