package com.gms;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int iD;
    private String name;
    private int year;
    private double price;
    private String description;
    @OneToOne
    private Platform platform;

    /* Constructor */
    public Game(){}

    /* Getters & Setters */

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString(){
        return  "Name: " + this.name + "\n" +
                "Year: " + this.year + "\n" +
                "Price: " + this.price + "\n" +
                "Description: " + this.description + "\n";

    }
}
