package main.classes;

import java.sql.Connection;
import java.util.Scanner;

public class EmpControl {

    public static Connection conn = DBUtil.getConnection();

    // 对数据表进行初始化
    public static void init(boolean init) {
        
        String str = new String();
        Scanner sc = new Scanner(System.in);
        System.out.printf("是否初始化系统：（true或false）");
        str = sc.nextLine();
        ;
        // 表已被初始化
        if (init) {
            System.out.println("系统已经被初始化");
            str = "false";
        }
        if (JavaUtil.TOF(str)) {
            DBUtil.deleteTable(conn);
            System.out.println("初始化完成");
        }
    }

    // 检查一个工号的数字部分是否在数据表中已经存在
    public static int isEidExists(EmployeeBean[] ebArray, String eid) {
        // 检查这个eid是否对应合作员工
        if (eid.substring(0, 2).equalsIgnoreCase("WX")) {
            // System.out.println("该工号对应合作员工，不能修改");
            return 4;
        }

        // 检查这个eid的数字部分是否符合规范（1000 - 9999）
        if (!JavaUtil.isValidNumber(Integer.parseInt((JavaUtil.extractNumber(eid))))) {
            // System.out.println("这个数字不符合规范（1000 - 9999）");
            return 3;
        }
        for (int i = 0; i < ebArray.length; i++) {
            // 检查表中是否存在这个eid的数字部分与表中的某个工号的数字部分相同的情况
            if (JavaUtil.extractNumber(eid).equalsIgnoreCase(JavaUtil.extractNumber(ebArray[i].getEid()))) {
                // 检查表中是否有存在工号与eid完全相同的情况
                if (ebArray[i].getEid().equalsIgnoreCase(eid)) {
                    // 这个eid所对应的员工处于离职状态
                    if (!ebArray[i].isStatus()) {
                        // System.out.println("该工号所对应的员工处于离职状态");
                        return 2;
                    }
                    return 5;
                }
                // 仅存在数字部分相同的情况
                // System.out.println("该工号的数字部分已被使用");
                return 1;
            }
        }
        // 这个eid在数据库中不存在
        // System.out.println("该工号不存在");
        return 0;
    }

    // 根据员工姓名和类型，生成固定格式的工号，以添加员工
    public static String add_emp(String ename, boolean etype) {
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        String eid = (etype ? "" : "WX") + JavaUtil.firstLetter(ename);
        System.out.printf("请输入要给%s分配的数字编号：", ename);
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        in.close();
        // 判断工号数字部分是否输入正确，若输入错误则直接退出
        if (!JavaUtil.isNumeric(str) && isEidExists(ebArray, str) != 0) {
            System.err.println("输入错误");
            System.exit(0);
        }
        eid += str;
        return eid;
    }

    // 添加新员工
    public static EmployeeBean newEmployee() {
        // 键盘输入
        Scanner sc = new Scanner(System.in);
        System.out.printf("请输入这名员工的姓名：");
        String temp_ename = sc.nextLine();
        System.out.printf("请输入%s的类型（true或false）：", temp_ename);
        boolean temp_etype = sc.nextBoolean();
        System.out.printf("请输入%s的状态（true或false）：", temp_ename);
        boolean temp_status = sc.nextBoolean();

        // 输入数据结构
        EmployeeBean bean = new EmployeeBean();
        bean.setEname(temp_ename);
        bean.setEtype(temp_etype);
        bean.setStatus(temp_status);
        bean.setEid(add_emp(bean.getEname(), bean.isEtype()));
        
        return bean;
    }

    // 检查是否允许更新工号
    public static boolean update_emp(String eid, int id_number) {
        Connection conn = DBUtil.getConnection();
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        int states = isEidExists(ebArray, eid);
        switch (states) {
        case 0:
            System.out.println("该工号不存在，不能修改");
            return false;
        case 4:
            System.out.println("该工号对应合作员工，不能修改");
            return false;
        case 2:
            System.out.println("该工号所对应的员工处于离职状态，不能修改");
            return false;
        default:
            break;
        }
        if (isEidExists(ebArray, Integer.toString(id_number)) == 1) {
            System.out.println("新工号所对应的数字部分已存在，不能修改");
            return false;
        }
        return true;
    }

    // 检查是否允许更新员工状态
    public static boolean update_status(String eid, boolean status) {
        Connection conn = DBUtil.getConnection();
        EmployeeBean[] ebArray = DataSourse.returnEmployee(conn);
        int states = isEidExists(ebArray, eid);
        switch (states) {
        case 0:
            System.out.println("该工号不存在，不能修改");
            return false;
        case 4:
            System.out.println("该工号对应合作员工，不能修改");
            return false;
        case 2:
            System.out.println("该工号所对应的员工处于离职状态，不能修改");
            return false;
        default:
            break;
        }
        return true;
    }
}
