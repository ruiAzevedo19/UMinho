
/**
 * Write a description of class Poligono here.
 * 
 * @author João Saraiva
 * @version 6/2018
 */

import java.util.List;
import java.util.ArrayList;

public abstract class Poligono
{
    private List<Ponto> poligono;
    
    public Poligono()
    { this.poligono = new ArrayList<>();
    }
    
    public Poligono(List<Ponto> pol)
    {  this();
       for (Ponto p : pol)
         this.poligono.add(p.clone());
       this.poligono.add(pol.get(0).clone());
    }
    
    
    public List<Ponto> getPoligono()
    { List<Ponto> pts = new ArrayList<>();
      for (Ponto p : this.poligono)
        pts.add(p.clone());
      return pts;
    }
    
    public void setPoligono(List<Ponto> pts)
    { this.poligono = new ArrayList<>();
      for (Ponto p : pts)
        this.poligono.add(p.clone());
    }
    
    
    public void addPonto(Ponto p)
    { this.poligono.add(p.clone()); }
    
    public boolean fechada()
    { return this.poligono.get(0).equals(this.poligono.get(this.poligono.size()-1));
    }
    
    public double perimetro()
    { double res = 0;
      for (int i = 1 ; i < this.poligono.size() ; i++)
        res += this.poligono.get(i).distancia(this.poligono.get(i-1));
      return res;
    }
    
       
/*
codigo re-utilizado de 

https://stackoverflow.com/questions/471962/how-do-i-efficiently-determine-if-a-polygon-is-convex-non-convex-or-complex


The algorithm is guaranteed to work as long as the vertices are ordered (either clockwise or counter-clockwise), 
and you don't have self-intersecting edges (i.e. it only works for simple polygons)
*/

    public boolean eConvexo() {
        boolean result = true;
        if( this.poligono.size() >= 4 )     // Triangulo é sempre convexo
        {
            boolean sign = false;
            int n = this.poligono.size();
            int i = 0;
            while (i < n) {
                double zcrossproduct = getZcrossproduct( n, i );
                if( i == 0 )
                    sign = zcrossproduct > 0;
                else if( sign != (zcrossproduct > 0) ) {
                    result = false;
                    break;
                }
                i++;
            }
        }
        return result;
    }

    private double getZcrossproduct(int n, int i) {
        double dx1 = poligono.get( (i + 2) % n ).getX() - poligono.get( (i + 1) % n ).getX();
        double dy1 = poligono.get( (i + 2) % n ).getY() - poligono.get( (i + 1) % n ).getY();
        double dx2 = poligono.get( i ).getX() - poligono.get( (i + 1) % n ).getX();
        double dy2 = poligono.get( i ).getY() - poligono.get( (i + 1) % n ).getY();
        return dx1 * dy2 - dy1 * dx2;
    }


    public abstract double area();
       
    public abstract Poligono clone();
    
}
