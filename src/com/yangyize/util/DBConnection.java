package com.yangyize.util;


import java.io.InputStream;
import java.sql.*;

/**
 * mysql连接类的封装
 * <p>
 * 【ResultSet】
 * - 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。
 * - ResultSet 对象具有指向其当前数据行的光标。
 * - 最初，光标被置于第一行之前。next 方法将光标移动到下一行；
 * - 因为该方法在 ResultSet 对象没有下一行时返回 false，所以可以在 while 循环中使用它来迭代结果集。
 */
public class DBConnection {

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepstmt = null;
    private String url = "jdbc:mysql://localhost/s_e_t?characterEncoding=utf8&useSSL=true";
    private String user = "root";
    private String password = "123456";

    /**
     * DBConnection()
     */
    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("【MYSQL】 成功加载MySQL驱动！");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

        } catch (ClassNotFoundException e) {
            System.out.println("【MYSQL】 找不到MySQL驱动!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("【MYSQL】 无法加载MySQL!");
            e.printStackTrace();
        }
    }

    /**
     * DBConnection(String sql)
     * @param sql
     */
    public DBConnection(String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("【MYSQL】 成功加载MySQL驱动！");
            conn = DriverManager.getConnection(url, user, password);
            this.prepareStatement(sql);
        } catch (ClassNotFoundException e) {
            System.out.println("【MYSQL】 找不到MySQL驱动!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("【MYSQL】 无法加载MySQL!");
            e.printStackTrace();
        }
    }

    /**
     * 预编译
     * PrepareStatement是预编译的sql语句对象，sql语句被预编译并保存在对象中。
     * 被封装的sql语句代表某一类操作，语句中可以包含动态参数“?”，在执行时可以为“?”动态设置参数值。
     * 使用PrepareStatement对象执行sql时，sql被数据库进行解析和编译，然后被放到命令缓冲区，每当执行同一个PrepareStatement对象时，
     * 它就会被解析一次，但不会被再次编译。在缓冲区可以发现预编译的命令，并且可以重用。
     * PrepareStatement可以减少编译次数提高数据库性能。
     *
     * @param sql
     */
    private void prepareStatement(String sql) {
        try {
            prepstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getConnection()
     *
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * 将指定参数设置为给定 Java String 值。
     *
     * @param index
     * @param value
     */
    public void setString(int index, String value) {
        try {
            prepstmt.setString(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定参数设置为给定 Java int 值。
     *
     * @param index
     * @param value
     */
    public void setInt(int index, int value) {
        try {
            prepstmt.setInt(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定参数设置为给定 Java boolean 值。
     *
     * @param index
     * @param value
     */
    public void setBoolean(int index, boolean value) {
        try {
            prepstmt.setBoolean(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用给定的 Calendar 对象将指定参数设置为给定 java.sql.Date 值。
     *
     * @param index
     * @param value
     */
    public void setDate(int index, Date value) throws SQLException {
        try {
            prepstmt.setDate(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定参数设置为给定 Java long 值。
     *
     * @param index
     * @param value
     */
    public void setLong(int index, long value) throws SQLException {
        try {
            prepstmt.setLong(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定参数设置为给定 Java REAL 值。
     *
     * @param index
     * @param value
     */
    public void setFloat(int index, float value) throws SQLException {
        try {
            prepstmt.setFloat(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定参数设置为给定输入流，该输入流将具有给定字节数。
     *
     * @param index
     * @param in
     * @param length
     * @throws SQLException
     */
    public void setBinaryStream(int index, InputStream in, int length) throws SQLException {
        try {
            prepstmt.setBinaryStream(index, in, length);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即清除当前参数值。
     *
     * @throws SQLException
     */
    public void clearParameters() throws SQLException {
        try {
            prepstmt.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得预编译
     *
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return prepstmt;
    }

    /**
     * Statement用于执行静态sql语句，在执行时，必须指定一个事先准备好的sql语句。
     *
     * @return
     */
    public Statement getStatement() {
        return stmt;
    }

    /**
     * 在此 PreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
     * 返回：包含该查询生成的数据的 ResultSet 对象；
     * 抛出：SQLException - 如果发生数据库访问错误，在关闭的 PreparedStatement 上调用此方法，
     * 或者 SQL 语句没有返回 ResultSet 对象
     * <p>
     * 更新   【增加返回null】
     *
     * @param sql
     * @return
     */
    public ResultSet executeQuery(String sql) {
        try {
            if (stmt != null) {
                return stmt.executeQuery(sql);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在此 PreparedStatement 对象中执行 SQL 查询，
     * 并返回该查询生成的 ResultSet 对象。
     *
     * @return
     */
    public ResultSet executeQuery() {
        try {
            if (prepstmt != null) {
                return prepstmt.executeQuery();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加数据到数据库
     *
     * @param sql
     */
    public void executeUpdate(String sql) {
        try {
            if (stmt != null) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同executeQuery()
     */
    public void executeUpdate() {
        try {
            if (prepstmt != null) {
                prepstmt.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 继承自 Statement
     * 立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
     * 一般来说，使用完后立即释放资源是一个好习惯，这样可以避免对数据库资源的占用。
     */
    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (prepstmt != null) {
                prepstmt.close();
                prepstmt = null;
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFirstString(String sql){
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getFirstInteger(String sql){
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



}
