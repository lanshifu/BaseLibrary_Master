package com.lanshifu.article_module.bean;

/**
 * Created by lanshifu on 2018/11/30.
 */

public class ArticleBean {

    /**
     * id : 34
     * title : 淫男乱女3【完】（作者：不详）
     * url : http://www.x4cs.com/dsjq/9656/
     * content :
     */

    private int id;
    private String title;
    private String url;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
