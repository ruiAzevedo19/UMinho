import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mat {

    public static List<List<String>> proj(List<List<String>> x, Integer... c) {
        List<List<String>> r = new ArrayList<List<String>>();
        
        for(List<String> l: x) {
            List<String> o = new ArrayList<String>();
            for(Integer i: c)
                o.add(l.get(i));
            r.add(o);
        }

        return r;
    }

    public static void main(String[] args) throws Exception {
        List<List<String>> x = Arrays.asList(
            Arrays.asList("1", "one", "um"),
            Arrays.asList("2", "two", "dois")
        );

        List<List<String>> r1 = proj(proj(x, 0, 2), 1);

        System.out.println(r1.toString());
    }
}
