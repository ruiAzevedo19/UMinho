
/**
 * Write a description of class Triangulo here.
 * 
 * @author  João Saraiva
 * @version 6/2018
 */

import java.util.ArrayList;

public class Triangulo extends PoligonoConvexo
{
    
    public Triangulo()
    { super();
    
    }
    
    public Triangulo(Ponto p1, Ponto p2, Ponto p3)
    { super();
      super.addPonto(p1.clone());
      super.addPonto(p2.clone());
      super.addPonto(p3.clone());
      super.addPonto(p1.clone());
    }

    public double areaTriangulo() {
        Ponto x = super.getPoligono().get( 0 );
        Ponto y = super.getPoligono().get( 1 );
        Ponto z = super.getPoligono().get( 2 );
        double a = x.distancia( y );
        double b = y.distancia( z );
        double c = z.distancia( x );

        double s = (a + b + c) / 2;  // semi-perimetro
        return Math.sqrt( s * (s - a) * (s - b) * (s - c) );
    }
    
    public Triangulo clone()
    { return new Triangulo(); }
    
    
}
