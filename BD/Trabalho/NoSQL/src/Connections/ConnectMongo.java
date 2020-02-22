package Connections;

import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;

public class ConnectMongo {

    public static MongoClient mongoClient;
    public static MongoDatabase db;


    public  ConnectMongo() {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("Arquivo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public MongoDatabase getDb(){
        return db;
    }

    public void close() {
        try {
            mongoClient.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}