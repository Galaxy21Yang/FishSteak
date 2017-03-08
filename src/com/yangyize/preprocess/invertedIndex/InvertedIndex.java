package com.yangyize.preprocess.invertedIndex;

import com.yangyize.preprocess.forwardIndex.ForwardIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * InvertedIndex类建立网页倒排索引，对应关系为词组映射url，通过正向索引来建立
 * 建立过程如下，从正向索引取得索引值，遍历其中url，对于其中每个词组，推入map中作为key
 * 而url作为其value插入，最后得到的map就是倒排索引
 */
public class InvertedIndex {
    private HashMap<String, ArrayList<String>> forwardIndexMap;
    private HashMap<String, ArrayList<String>> invertedIndexMap;

    public InvertedIndex() {
        ForwardIndex forwardIndex = new ForwardIndex();
        forwardIndexMap = forwardIndex.createForwardIndex();

    }

    public HashMap<String, ArrayList<String>> createInvertedIndex() {
        invertedIndexMap = new HashMap<String, ArrayList<String>>();

        // 遍历原来的正向索引，进行倒排
        for (Iterator iter = forwardIndexMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String url = (String) entry.getKey();
            ArrayList<String> words = (ArrayList<String>) entry.getValue();
            String word;
            for (int i = 0; i < words.size(); i++) {
                word = words.get(i);

                //倒排索引中还没有这个key，可以加入这个词，再把url链接上
                if (!invertedIndexMap.containsKey(word)) {
                    ArrayList<String> urls = new ArrayList<String>();
                    urls.add(url);
                    invertedIndexMap.put(word, urls);
                } else {
                    ArrayList<String> urls = invertedIndexMap.get(word);
                    if (!urls.contains(url)) {
                        urls.add(url);
                    }
                }
            }
        }
        System.out.println("*********************************************");
        System.out.println("【InvertedIndex】 倒排序列创建完成，大小为："+ invertedIndexMap.size());
        return invertedIndexMap;
    }

    public HashMap<String, ArrayList<String>> getInvertedIndex() {
        return invertedIndexMap;
    }

    public static void main(String[] args) {
        InvertedIndex invertedIndex = new InvertedIndex();
        HashMap<String, ArrayList<String>> invertedIndexMap = invertedIndex.createInvertedIndex();

        String key = "的";
        ArrayList<String> urls = invertedIndexMap.get(key);

        if (urls != null) {
            System.out.println("【InvertedIndex】 得到结果如下： ");
            for (String url : urls) {
                System.out.println(url);
            }
        } else {
            System.out.println("【InvertedIndex】 没找到要搜索的关键词");
        }
    }
}
