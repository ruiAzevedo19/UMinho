package trazAqui;

import java.io.Serializable;

public class Ponto implements Serializable {

  /* STARTING functions */

  private float x;
  private float y;

  public Ponto() {
    this.x = 0;
    this.y = 0;
  }

  public Ponto(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Ponto(Ponto p) {
    this.x = p.getX();
    this.y = p.getY();
  }


  /* GET functions */

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }


  /* SET functions */

  public void setX(float newX) {
    this.x = newX;
  }

  public void setY(float newY) {
    this.y = newY;
  }


  /* OTHER functions */

  public Ponto clone() {
    return new Ponto(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;

    Ponto p = (Ponto) obj;

    return p.getX() == this.x && p.getY() == this.y;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("(").append(this.x).append(", ").append(this.y).append(")");

    return sb.toString();
  }
}