package lei.doctor;

/**
 * 医生
 */
public abstract class Doctor {
    protected String name;        // 姓名
    protected String idnumber;      // 编号
    protected String address;     // 地址

    abstract void info();
}
