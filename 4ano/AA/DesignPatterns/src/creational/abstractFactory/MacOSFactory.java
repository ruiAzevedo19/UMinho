package creational.abstractFactory;

public class MacOSFactory implements AbstractFactory{
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
       return new MacOSCheckbox();
    }
}
