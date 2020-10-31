
/**
 * Write a description of class Ponto here.
 * 
 * @author Joao Saraiva 
 * @version 2010/11
 */
import static java.lang.Math.*; 

public class Ponto { 
 
  
   private double x, y; 
 
   // Construtores usuais 
   public Ponto(double x, double y) 
   { this.x = x; this.y = y; } 
   
   public Ponto()
   
   { this(0.0, 0.0); }  // usa o outro construtor 
   public Ponto(Ponto p) 
   { x = p.getX(); y = p.getY(); } 
 
   
   // M�todos de Inst�ncia 
   public double getX() { return this.x; } 
   public double getY() { return this.y; } 
   
   public void setX(double x) {this.x = x;}
   public void setY(double y) {this.y = y;}
 
   /** incremento das coordenadas */ 
   public void incCoord(double dx, double dy) { 
      this.x += dx; this.y += dy; 
   } 
   /** decremento das coordenadas */ 
   public void decCoord(double dx, double dy) { 
      this.x -= dx; this.y -= dy; 
   } 

    /** soma as coordenadas do ponto par�metro ao ponto receptor */ 
   public void somaPonto(Ponto p) { 
      this.x += p.getX(); this.y += p.getY(); 
   } 
   /** soma os valores par�metro e devolve um novo ponto */ 
   public Ponto somaPonto(double dx, double dy) { 
     return new Ponto(this.x += dx, this.y+= dy); 
   } 
   /* determina se um ponto � sim�trico (dista do eixo dos XX o 
       mesmo que do eixo dos YY */ 
   public boolean simetrico() { 
     return abs(this.x) == abs(this.y); 
   } 
 
   /** verifica se ambas as coordenadas s�o positivas */ 
   public boolean coordPos() { 
     return this.x > 0 && this.y > 0; 
   }  
 
   /** Distancia entre dois pontos */
   public double distancia (Ponto p)
   { return Math.sqrt( (Math.pow(p.getX()- this.x,2)) 
                     + (Math.pow(p.getY() - this.y,2))
                     );
       
    }
   
   // M�todos complementares usuais 
 
   public boolean equals (Object o)
    { if (this == o) 
        { return true; }
      
      if ((o == null) || (o.getClass() != this.getClass()))
        { return false; }
      
      Ponto p = (Ponto) o;
      return ( this.x == p.getX() && this.y == p.getY());
    }
   
   public String toString()
   {
     return ("x : " + this.x + " y: " + this.y);   
   }
    
    
   
 
   /** Cria uma c�pia do ponto receptor (receptor = this) */ 
   public Ponto clone() { 
      return new Ponto(this);  
   } 

   
}