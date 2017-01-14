package lei.doctor;

/**
 * 非专业医生
 */
public class NonSpecialist extends Doctor {
    public NonSpecialist(String name, String idnumber, String address) {
        this.name = name;
        this.idnumber = idnumber;
        this.address = address;
    }

    @Override
    void info() {
        System.out.println("非专业医生:");
        System.out.println("姓名: " + this.name);
        System.out.println("编号: " + this.idnumber);
        System.out.println("地址: " + this.address);
    }
}
