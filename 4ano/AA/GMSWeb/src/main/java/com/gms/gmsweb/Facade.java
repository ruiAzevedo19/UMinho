package com.gms;

import com.gms.Exception.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Facade {
    private Session s;
    private Transaction t;

    public Facade(){
        Configuration configuration = new Configuration().configure();
        SessionFactory sf = configuration.buildSessionFactory();
        this.s = sf.openSession();
        this.s.setHibernateFlushMode(FlushMode.COMMIT);
    }

    /* --- Connection --- */
    public void beginTransaction(){
        this.t = this.s.beginTransaction();
    }

    public void commit(){
        this.t.commit();
    }

    public void rollback(){
        this.t.rollback();
    }

    public void closeSession(){
        this.s.close();
    }

    /* --- GMS --- */
    public boolean hasUser(String name){
        boolean has = false;
        Query query = this.s.createQuery("from User u where name = :name");
        query.setParameter("name", name);
        if( query.list().size() != 0 )
            has = true;
        return has;
    }

    public Game getGameByName(String name){
        Game g = null;
        Query query = this.s.createQuery("from Game g where name=:name");
        query.setParameter("name", name);
        List<Game> result = query.list();

        if( result.size() != 0 )
            g = result.get(0);
        return g;
    }

    public Platform getPlatformByName(String name){
        Platform p = null;
        Query query = this.s.createQuery("from Platform g where name=:name");
        query.setParameter("name", name);
        List<Platform> result = query.list();
        if( result.size() != 0 )
            p = result.get(0);
        return p;
    }

    public void registerUser(User user) throws UserAlreadyExistsException {
        if( hasUser(user.getName()) )
            throw new UserAlreadyExistsException("User " + user.getName() + " already exists!");
        s.save(user);
    }

    public void registerGame(Game game) throws GameAlreadyExistsException {
       if( getGameByName(game.getName()) != null)
           throw new GameAlreadyExistsException("The game " + game.getName() + " already exists!");
       s.save(game);
    }

    public void registerPlatform(Platform p) throws PlatformAlreadyExistsException {
        if( getGameByName(p.getName()) != null)
            throw new PlatformAlreadyExistsException("The  platform " + p.getName() + " already exists!");
        s.save(p);
    }

    public Collection<Game> getUserGames(String name) throws UserDoesntExistsException{
        if( !hasUser(name) )
            throw new UserDoesntExistsException("User " + name + " does not exists!");
        Query query = this.s.createQuery("from User u where u.name=:name");
        query.setParameter("name", name);
        List result = query.list();
        User u = (User)result.get(0);
        return u.getGames();
    }

    public Collection<Game> getAllGames(){
        Query query = this.s.createQuery("from Game g");
        List result = query.list();
        if( result.size() == 0 )
            return new ArrayList<>();
        return new ArrayList<>(result);
    }

    public Game searchGame(String name) throws GameDoesntExistsException{
        Query q = s.createQuery("from Game where name=:name");
        q.setParameter("name",name);
        List result = q.list();
        if(  result.size() == 0 )
            throw new GameDoesntExistsException("The game " + name + " does not exists!");
        return (Game)result.get(0);
    }

    public void deleteGame(String name) throws GameDoesntExistsException{
        Query q1 = this.s.createQuery("from Game u where u.name=:name");
        q1.setParameter("name", name);
        List result = q1.list();
        if( result.size() == 0 )
            throw new GameDoesntExistsException("The game " + name + " does not exists!");
        Game g = (Game)result.get(0);
        Query q2 = this.s.createQuery("from User");
        Collection<User> users = q2.list();
        users.forEach(u -> { u.getGames().remove(g); s.update(u);} );
        g.setPlatform(null);
        s.delete(g);
    }
}
