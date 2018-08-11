package com.lanshifu.demo_module.design_mode;

import android.widget.ImageView;

public class StragetyTest {

    /**
     * 图片加载策略
     */
    interface LoaderStragety {
        void loadImage(String path, ImageView imageView);
    }

    /**
     * glide 加载
     */
    static class GlideLoader implements LoaderStragety {

        @Override
        public void loadImage(String path, ImageView imageView) {
//            LogUtils.debugInfo("GlideLoader");
        }
    }

    /**
     * 毕加索加载
     */
    static class PicassLoader implements LoaderStragety {

        @Override
        public void loadImage(String path, ImageView imageView) {
//            LogUtils.debugInfo("PicassLoader");
        }
    }


    private LoaderStragety loader;

    /**
     * 设置策略
     * @param loader
     */
    public void setImageLoader(LoaderStragety loader) {
        this.loader = loader;
    }

    /**
     * 加载图片的方法
     * @param path
     * @param imageView
     */
    void loadImage(String path, ImageView imageView) {
        if (loader == null) {
            //默认使用glide
            loader = new GlideLoader();
        }
        loader.loadImage(path, imageView);
    }

    void test(){
        ImageView imageView = null;
        String url = null;
        loadImage(url,imageView);
    }
}
