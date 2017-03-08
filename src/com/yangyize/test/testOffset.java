package com.yangyize.test;

import com.yangyize.util.DBConnection;
import com.yangyize.util.MD5;
import com.yangyize.util.Page;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 测试Offset
 * <p>
 * 测试MD5() Page()完成
 */
public class testOffset {

    /**
     * 之后的offset计算的时候需要+2，因为readline函数读取
     * 的字符串忽略最后的换行符等，而换行符在其中占2个字符
     */
    private static int offset = 0;

    public static void main(String[] args) {

        String dictFile = "src/com/yangyize/test/testText.txt";
        try {
            FileReader fileReader = new FileReader(dictFile);
            BufferedReader bfReader = new BufferedReader(fileReader);
            DBConnection dbc = new DBConnection();
            MD5 md5 = new MD5();
            String word;
            Page page = new Page();
            offset = 0;
            int oldOffset = 0;

            while ((word = bfReader.readLine()) != null) {
                oldOffset = offset;
                offset += word.length() + 2;
                String url = readRawHead(bfReader);
                //System.out.println("the offset is: " + offset);
                String content = readRawContent(bfReader);
                System.out.println("【testOffset】  offset是：" + offset);
                String contentMD5 = md5.getMD5ofStr(content);
                System.out.println("【testOffset】  MD5是：" + contentMD5);
                page.setPage(url, oldOffset, contentMD5, dictFile);
            }

            bfReader.close();
            dbc.close();
            System.out.println("【testOffset】  完成所有程序");

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
                offset = offset + word.length() + 2;
                if (word.trim().isEmpty()) {
                    break;
                } else {
                    strBuffer.append(word + "\n");
                }
            }

            System.out.println("【testOffset】  readRawContent完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuffer.toString();
    }

    private static String readRawHead(BufferedReader bfReader) {
        String urlLine = null;
        try {
            urlLine = bfReader.readLine();
            offset = offset + urlLine.length() + 2;
            if (urlLine != null) {
                urlLine = urlLine.substring(urlLine.indexOf(":") + 1, urlLine.length());
            }

            String temp;
            while (!(temp = bfReader.readLine()).trim().isEmpty()) {
                offset = offset + temp.length() + 2;
            }

            offset += 2;
            System.out.println("【testOffset】  Head完成! URL为: " + urlLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlLine;
    }

}
