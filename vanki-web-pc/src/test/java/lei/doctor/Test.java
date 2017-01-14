package lei.doctor;

/**
 * 打印信息
 */
public class Test {
    public static void main(String[] args) {
        Specialist s = new Specialist("张三", "110", "xx路101号", "xx专业");
        NonSpecialist nonS = new NonSpecialist("李四", "111", "xx路111号");

        s.info();
        nonS.info();
    }
}
