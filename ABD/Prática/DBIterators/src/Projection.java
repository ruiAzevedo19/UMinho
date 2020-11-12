import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Projection<T> implements Iterable< List<T> >{
    private Iterable< List<T> > record;
    private Integer[] c;

    public Projection(Iterable<List<T>> x, Integer... c){
        this.record = x;
        this.c = c;
    }

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
        List< List<String> > table = Arrays.asList(
                Arrays.asList( "1" , "one" , "um" ),
                Arrays.asList( "2" , "two" , "dois" )
        );
        Iterable< List<String> > r = new Projection<>(table,0,1);

    }
}
