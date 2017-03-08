package com.yangyize.preprocess.forwardIndex;

import com.yangyize.preprocess.DictSegment;
import com.yangyize.preprocess.index.originalPageGetter;
import com.yangyize.util.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ForwardIndex类建立网页正向索引，对应关系为url映射到网页所含词组,为倒排索引做准备
 * 建立过程如下，从数据库中取出url，判断该网页是否已经分析过，如果没有分析过，根据文件名和偏移
 * 得到网页的内容，对网页内容进行分词，得到传回来的词组，然后使得url和这个词组关联起来
 */
public class ForwardIndex {

    private DBConnection dbc = new DBConnection();
    private HashMap<String, ArrayList<String>> indexMap = new HashMap<String, ArrayList<String>>();
    private originalPageGetter pageGetter = new originalPageGetter();
    private DictSegment dictSeg = new DictSegment();

    public ForwardIndex() {
    }

    public HashMap<String, ArrayList<String>> createForwardIndex() {
        try {
            ArrayList<String> segResult = new ArrayList<String>();
            String sql = "select * from pageindex"; //sql语句
            ResultSet rs = dbc.executeQuery(sql);
            String url, fileName;
            int offset = 0;

            // 从数据库中读出字段，然后得到网页之后进行分词处理
            System.out.println("【ForwardIndex】 在进程中创建前序排列: ");
            while (rs.next()) {
                // 选择sname这列数据
                url = rs.getString("url");
                System.out.println(url);

                fileName = rs.getString("raws");
                offset = Integer.parseInt(rs.getString("offset"));
                String htmlDoc = pageGetter.getContent(fileName, offset);

                segResult = dictSeg.SegmentFile(htmlDoc);
                indexMap.put(url, segResult);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------");
        System.out.println("【ForwardIndex】 前序排列创建成功，大小为：" + indexMap.size());

        return indexMap;
    }

    public static void main(String[] args) {
        ForwardIndex forwardIndex = new ForwardIndex();
        HashMap<String, ArrayList<String>> indexMap = forwardIndex.createForwardIndex();

        for (Iterator iter = indexMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next(); // map.entry 同时取出键值对
            String url = (String) entry.getKey();
            ArrayList<String> words = (ArrayList<String>) entry.getValue();
            System.out.println("【ForwardIndex】 " + url + " 对应的分词结果是： " + words.size());

        }
    }

}
