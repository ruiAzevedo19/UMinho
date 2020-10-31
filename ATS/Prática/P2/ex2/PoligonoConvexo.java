
/**
 * Write a description of class PoligonoConvexo here.
 * 
 * @author João Saraiva
 * @version 6/2018
 */

import java.util.List;
import java.util.ArrayList;

public class PoligonoConvexo extends Poligono
{
    
    public PoligonoConvexo()
    { super(); }
    
    public PoligonoConvexo(List<Ponto> pts) 
    { super(pts);
      
    }
    
    /*
    public PoligonoConvexo(List<Ponto> pts) throws NaoConvexoException 
    { super(pts);
      if (! eConvexo())
        throw new NaoConvexoException() ;
    }
    */
    
    public List<Triangulo> triangula()
    {  List<Triangulo> res = new ArrayList<>(); 
       List<Ponto> pts = super.getPoligono();
       Ponto p0 = pts.get(0);
       for (int i = 1 ; i < pts.size()-2 ; i++)
         { Triangulo t = new Triangulo(p0.clone(), pts.get(i).clone(), pts.get(i+1).clone());
           res.add(t);
        }
       return res;
        
    }
    
    public double area ()
    {  List<Triangulo> triangs = triangula(); 
       return (double) triangs.stream()
                              .mapToDouble(t -> t.areaTriangulo())
                              .sum();
    }
    
    
    
 
    public PoligonoConvexo clone ()
    { return new PoligonoConvexo(); }
    
}
