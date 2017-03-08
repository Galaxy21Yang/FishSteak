package com.yangyize.preprocess;

import java.io.*;
import java.util.HashSet;

/**
 * 文件读取
 */
public class DictReader {

    //private static final String dictFile = "Dictionary\\wordlist.txt";

    public DictReader() {
    }

    public HashSet<String> scanDict(String dictFile) {
        HashSet<String> dictionary = new HashSet<String>();
        try {
            FileReader fileReader = new FileReader(dictFile);
            BufferedReader bfReader = new BufferedReader(fileReader);

            String word;
            while ((word = bfReader.readLine()) != null) {
                dictionary.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("the size of dictionary is: " + dictionary.size());

        return dictionary;
    }
}