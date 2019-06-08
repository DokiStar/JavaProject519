package main.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourse {

    // 添加员工
    public static void addEmployee(Connection conn, EmployeeBean eb) {
        final String addSQL = "INSERT INTO employee" + " (eid, ename, etype, status)" + " VALUES(? ,? ,? ,?)";
        if (conn != null) {
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(addSQL);
                // 设置占位符的值
                ps.setString(1, eb.getEid());
                ps.setString(2, eb.getEname());
                ps.setInt(3, eb.isEtype() ? 1 : 0);
                ps.setInt(4, eb.isStatus() ? 1 : 0);
                ps.executeUpdate();
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

    // 返回员工数据类型的数组
    public static EmployeeBean[] returnEmployee(Connection conn) {
        EmployeeBean[] ebArray = new EmployeeBean[DBUtil.countRow(conn)];
        for (int i = 0; i < ebArray.length; i++) {
            ebArray[i] = new EmployeeBean();
        }

        final String selectSQL = "SELECT * FROM employee";
        if (conn != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(selectSQL);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    String eid = rs.getString("eid");
                    String ename = rs.getString("ename");
                    boolean etype = rs.getBoolean("etype");
                    boolean status = rs.getBoolean("status");

                    ebArray[i].setEid(eid);
                    ebArray[i].setEname(ename);
                    ebArray[i].setEtype(etype);
                    ebArray[i].setStatus(status);
                    i++;
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
        return ebArray;
    }

    // 在数据库中更新员工工号
    public static void updateEid(Connection conn, int newEid, String oldEid) {
        if (!EmpControl.update_emp(oldEid, newEid)) {
            return;
        }
        final String updateSQL = "UPDATE employee SET eid = ? WHERE eid = ?";
        if (conn != null) {
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(updateSQL);
                ps.setString(1, JavaUtil.extractAlphabet(oldEid) + newEid);
                ps.setString(2, oldEid);
                ps.executeUpdate();
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

    // 在数据库中更新员工状态
    public static void updateStatus(Connection conn, boolean status, String eid) {
        if (!EmpControl.update_status(eid, status)) {
            return;
        }
        final String updateSQL = "UPDATE employee SET status = ? WHERE eid = ?";
        if (conn != null) {
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(updateSQL);
                ps.setBoolean(1, status);
                ps.setString(2, eid);
                ps.executeUpdate();
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
}
