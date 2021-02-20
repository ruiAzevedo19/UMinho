package Model;

import java.io.Serializable;

public class GPS implements Serializable {
    private double x;
    private double y;

    public GPS() {
        this.x = 0;
        this.y = 0;
    }

    public GPS(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GPS(GPS outro) {
        this.x = outro.getX();
        this.y = outro.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double calculaDistancia (GPS outro) {
        return Math.sqrt(Math.pow(outro.getX() - this.x , 2) + Math.pow(outro.getY() - this.y , 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        GPS body = (GPS) obj;

        return body.getX() == this.getX() && body.getY() == this.getY();
    }

    @Override
    public GPS clone() {
        return new GPS(this);
    }

    @Override
    public String toString() {
        String coords = "Coordenadas:\n";
        coords += "Pos X: " + this.getX() + ";\n";
        coords += "Pos Y: " + this.getY() + ";\n";

        return coords;
    }
}
