package net.oschina.app.improve.bean;

import java.io.Serializable;

/**
 * 头条
 * Created by huanghaibin on 2017/10/23.
 */

public class Article implements Serializable{
    private int type;
    private String key;
    private String title;
    private String desc;
    private String url;
    private String pubDate;
    private String source;
    private String[] imgs;
    private int commentCount;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
