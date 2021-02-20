package main;

import controller.Controller;
import controller.IController;

public class Main {
    public static void main(String[] args) throws Exception {
        IController ct = new Controller(0);
        ct.run();
    }
}
