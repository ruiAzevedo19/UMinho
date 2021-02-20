package View;

import View.Interface.IRequestView;
import View.Interface.IView;

public class RequestView implements IRequestView {

    public void clearScreen() {
        for(int i = 0; i < 50; i++)
            System.out.println();
    }

    public void showTime(double time){
        System.out.println("Tempo: " + time + "s");
        System.out.println();
    }

    public void show(Object o) {
        System.out.print(o + " <$> ");
    }

    public void printLine(){
        System.out.println();
    }

    public void printMessage(Object o){
        System.out.println(o);
    }

    public void printQueryHeader(String h){
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t" + h);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("                                                                             Sair : S");
        System.out.println();
    }
}
