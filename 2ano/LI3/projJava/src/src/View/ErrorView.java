package View;

import View.Interface.IView;

public class ErrorView implements IView {

    public void show(){

    }

    public void show(Object o){
        System.out.println("/!\\ Error : " + o + " /!\\");
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
