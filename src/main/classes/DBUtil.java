package main.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    // 数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1.加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 2.设置用户名，密码、URL
            String user = "root";
            String password = "123456";
            String url = "jdbc:mysql://localhost:3306/employeemanage?useUnicode=true&characterEncoding=utf8";
            // 3.利用驱动管理器得到数据库连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null != conn) {
            System.out.println("获取Mysql数据库连接:" + conn + "成功");
        } else {
            System.out.println("获取数据库连接失败!");
        }
        return conn;
    }

    // 创建数据表
    public static void createTable(Connection conn) {
        final String createSQL = "CREATE TABLE IF NOT EXISTS employee( " + "eid VARCHAR(40) PRIMARY KEY, "
                + "ename VARCHAR(100) NOT NULL, " + "etype BOOLEAN NOT NULL, " + "status BOOLEAN NOT NULL)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(createSQL);
            // 执行
            int rows = ps.executeUpdate();
            // System.out.println("操作影响行数:" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 检查员工表是否为空
    public static boolean isNullTable(int count) {
        if (count == 0) {
            return true;
        }
        return false;
    }

    // 清除数据表
    public static void deleteTable(Connection conn) {
        final String removeSQL = "DELETE FROM employee";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(removeSQL);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询数据库所有信息
    public static void selectDB(Connection conn) {
        System.out.println("eid\tename\tetype\tstatus");
        final String selectSQL = "SELECT * FROM employee";
        if (conn != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String eid = rs.getString("eid");
                    String ename = rs.getString("ename");
                    boolean etype = rs.getBoolean("etype");
                    boolean status = rs.getBoolean("status");
                    System.out.println(eid + "\t" + ename + "\t" + etype + "\t" + status);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 返回表中行数
    public static int countRow(Connection conn) {
        int count = 0;
        final String countSQL = " select count(*) from employee";
        if (conn != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(countSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("count(*)");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    // 关闭连接
    public static void closeAll(ResultSet rs, Statement s, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
