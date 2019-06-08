package main;

import java.sql.Connection;
import java.util.Scanner;

import main.classes.DBUtil;
import main.classes.DataSourse;
import main.classes.EmpControl;
import main.classes.EmployeeBean;

public class EmpMain {
    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();
        DBUtil.createTable(connection);

        System.out.println("请选择你想要的功能：");
        System.out.println("1-初始化系统");
        System.out.println("2-新增员工");
        System.out.println("3-更新员工工号");
        System.out.println("4-更新员工状态");
        System.out.println("5-查询员工信息");

        Scanner sc = new Scanner(System.in);
        int temp = sc.nextInt();
        switch (temp) {
        case 1:
            int countRow = DBUtil.countRow(connection);
            boolean init = DBUtil.isNullTable(countRow);

            // 初始化系统
            EmpControl.init(init);
            break;
        case 2:
            EmployeeBean bean = EmpControl.newEmployee();
            DataSourse.addEmployee(connection, bean);
            break;
        case 3:
            // 更改工号
            DataSourse.updateEid(connection, 8445, "H9991");
            DBUtil.selectDB(connection);
        case 4:
            // 更改状态
            DataSourse.updateStatus(connection, false, "H4668");
            break;
        case 5:
            // 查询信息
            DBUtil.selectDB(connection);
            break;
        default:
            break;
        }
        sc.close();
    }
}
