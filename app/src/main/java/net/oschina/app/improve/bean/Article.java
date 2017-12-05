package net.oschina.app.improve.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 头条
 * Created by huanghaibin on 2017/10/23.
 */

public class Article implements Serializable {
    public static final int TYPE_AD = 9999;//广告类型

    private int type;
    private String authorName;
    private String authorId;
    private String key;
    private String title;
    private String desc;
    private String url;
    private String pubDate;
    private String source;
    private String[] imgs;
    private Tag[] iTags;
    private int commentCount;
    private boolean favorite;

    @SerializedName("osc_id")
    private long oscId;

    @SerializedName("view_count")
    private int viewCount;

    public Tag[] getiTags() {
        return iTags;
    }

    public void setiTags(Tag[] iTags) {
        this.iTags = iTags;
    }

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }


    public long getOscId() {
        return oscId;
    }

    public void setOscId(long oscId) {
        this.oscId = oscId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
