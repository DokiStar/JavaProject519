package main.classes;

import java.sql.Connection;
import java.util.Scanner;

public class EmpControl {

    public static Connection conn = DBUtil.getConnection();

    // �����ݱ���г�ʼ��
    public static void init(boolean init) {
        
        String str = new String();
        Scanner sc = new Scanner(System.in);
        System.out.printf("�Ƿ��ʼ��ϵͳ����true��false��");
        str = sc.nextLine();
        ;
        // ���ѱ���ʼ��
        if (init) {
            System.out.println("ϵͳ�Ѿ�����ʼ��");
            str = "false";
        }
        if (JavaUtil.TOF(str)) {
            DBUtil.deleteTable(conn);
            System.out.println("��ʼ�����");
        }
    }

    // ���һ�����ŵ����ֲ����Ƿ������ݱ����Ѿ�����
    public static int isEidExists(EmployeeBean[] ebArray, String eid) {
        // ������eid�Ƿ��Ӧ����Ա��
        if (eid.substring(0, 2).equalsIgnoreCase("WX")) {
            // System.out.println("�ù��Ŷ�Ӧ����Ա���������޸�");
            return 4;
        }

        // ������eid�����ֲ����Ƿ���Ϲ淶��1000 - 9999��
        if (!JavaUtil.isValidNumber(Integer.parseInt((JavaUtil.extractNumber(eid))))) {
            // System.out.println("������ֲ����Ϲ淶��1000 - 9999��");
            return 3;
        }
        for (int i = 0; i < ebArray.length; i++) {
            // �������Ƿ�������eid�����ֲ�������е�ĳ�����ŵ����ֲ�����ͬ�����
            if (JavaUtil.extractNumber(eid).equalsIgnoreCase(JavaUtil.extractNumber(ebArray[i].getEid()))) {
                // �������Ƿ��д��ڹ�����eid��ȫ��ͬ�����
                if (ebArray[i].getEid().equalsIgnoreCase(eid)) {
                    // ���eid����Ӧ��Ա��������ְ״̬
                    if (!ebArray[i].isStatus()) {
                        // System.out.println("�ù�������Ӧ��Ա��������ְ״̬");
                        return 2;
                    }
                    return 5;
                }
                // ���������ֲ�����ͬ�����
                // System.out.println("�ù��ŵ����ֲ����ѱ�ʹ��");
                return 1;
            }
        }
        // ���eid�����ݿ��в�����
        // System.out.println("�ù��Ų�����");
        return 0;
    }

    // ����Ա�����������ͣ����ɹ̶���ʽ�Ĺ��ţ������Ա��
    public static String add_emp(String ename, boolean etype) {
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        String eid = (etype ? "" : "WX") + JavaUtil.firstLetter(ename);
        System.out.printf("������Ҫ��%s��������ֱ�ţ�", ename);
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        in.close();
        // �жϹ������ֲ����Ƿ�������ȷ�������������ֱ���˳�
        if (!JavaUtil.isNumeric(str) && isEidExists(ebArray, str) != 0) {
            System.err.println("�������");
            System.exit(0);
        }
        eid += str;
        return eid;
    }

    // �����Ա��
    public static EmployeeBean newEmployee() {
        // ��������
        Scanner sc = new Scanner(System.in);
        System.out.printf("����������Ա����������");
        String temp_ename = sc.nextLine();
        System.out.printf("������%s�����ͣ�true��false����", temp_ename);
        boolean temp_etype = sc.nextBoolean();
        System.out.printf("������%s��״̬��true��false����", temp_ename);
        boolean temp_status = sc.nextBoolean();

        // �������ݽṹ
        EmployeeBean bean = new EmployeeBean();
        bean.setEname(temp_ename);
        bean.setEtype(temp_etype);
        bean.setStatus(temp_status);
        bean.setEid(add_emp(bean.getEname(), bean.isEtype()));
        
        return bean;
    }

    // ����Ƿ�������¹���
    public static boolean update_emp(String eid, int id_number) {
        Connection conn = DBUtil.getConnection();
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        int states = isEidExists(ebArray, eid);
        switch (states) {
        case 0:
            System.out.println("�ù��Ų����ڣ������޸�");
            return false;
        case 4:
            System.out.println("�ù��Ŷ�Ӧ����Ա���������޸�");
            return false;
        case 2:
            System.out.println("�ù�������Ӧ��Ա��������ְ״̬�������޸�");
            return false;
        default:
            break;
        }
        if (isEidExists(ebArray, Integer.toString(id_number)) == 1) {
            System.out.println("�¹�������Ӧ�����ֲ����Ѵ��ڣ������޸�");
            return false;
        }
        return true;
    }

    // ����Ƿ��������Ա��״̬
    public static boolean update_status(String eid, boolean status) {
        Connection conn = DBUtil.getConnection();
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        int states = isEidExists(ebArray, eid);
        switch (states) {
        case 0:
            System.out.println("�ù��Ų����ڣ������޸�");
            return false;
        case 4:
            System.out.println("�ù��Ŷ�Ӧ����Ա���������޸�");
            return false;
        case 2:
            System.out.println("�ù�������Ӧ��Ա��������ְ״̬�������޸�");
            return false;
        default:
            break;
        }
        return true;
    }
}
