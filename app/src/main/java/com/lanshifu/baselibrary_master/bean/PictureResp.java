package com.lanshifu.baselibrary_master.bean;

import java.util.List;

public class PictureResp {

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 一個很騷的小網友[9P]
         * url : http://nbvvv.8iwvsl.com/110A02/p01/%E4%B8%80%E5%80%8B%E5%BE%88%E9%A8%B7%E7%9A%84%E5%B0%8F%E7%B6%B2%E5%8F%8B[9P]/01.jpg
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
