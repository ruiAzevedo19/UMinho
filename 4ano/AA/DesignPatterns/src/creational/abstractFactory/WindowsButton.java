package creational.abstractFactory;

public class WindowsButton implements Button {
    @Override
    public void display() {
        System.out.println("Created an Windows button...");
    }
}
