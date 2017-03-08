package com.yangyize.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.yangyize.preprocess.DictSegment;
import com.yangyize.util.HtmlParser;

/**
 * <p>
 * 测试DictSegment
 * <p>
 * 测试通过
 */
public class testDictSegment {
    private static String dictFile = "src/com/yangyize/test/testText.txt";

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader(dictFile);
            BufferedReader bfReader = new BufferedReader(fileReader);
            StringBuffer result = new StringBuffer();

            String word;
            while ((word = bfReader.readLine()) != null) {
                result.append(word + "\n");
            }

            String htmlDoc = result.toString();
            System.out.println("htmlDoc Length" + htmlDoc.length());

            htmlDoc = (new HtmlParser()).html2Text(htmlDoc);

            DictSegment seg = new DictSegment();
            ArrayList<String> segResult = seg.SegmentFile(htmlDoc);

            int count = 0;
            for (String temp : segResult) {
                if (count++ == 100) {
                    System.out.println();
                    count = 0;
                }

                System.out.println(temp);
            }

        } catch (FileNotFoundException e) {
            System.out.println("【Test 分词】   文件未找到");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
