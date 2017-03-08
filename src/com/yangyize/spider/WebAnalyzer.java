package com.yangyize.spider;

import com.yangyize.util.HtmlParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * 网页分析
 */
public class WebAnalyzer {
    private static final String ENDPAGE = "**************************************************";

    public WebAnalyzer() {
    }

    /**
     * 抽取web中的url
     */
    public ArrayList<URL> doAnalyzer(BufferedWriter bfWriter, URL url, String htmlDoc) {
        System.out.println("in doing analyzer ths size of doc is: " + htmlDoc.length());
        ArrayList<URL> urlInHtmlDoc = (new HtmlParser()).urlDetector(htmlDoc);
        saveDoc(bfWriter, url, htmlDoc);
        return urlInHtmlDoc;
    }

    private void saveDoc(BufferedWriter bfWriter, URL url, String htmlDoc) {
        try {
            // 版本
            String versionStr = "version:1.0\n";

            // URL
            String URLStr = "url:" + url.toString() + "\n";

            // 时间
            Date date = new Date();
            String dateStr = "date:" + date.toString() + "\n";

            // IP
            InetAddress address = InetAddress.getByName(url.getHost());
            String IPStr = address.toString();
            IPStr = "IP:" + IPStr.substring(IPStr.indexOf("/") + 1, IPStr.length()) + "\n";

            //长度
            String htmlLen = "length:" + htmlDoc.length() + "\n";


            bfWriter.append(versionStr);
            bfWriter.append(URLStr);
            bfWriter.append(dateStr);
            bfWriter.append(IPStr);
            bfWriter.append(htmlLen);
            bfWriter.newLine();
            bfWriter.append(ENDPAGE);
            bfWriter.newLine();
            bfWriter.append(htmlDoc);
            bfWriter.newLine();

            bfWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
