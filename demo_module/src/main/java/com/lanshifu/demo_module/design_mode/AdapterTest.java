package com.lanshifu.demo_module.design_mode;

/**
 * Created by lanshifu on 2018/11/30.
 */

public class AdapterTest {

    interface V220{
        int getV220();
    }

    interface V5{
        int getV5();
    }

    /**
     * 手机需要5v
     */
    class Phone implements V5{
        PhoneAdapter mPhoneAdapter;

        public void setPhoneAdapter(PhoneAdapter phoneAdapter) {
            mPhoneAdapter = phoneAdapter;
        }

        @Override
        public int getV5() {
            if (mPhoneAdapter != null){
                return mPhoneAdapter.getV5();
            }
            return 5;
        }
    }

    /**
     * 插座220v
     */
    class Socket implements V220{
        @Override
        public int getV220() {
            return 220;
        }
    }

    /**
     * 适配器
     */
    class PhoneAdapter implements V5{
        V220 v220;
        public PhoneAdapter(V220 v220) {
            this.v220 = v220;
        }

        public int getV220() {
            return v220.getV220();
        }


        @Override
        public int getV5() {
            return 5;
        }
    }

    void test(){
        Phone phone = new Phone();
        Socket socket = new Socket();
        PhoneAdapter phoneAdapter = new PhoneAdapter(socket);
        phone.setPhoneAdapter(phoneAdapter);
        phoneAdapter.getV5();


    }

}
