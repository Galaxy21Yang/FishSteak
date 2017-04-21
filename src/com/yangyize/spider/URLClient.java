package com.yangyize.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * 将URL对应的网页保存为字符串
 */
public class URLClient {
    public URLClient() {

    }

    public String getDocumentAt(URL url) {
        System.out.println("【Spider】Url: " + url.toString() + "正在保存为文档");

        URL hostURL = url;
        StringBuffer document = new StringBuffer();
        try {
            URLConnection conn = hostURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty())
                    document.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            System.out.println("【Spider】无法连接到URL: " + url.toString());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document.toString();
    }
}
