package net.oschina.app.improve.bean;

import java.io.Serializable;

/**
 * 头条
 * Created by huanghaibin on 2017/10/23.
 */

public class Top implements Serializable{
    private int type;
    private String key;
    private String title;
    private String desc;
    private String href;
    private String pubDate;
    private String origin;
    private SubBean.Image[] images;

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public SubBean.Image[] getImages() {
        return images;
    }

    public void setImages(SubBean.Image[] images) {
        this.images = images;
    }
}
