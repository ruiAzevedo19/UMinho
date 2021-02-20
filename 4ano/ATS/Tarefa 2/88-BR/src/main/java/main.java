import Controller.Controller;
import Controller.IController;

public class main {
    public static void main(String[] args) throws Exception {
        IController ct = new Controller(0);
        ct.run();
    }
}
