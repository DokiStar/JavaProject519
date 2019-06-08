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

        System.out.println("��ѡ������Ҫ�Ĺ��ܣ�");
        System.out.println("1-��ʼ��ϵͳ");
        System.out.println("2-����Ա��");
        System.out.println("3-����Ա������");
        System.out.println("4-����Ա��״̬");
        System.out.println("5-��ѯԱ����Ϣ");

        Scanner sc = new Scanner(System.in);
        int temp = sc.nextInt();
        switch (temp) {
        case 1:
            int countRow = DBUtil.countRow(connection);
            boolean init = DBUtil.isNullTable(countRow);

            // ��ʼ��ϵͳ
            EmpControl.init(init);
            break;
        case 2:
            EmployeeBean bean = EmpControl.newEmployee();
            DataSourse.addEmployee(connection, bean);
            break;
        case 3:
            // ���Ĺ���
            DataSourse.updateEid(connection, 8445, "H9991");
            DBUtil.selectDB(connection);
        case 4:
            // ����״̬
            DataSourse.updateStatus(connection, false, "H4668");
            break;
        case 5:
            // ��ѯ��Ϣ
            DBUtil.selectDB(connection);
            break;
        default:
            break;
        }
        sc.close();
    }
}
