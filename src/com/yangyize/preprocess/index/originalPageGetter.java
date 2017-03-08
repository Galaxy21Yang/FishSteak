package com.yangyize.preprocess.index;

import com.yangyize.util.Configuration;
import com.yangyize.util.DBConnection;
import com.yangyize.util.MD5;
import com.yangyize.util.Page;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 根据输入参数从原始raws文件中读取网页的功能
 * 输入的参数是：rawsname，offset，验证的url信息和验证connent摘要信息
 */
public class originalPageGetter {

    private String url = "";
    private DBConnection dbc = new DBConnection();
    private MD5 md5 = new MD5();
    private String date = "";
    private String urlFromHead = "";
    private Configuration conf = new Configuration();

    public originalPageGetter() {
    }

    public originalPageGetter(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    /**
     * 从数据库中读取文件
     *
     * @return
     */
    public String getPage() {
        Page page = getRawsInfo(url);
        String content = "";
        try {
            StringBuffer tfileName = new StringBuffer();
            tfileName.append(page.getRawName());
            String fileName = tfileName.toString();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bfReader = new BufferedReader(fileReader);

            // **
            bfReader.skip(page.getOffset());

            readRawHead(bfReader);
            content = readRawHead(bfReader);
            String contentMD5 = md5.getMD5ofStr(content);
            if (contentMD5.equals(page.getContent())) {
                System.out.println("【originalPageGetter】  MD5相符");
            } else {
                System.out.println("【originalPageGetter】  MD5不符");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public Page getRawsInfo(String url) {
        String sql = "select * from pageindex where url ='" + url + "' ";
        ResultSet rs = dbc.executeQuery(sql);

        String content = "";
        String raws = "";
        int offset = 0;

        try {
            while (rs.next()) {
                // 选择content这列数据
                content = rs.getString("content");

                // 选择offset这列数据
                offset = Integer.parseInt(rs.getString("offset"));

                // 选择raws这列数据
                raws = rs.getString("raws");

                System.out.println(url + "\t" + content + "\t" + offset + "\t" + raws);


            }
            return new Page(url, offset, content, raws);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getContent(String file, int offset) {
        String content = "";
        BufferedReader bfReader = null;
        try {
            StringBuffer tfileName = new StringBuffer();
            tfileName.append(file);

            String fileName = tfileName.toString();
            FileReader fileReader = new FileReader(fileName.toString());
            bfReader = new BufferedReader(fileReader);

            String word;
            bfReader.skip(offset);
            readRawHead(bfReader);
            content = readRawContent(bfReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfReader != null) {
                try {
                    bfReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }

    private String readRawHead(BufferedReader bfReader) {
        String headStr = "";
        try {
            // 版本行
            bfReader.readLine();

            urlFromHead = bfReader.readLine();
            headStr += urlFromHead;
            if (urlFromHead != null) {
                urlFromHead = urlFromHead.substring(urlFromHead.indexOf(":") + 1, urlFromHead.length());
            }

            date = bfReader.readLine();
            headStr += date;
            if (date != null) {
                date = date.substring(date.indexOf(":") + 1, date.length());
            }

            String temp;
            while (!(temp = bfReader.readLine()).trim().isEmpty()) {
                headStr += temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return headStr;
    }

    private String readRawContent(BufferedReader bfReader) {
        StringBuffer strBuffer = new StringBuffer();
        try {
            String word;
            while ((word = bfReader.readLine()) != null) {
                if (word.trim().isEmpty()) {
                    break;
                } else {
                    strBuffer.append(word + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strBuffer.toString();
    }

}
