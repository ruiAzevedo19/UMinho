package creational.abstractFactory;

public class Cliente {
    private Button button;
    private Checkbox checkbox;

    public Cliente(AbstractFactory factory){
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void display(){
        button.display();
        checkbox.display();
    }
}
