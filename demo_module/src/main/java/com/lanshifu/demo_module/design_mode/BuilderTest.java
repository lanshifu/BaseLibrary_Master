package com.lanshifu.demo_module.design_mode;

import android.text.TextUtils;

public class BuilderTest {

    private String title;
    private String message;

    /**
     * dialog例子 原型模式
     */
    public BuilderTest(Builder builder) {
        this.title = builder.title;
        this.message = builder.message;
    }

    public void show() {
        if (!TextUtils.isEmpty(title)) {
//            LogUtils.debugInfo("title=" + title);
        }
        if (!TextUtils.isEmpty(message)) {
//            LogUtils.debugInfo("message=" + message);
        }
    }


    public static class Builder {
        private String title;
        private String message;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public BuilderTest build() {
            return new BuilderTest(this);
        }

    }
}
