import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vanki on 16/10/26.
 */
public class T {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add(null);

        List<String> list = new ArrayList<>();
        list.add(null);

        System.out.println(set.size());
        System.out.println(list.size());
    }
}
