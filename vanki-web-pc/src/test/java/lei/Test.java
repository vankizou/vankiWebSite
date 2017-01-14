package lei;

/**
 * P50, 二, 计算器
 */
public class Test {
    public static void main(String[] args) {
        Calculator calc = new Calculator(20, 10);
        System.out.println(calc.add());
        System.out.println(calc.subtract());
        System.out.println(calc.multiple());
        System.out.println(calc.divide());
    }
}

class Calculator {
    /**
     * 两个成员变量
     * 成员变量在方法外, 类内定义.
     * 成员变量分两种, 普通成员变量和静态成员变量
     */
    private double number1;
    private double number2;

    public Calculator(double number1, double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public double add() {
        return number1 + number2;
    }

    public double subtract() {
        return number1 - number2;
    }

    public double multiple() {
        return number1 * number2;
    }

    public double divide() {
        if (number2 == 0) return 0; // 除数不能为0
        return number1 / number2;
    }
}
