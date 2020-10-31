import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] values = {3,6,8,7,9,5};
        /*
        int result = Arrays.stream(values).map(v -> v*v).sum();
        System.out.println(result);

        // manual refactor
        int sum = 0;
        for(Integer i : values)
            sum += i * i;

        // intellij refactor
        int result = 0;
        for (int v : values) {
            int i = v * v;
            result += i;
        }
         */
    }
}

