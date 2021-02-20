package controller;

import java.util.Scanner;

public class Input {

    private Input(){

    }


    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void readStringAndIgnore() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
