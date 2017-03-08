package com.yangyize.test;

import com.yangyize.util.DBConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 测试HtmlParse
 */
public class testHtmlParse {
    public static void main(String[] args) {

        String dictFile = "/Raws/RAW1__0.html";
        try {
            FileReader fileReader = new FileReader(dictFile);
            BufferedReader bfReader = new BufferedReader(fileReader);
            DBConnection dbc = new DBConnection();

            String word;
            StringBuffer strBuffer = new StringBuffer();
            word = bfReader.readLine();
            strBuffer.append(word + "\n");
            System.out.println("【testMySql】  StringBuffer是: " + strBuffer.toString() + " the length is: " + strBuffer.length());

            bfReader.skip(13);
            while ((word = bfReader.readLine()) != null) {
                String url = readRawHead(bfReader);
                String content = readRawContent(bfReader);
                System.out.println("【testMySql】  URL: " + url);
                System.out.println("【testMySql】  content: " + content);
            }

            bfReader.close();
            dbc.close();
            System.out.println("【testMySql】  完成所有程序");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readRawContent(BufferedReader bfReader) {
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

    private static String readRawHead(BufferedReader bfReader) {
        String urlLine = null;
        try {
            urlLine = bfReader.readLine();
            if (urlLine != null) {
                urlLine = urlLine.substring(urlLine.indexOf(":") + 1, urlLine.length());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlLine;
    }
}
