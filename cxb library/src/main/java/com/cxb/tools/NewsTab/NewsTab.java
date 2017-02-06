package com.cxb.tools.newstab;

import java.io.Serializable;

/**
 * 新闻头部tab
 */

public class NewsTab implements Serializable {

    private String id;
    private String name;

    public NewsTab(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
