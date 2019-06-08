package main.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    // ���ݿ�����
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1.����������
            Class.forName("com.mysql.jdbc.Driver");
            // 2.�����û��������롢URL
            String user = "root";
            String password = "123456";
            String url = "jdbc:mysql://localhost:3306/employeemanage?useUnicode=true&characterEncoding=utf8";
            // 3.���������������õ����ݿ�����
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null != conn) {
            System.out.println("��ȡMysql���ݿ�����:" + conn + "�ɹ�");
        } else {
            System.out.println("��ȡ���ݿ�����ʧ��!");
        }
        return conn;
    }

    // �������ݱ�
    public static void createTable(Connection conn) {
        final String createSQL = "CREATE TABLE IF NOT EXISTS employee( " + "eid VARCHAR(40) PRIMARY KEY, "
                + "ename VARCHAR(100) NOT NULL, " + "etype BOOLEAN NOT NULL, " + "status BOOLEAN NOT NULL)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(createSQL);
            // ִ��
            int rows = ps.executeUpdate();
            // System.out.println("����Ӱ������:" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ���Ա�����Ƿ�Ϊ��
    public static boolean isNullTable(int count) {
        if (count == 0) {
            return true;
        }
        return false;
    }

    // ������ݱ�
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

    // ��ѯ���ݿ�������Ϣ
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

    // ���ر�������
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

    // �ر�����
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
