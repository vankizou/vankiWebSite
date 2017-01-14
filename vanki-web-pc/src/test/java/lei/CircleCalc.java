package lei;

/**
 * 求圆的面积和周长
 */
public class CircleCalc {
    public static void main(String[] args) {
        double r = 20;  // 圆的半径

        double circleArea = calcArea(r);    // 圆的面积
        double circleGirth = calcGirth(r);  // 圆的周长

        System.out.println(circleArea);     // 打印圆的面积
        System.out.println(circleGirth);    // 打印圆的周长
    }

    /**
     * 求圆的面积
     * ps: 圆的面积公式: π 乘以 r 的平方
     *
     * @param r 圆的半径
     *
     * @return 圆的面积
     */
    private static double calcArea(double r) {
        return Math.pow(r, 2) * 3.14;   // 等价于: r * r * 3.14
    }

    /**
     * 求圆的周长
     * ps: 圆的周长公式: 2πr
     * @param r
     * @return
     */
    private static double calcGirth(double r) {
        return 2 * 3.14 * r;
    }
}
