package net.oschina.app.improve.bean;

import java.io.Serializable;

/**
 * 用户标签
 * Created by haibin on 2018/05/28.
 */
public class Tags implements Serializable{
    private int id;
    private String name;
    private boolean related;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRelated() {
        return related;
    }

    public void setRelated(boolean related) {
        this.related = related;
    }
}
