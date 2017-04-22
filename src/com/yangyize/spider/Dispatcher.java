package com.yangyize.spider;

import com.yangyize.util.DBConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * URL调度
 */
public class Dispatcher {
    private DBConnection dbc = new DBConnection();
    private static ArrayList<URL> urls = new ArrayList<URL>();
    private static ArrayList<URL> visitedURLS = new ArrayList<URL>();

    public Dispatcher(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public synchronized URL getURL() {
//        while (urls.isEmpty()) {
        String sqlCount = "select count(*) from url_index where used = 0;";
        while (dbc.getFirstInteger(sqlCount) <= 0) {
            try {
                wait(); //等待生产中写入数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String sqlUnusedUrlRead = "select * from url_index where used = 0 limit 1;";
        URL url = null;
        try {
            url = new URL(dbc.getFirstString(sqlUnusedUrlRead));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        this.notify();
//        URL url = urls.get(0);
//        urls.remove(url);
//        visitedURLS.add(url);

        String sqlChangeUsed = "update url_index set used=1 where url = '" +
                url.toString() + "';";

        dbc.executeUpdate(sqlChangeUsed);
        System.out.println("【Spider】URL已读取");
        System.out.println("*****"+dbc.getFirstString(sqlUnusedUrlRead));

        return url;
    }

    public synchronized void insert(URL url) {
        if (!urls.contains(url) && !visitedURLS.contains(url)) {
            System.out.println("【Spider】添加URL :" + url.toString());
            urls.add(url);

        }
        String sqlInsert = " INSERT INTO url_index(url,used) SELECT '" +
                url.toString()+"','0' " +
                "WHERE NOT EXISTS (select * from `url_index` where url = '" +
                url.toString() +
                "');";
        dbc.executeUpdate(sqlInsert);
        System.out.println("【Spider】executeUpdate() 运行成功！");

    }

    public synchronized void insert(ArrayList<URL> analyzedURL) {
        for (URL url : analyzedURL) {
            insert(url);
        }
    }
}
