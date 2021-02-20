package Controller;

import java.util.Scanner;

public class Input {
    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void readStringAndIgnore() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
    }

    public static int readInt() {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        return input;
    }
}
