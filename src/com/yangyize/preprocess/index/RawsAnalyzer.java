package com.yangyize.preprocess.index;

import com.yangyize.util.Configuration;
import com.yangyize.util.DBConnection;
import com.yangyize.util.MD5;
import com.yangyize.util.Page;

import java.io.*;
import java.util.ArrayList;

/**
 * - RawsAnalyzer类实现了从原始网页集合Raws的分析操作，在完整MD5摘要算法之后，
 * - 建立网页URL、网页内容摘要、网页在Raws中偏移的映射、所属Raws的映射
 * - 算法传入的参数为raws所在的目录，需要遍历其中的众多文件
 */
public class RawsAnalyzer {

    private DBConnection dbc = new DBConnection();
    private MD5 md5 = new MD5();
    private int offset;
    private Page page;
    private String rootDirectory;

    /**
     * @param rootName
     */
    public RawsAnalyzer(String rootName) {
        this.rootDirectory = rootName;
        page = new Page();
    }

    /**
     *
     */
    public void createPageIndex() {
        ArrayList<String> fileNames = getSubFile(rootDirectory);
        for (String fileName : fileNames) {
            createPageIndex(fileName);
        }
    }

    /**
     * @param fileName
     */
    public void createPageIndex(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bfReader = new BufferedReader(fileReader);

            String word;
            offset = 0;
            int oldOffset = 0;

            while ((word = bfReader.readLine()) != null) {
                oldOffset = offset;
                offset += word.length() + 1;
                String url = readRawHead(bfReader);
                String content = readRawContent(bfReader);


                String contentMD5 = md5.getMD5ofStr(content);
                page.setPage(url, oldOffset, contentMD5, fileName);
                page.add2DB(dbc);
                System.out.println("【RawAnalyzer】  " + url + " 的offset是 " + offset);
            }

            bfReader.close();

            System.out.println("【RawAnalyzer】  文件加载完毕");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Raws的url
     *
     * @param bfReader
     * @return
     */
    private String readRawHead(BufferedReader bfReader) {
        String urlLine = null;
        try {

            urlLine = bfReader.readLine();
            offset = offset + urlLine.length() + 1;
            if (urlLine != null)
                urlLine = urlLine.substring(urlLine.indexOf(":") + 1, urlLine.length());

            String temp;
            while (!(temp = bfReader.readLine()).trim().isEmpty()) {
                offset = offset + temp.length() + 1;
            }
            offset += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlLine;
    }

    /**
     * 获取Raw的索引
     *
     * @param bfReader
     * @return
     */
    private String readRawContent(BufferedReader bfReader) {
        StringBuffer strBuffer = new StringBuffer();

        try {
            String word;
            while ((word = bfReader.readLine()) != null) {
                offset = offset + word.length() + 1;
                if (word.trim().isEmpty())
                    break;
                else
                    strBuffer.append(word + "\n");
            }
            offset += 2;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strBuffer.toString();
    }

    /**
     * 加载完整的路径
     *
     * @param fileName
     * @return
     */
    public static ArrayList<String> getSubFile(String fileName) {

        ArrayList<String> fileNames = new ArrayList<String>();
        Configuration conf = new Configuration();

        File parentF = new File(conf.RAWPATH + fileName);

        if (parentF.exists()) {
            if (parentF.isFile()) {
                System.out.println("【RawAnalyzer】  加载文件： " + parentF.getAbsolutePath());
                fileNames.add(parentF.getAbsolutePath());
                return fileNames;
            } else {
                System.out.println("【RawAnalyzer】  加载文件夹： " + conf.RAWPATH + fileName);
                String[] subFiles = parentF.list();
                for (int i = 0; i < subFiles.length; i++) {
                    System.out.println("【RawAnalyzer】  加载文件： " + conf.RAWPATH + fileName + subFiles[i]);
                    fileNames.add(conf.RAWPATH + fileName + subFiles[i]);
                    return fileNames;
                }
            }
        }
        System.out.println("【RawAnalyzer】  没有对应的文件或文件夹");
        return null;
    }

    /**
     * 运行main文件开始将Raw文件中的内容加入到数据库中
     *
     * @param args
     */
    public static void main(String[] args) {

        RawsAnalyzer analyzer = new RawsAnalyzer("");
        System.out.println("【RawAnalyzer】  数据开始收集到数据库....");
        analyzer.createPageIndex();

    }

}
