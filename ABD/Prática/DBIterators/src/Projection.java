import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Selection<T> implements Iterable< List<T> >{
    private Iterable< List<T> > record;

    @Override
    public Iterator< List<T> > iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public List<T> next() {
                return null;
            }
        };
    }

    public static void main(String[] args) {
        List< List<String> > table = Arrays.asList();

    }
}
