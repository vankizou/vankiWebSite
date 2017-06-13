/**
 * Created by vanki on 16/10/26.
 */
public class T {
    public static void main(String[] args) {
        System.out.println(isReverseNum(11211));
    }

    public static boolean isReverseNum(int num) {
        return new StringBuffer(String.valueOf(num)).reverse().toString().equals(String.valueOf(num));
    }
}
