package com.yangyize.spider;

import java.net.URL;
import java.util.ArrayList;

/**
 * URL调度
 */
public class Dispatcher {
    private static ArrayList<URL> urls = new ArrayList<URL>();
    private static ArrayList<URL> visitedURLS = new ArrayList<URL>();
    private static ArrayList<URL> unvistedURLS = new ArrayList<URL>();

    public Dispatcher(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public synchronized URL getURL() {
        while (urls.isEmpty()) {
            try {
                wait(); //等待生产中写入数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.notify();
        URL url = urls.get(0);
        urls.remove(url);
        visitedURLS.add(url);

        return url;
    }

    public synchronized void insert(URL url) {
        if (!urls.contains(url) && !visitedURLS.contains(url))
            urls.add(url);
    }

    public synchronized void insert(ArrayList<URL> analyzedURL) {
        for (URL url : analyzedURL) {
            if (!urls.contains(url) && !visitedURLS.contains(url)) {
                urls.add(url);
            }
        }
    }
}
