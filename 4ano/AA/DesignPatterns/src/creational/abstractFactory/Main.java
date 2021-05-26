package creational.abstractFactory;

public class Main {
    public static void main(String[] args) {
        MacOSFactory mac   = new MacOSFactory();
        WindowsFactory win = new WindowsFactory();

        Cliente cli_mac = new Cliente(mac);
        Cliente cli_win = new Cliente(win);

        System.out.println("---| MacOS |---");
        cli_mac.display();

        System.out.println("---| Windows |---");
        cli_win.display();
    }
}
