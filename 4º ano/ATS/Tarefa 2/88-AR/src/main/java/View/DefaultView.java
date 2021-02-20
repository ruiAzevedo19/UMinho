package view;

import static java.lang.System.*;

public class DefaultView implements View {
    @Override
    public void show(Object o) {

        out.print(o);
    }
}
