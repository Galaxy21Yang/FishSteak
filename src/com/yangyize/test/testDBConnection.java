package com.yangyize.test;

import com.yangyize.util.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 测试DBConnection类运行情况
 * <p>
 * 通过
 */
public class testDBConnection {

    public static void main(String[] args) {
        DBConnection dbc = new DBConnection();
///Users/zetze/Documents/Java/SearchEngine/Raws/RAW1__0.html
        String url = "testURL1";
        String content = "testContent2";
        String offset = "11";
        String raws = "RAW1__0.html";

//        String sql = "insert into pageindex(url, content, offset, raws)" +
//                " values ('" + url + "', '" + content + "', '" + offset + "', '" + raws + "')";
//        dbc.executeUpdate(sql);
//        System.out.println("executeUpdate() 运行成功！");

        String sqlUnusedUrlRead = "select * from url_index where used = 0 limit 1;";
        System.out.println(dbc.getFirstString(sqlUnusedUrlRead));

        String sqlCount = "select count(*) from url_index where used = 0;";
        System.out.println(dbc.getFirstString(sqlCount));

    }
}
