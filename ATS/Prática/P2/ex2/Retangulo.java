
/**
 * Write a description of class Retangulo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.List;
import java.util.ArrayList; 

public class Retangulo extends PoligonoConvexo
{
    
    public Retangulo()
    { super(); }
   
    
    public Retangulo(Ponto p1, Ponto p2, Ponto p3,Ponto p4)
    { ArrayList<Ponto> pts = new ArrayList<>();
      pts.add(p1.clone());
      pts.add(p2.clone());
      pts.add(p3.clone());
      pts.add(p4.clone());
      pts.add(p1.clone());
      super.setPoligono(pts);
    }
    
    
    public double areaQuadrado()
    { List<Ponto> pts = super.getPoligono();
      Ponto p1 = pts.get(0);
      Ponto p2 = pts.get(1);
      double lado1 = p1.distancia(p2); 
      Ponto p3 = pts.get(2);
      double lado2 = p2.distancia(p3); 
      
      
      return lado1 * lado2; }
    
    
    public Retangulo clone()
    { return new Retangulo(); }
    
    
    
}
