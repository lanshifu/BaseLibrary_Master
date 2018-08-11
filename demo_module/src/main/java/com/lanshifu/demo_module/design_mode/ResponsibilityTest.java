package com.lanshifu.demo_module.design_mode;


/**
 * 责任链模式例子
 *
 */
public class ResponsibilityTest {

    /**
     * 报账的流程，
     */
    static abstract class Leader {

        //下个处理人，上一级领导
        protected Leader nextHandler;

        public void setNextHandler(Leader leader){
            nextHandler = leader;
        }


        /**
         * 报账的额度
         *
         * @return
         */
        public abstract int limit();

        /**
         * 处理报账的行为
         *
         * @param memery
         */
        public abstract void handle(int memery);

        public final void handleRequest(int money) {
            if (money < limit()) {
                handle(limit());
            } else {
                if (nextHandler != null) {
                    nextHandler.handleRequest(money);
                }
            }
        }

    }

    /**
     * 组长
     */
    static class GroupLeader extends Leader{

        @Override
        public int limit() {
            return 1000; //报账1000
        }

        @Override
        public void handle(int memery) {
//            Timber.d("组长处理报账");

        }
    }

    /**
     * 主管
     */
    static class Director extends Leader{

        @Override
        public int limit() {
            return 2000;
        }

        @Override
        public void handle(int memery) {
//            Timber.d("主管处理报账");
        }
    }

    /**
     * 老板
     */
    static class Boss extends Leader{

        @Override
        public int limit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void handle(int memery) {
//            Timber.d("老板处理报账");
        }
    }


    void  test(){

        GroupLeader groupLeader = new GroupLeader();
        Director director = new Director();
        Boss boss = new Boss();

        //设置责任链的下一个处理人，主管
        groupLeader.setNextHandler(director);
        director.setNextHandler(boss);

        //找组长报账
        groupLeader.handleRequest(2000);
        //组长不在，也可以直接找主管报账
//        director.handleRequest(2000);


    }

}
