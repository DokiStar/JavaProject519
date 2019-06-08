package main.classes;

// 定义员工数据结构
public class EmployeeBean {
    // 使用private修饰符，保障数据安全性
    private String eid; // 员工编号
    private String ename; // 员工姓名
    private boolean etype; // 员工类型
    private boolean status; // 员工状态 

    public EmployeeBean() {
        super();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public boolean isEtype() {
        return etype;
    }

    public void setEtype(boolean etype) {
        this.etype = etype;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
