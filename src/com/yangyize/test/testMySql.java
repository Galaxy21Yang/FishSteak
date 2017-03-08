package com.yangyize.test;

import java.sql.*;

/**
 * 测试MYSQL链接情况
 * <p>
 * 通过
 */
public class testMySql {

    public static void main(String[] args) {

        String driver = "com.mysql.jdbc.Driver"; // 驱动程序名
        String url = "jdbc:mysql://localhost/s_e_t?characterEncoding=utf8&useSSL=true"; // URL指向要访问的数据库名scutcs
        String user = "root"; // MySQL配置时的用户名
        String password = "123456"; // MySQL配置时的密码

        try {

            Class.forName(driver); // 加载驱动程序
            System.out.println("【MUSQL】 成功加载MySql驱动!");
            Connection conn = DriverManager.getConnection(url, user, password); // 连续数据库

            if (!conn.isClosed())
                System.out.println("【MYSQL】 成功连接到数据库!"); // 验证是否连接成功

            Statement statement = conn.createStatement(); // statement用来执行SQL语句

            String sql = "select * from url"; // 要执行的SQL语句

            ResultSet rs = statement.executeQuery(sql); // 结果集

            System.out.println("-----------------------------------------");
            System.out.println("执行结果如下所示:");
            System.out.println("-----------------------------------------");
            System.out.println(" ID" + "\t" + " URL" + "\t\t" + "RAWS");
            System.out.println("-----------------------------------------");

            String ID = null;

            while (rs.next()) {

                ID = rs.getString("ID"); // 选择sname这列数据

                System.out.println(ID + "\t" + rs.getString("url") + "\t"
                        + rs.getString("RawName")); // 输出结果
            }

            rs.close();
            conn.close();

        } catch (ClassNotFoundException e) {

            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}