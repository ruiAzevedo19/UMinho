package Model;

import Exception.*;

import java.util.List;

public interface SoundCloud {
    void registration(String username, String password) throws AlreadyRegistedException;
    void authentication(String username, String password) throws InvalidLoginException;
    List<Music> consult(String type, String[] args);
    Music download(int id) throws InvalidIDException;
    int upload(Music m);
}
