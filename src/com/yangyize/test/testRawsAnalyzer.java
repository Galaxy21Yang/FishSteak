package com.yangyize.test;

import com.yangyize.preprocess.index.RawsAnalyzer;
import com.yangyize.preprocess.index.originalPageGetter;

/**
 * 测试RawsAnalyzer
 */
public class testRawsAnalyzer {
    public static void main(String[] args) {

		//RawsAnalyzer analyzer = new RawsAnalyzer("");
        //RawsAnalyzer analyzer = new RawsAnalyzer("/RAW1__4.html");
		//analyzer.createPageIndex();

        originalPageGetter getter = new originalPageGetter("http://www.sohu.com");
        getter.getPage();
    }
}
