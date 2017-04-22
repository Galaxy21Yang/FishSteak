package com.yangyize.spider;

import com.yangyize.util.DBConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * main
 * <p>
 * 2/24 存在的问题：  1. 爬虫各个线程之间爬到的网页可能出现重复
 * 2. 单个爬取文件太大
 */
public class Spider {
    private static DBConnection dbc = new DBConnection();
    private ArrayList<URL> urls;
    private int gatherNum = 100; //线程数

    public Spider() {
    }

    public Spider(ArrayList<URL> urls) {
        this.urls = urls;
    }

    /**
     * 启动线程gather，收集网页资料
     */
    public void start() {
        Dispatcher disp = new Dispatcher(urls);
        for (int i = 0; i < gatherNum; i++) {
            Thread gather = new Thread(new Gather(String.valueOf(i), disp));
            gather.start();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<URL> urls = new ArrayList<URL>();
        try {
            urls.add(new URL("http://www.163.com"));
            urls.add(new URL("http://www.sina.com"));
            urls.add(new URL("http://www.sohu.com"));
            urls.add(new URL("http://www.qq.com"));
            urls.add(new URL("http://www.hao123.com"));
            for(URL url : urls){
                String sqlInsert = " INSERT INTO url_index(url,used) SELECT '" +
                        url.toString()+"','0' " +
                        "WHERE NOT EXISTS (select * from `url_index` where url = '" +
                        url.toString() +
                        "');";
                dbc.executeUpdate(sqlInsert);
            }
        } catch (MalformedURLException e) { //URL格式错误异常
            e.printStackTrace();
        }
        Spider spider = new Spider(urls);
        spider.start();
    }
}
