import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class R {
    public static void main(String[] args) {
        List<String> n = new ArrayList<>();
        n.add( "Mário" );
        n.add("Simão");
        n.add("Sofia");
        n.add("Margarida");
        n.add( "Miriam" );
        List<String> r = new ArrayList<>();
        int size = n.size();

        Random rand = new Random();
        for(int i = 0; i < size; i++){
            int index = rand.nextInt(size);
            String name = n.get( index );
            r.add( name );
            n.remove( name );
        }
        System.out.println(r.toString());
    }
}
