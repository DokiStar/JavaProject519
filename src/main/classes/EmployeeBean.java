package main.classes;

// ����Ա�����ݽṹ
public class EmployeeBean {
    // ʹ��private���η����������ݰ�ȫ��
    private String eid; // Ա�����
    private String ename; // Ա������
    private boolean etype; // Ա������
    private boolean status; // Ա��״̬ 

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
