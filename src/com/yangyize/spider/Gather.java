package com.yangyize.spider;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * web采集
 */
public class Gather implements Runnable {
    private Dispatcher disp;
    private String ID;
    private URLClient client = new URLClient();
    private WebAnalyzer analyzer = new WebAnalyzer();
    private File file;
    private BufferedWriter bfWriter;

    public Gather(String ID, Dispatcher disp) {
        this.ID = ID;
        this.disp = disp;

        file = new File("Raws", "RAW1__" + ID + ".html");

        try {
            file.createNewFile();
            bfWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int counter = 0;
        while (counter++ <= 2) { //每个线程提取两个网页
            URL url = disp.getURL();
            System.out.println("in running: " + ID + " get url: " + url.toString());
            String htmlDoc = client.getDocumentAt(url);

            //没有获得页面的代码信息，这样就需要删除
            if (htmlDoc.length() != 0) {
                ArrayList<URL> newURL = analyzer.doAnalyzer(bfWriter, url, htmlDoc);
                if (newURL.size() != 0)
                    disp.insert(newURL);
            }

        }
    }

}
