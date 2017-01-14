package lei.doctor;

/**
 * 专业医生
 */
public class Specialist extends Doctor {
    private String speciality;  // 专业

    public Specialist(String name, String idnumber, String address, String speciality) {
        this.name = name;
        this.idnumber = idnumber;
        this.address = address;
        this.speciality = speciality;
    }

    @Override
    void info() {
        System.out.println("专业医生:");
        System.out.println("姓名: " + this.name);
        System.out.println("编号: " + this.idnumber);
        System.out.println("地址: " + this.address);
        System.out.println("专业: " + this.speciality);
    }
}
