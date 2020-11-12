package Examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Iter {

    private static class Proj implements Iterable<List<String>> {

        private final Iterable<List<String>> x;
        private final Integer[] c;

        public Proj(Iterable<List<String>> x, Integer... c) {
            this.c = c;
            this.x = x;
        }

        public Iterator<List<String>> iterator() {
            return new Iterator<List<String>>() {
                private Iterator<List<String>> i = x.iterator();

                public boolean hasNext() {
                    return i.hasNext();
                }

                public List<String> next() {
                    List<String> l = i.next();
                    List<String> r = new ArrayList<String>();
                    for(Integer j: c)
                        r.add(l.get(j));
                    return r;
                }

                public void remove() { }
            };
        }
    }

    public static void main(String[] args) throws Exception {
        List<List<String>> x = Arrays.asList(
                Arrays.asList("1", "one", "um"),
                Arrays.asList("2", "two", "dois")
        );

        Iterable<List<String>> r1 = new Proj(new Proj(x, 0, 2), 1);

        for(List<String> l: r1)
            System.out.println(l.toString());
    }
}
