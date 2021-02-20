package controller;

import view.DefaultView;
import view.View;

import java.beans.PropertyChangeSupport;

public abstract class Login extends Controller {
    private PropertyChangeSupport changes;
    private String username;

    protected Login(Controller controller) {
        super(controller);
        this.changes = new PropertyChangeSupport(this);
        this.username = null;
    }

    public PropertyChangeSupport getChanges() {
        return changes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void changePassword() {
        View v = new DefaultView();
        v.show(Messages.NEWPASSWORD);
        String pw1 = Input.readString();
        v.show(Messages.NEWPASSWORDCONFIRMATION);
        String pw2 = Input.readString();
        if (username != null) {
            if (pw1.equals(pw2))
                super.setLogin(this.username, pw1);
            else
                v.show(Messages.PASSWORDCONFIRMATIONINVALID);
        } else {
            v.show("Wrong usage of method");
        }
    }
}
