package Controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginDatabase implements Serializable {
    private final Map<String, String> credentials;

    public LoginDatabase() {
        this.credentials = new HashMap<>();
    }

    public void addLogin(String user, String pass) {
        this.credentials.put(user, pass);
    }

    public boolean validateLogin(String user, String password) {
        boolean valid = false;
        if (this.credentials.containsKey(user))
            valid = this.credentials.get(user).equals(password);
        return valid;
    }

    public void setLogin(String username, String password) {
        this.credentials.put(username, password);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        List<Map.Entry<String, String>> sortedLogins = this.credentials.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        for (Map.Entry<String, String> entry : sortedLogins)
            builder.append(entry.getKey()).append(" => ").append(entry.getValue()).append("\n");

        return builder.toString();
    }

    public boolean containsUser(String username) {
        return this.credentials.containsKey(username);
    }
}
