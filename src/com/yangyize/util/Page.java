package com.yangyize.util;

/**
 * Page
 */
public class Page {

    private String url;
    private int offset;
    private String content;
    private String rawName;

    public Page() {
    }

    public String getUrl() {
        return url;
    }

    public int getOffset() {
        return offset;
    }

    public String getContent() {
        return content;
    }

    public String getRawName() {
        return rawName;
    }

    public Page(String url, int offset, String content, String rawName) {
        this.url = url;
        this.offset = offset;
        this.content = content;
        this.rawName = rawName;
    }

    public void setPage(String url, int offset, String content, String rawName) {
        this.url = url;
        this.offset = offset;
        this.content = content;
        this.rawName = rawName;
    }

    public void add2DB(DBConnection dbc) {
        String sql = "insert into pageindex(url, content, offset, raws)" +
                " values ('" + url + "', '" + content + "', '" + offset + "', '" + rawName + "')";
        dbc.executeUpdate(sql);
    }
}
