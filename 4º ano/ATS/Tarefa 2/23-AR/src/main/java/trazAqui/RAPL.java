package trazAqui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RAPL {
     public static void main(String[] args) {
          TrazAqui ta = new TrazAqui();

          // --- Read log file -------------------------------------------------------------------------------------- //
          try {
               ta = ta.leFicheiro( "./TrazAqui.object" );
          } catch(FileNotFoundException e) {
               System.out.println("File " + args[0]);
               ta.parseTotal( args[0] );
          } catch(ClassNotFoundException | IOException e) {
               System.out.println( e.getMessage() );
               return;
          }
          // --- Methods -------------------------------------------------------------------------------------------- //
          int i, n = 10000;
          Utilizadores u;
          Lojas l;
          Encomenda e;
          for(i = 0; i < n; i++){
               // --- add users ---
               String code = "u" + (100 + i);
               String name = "nameUser " + (100 + i);
               u = new Utilizadores(code,name,"","",new Ponto(),new HashMap<>());
               ta.addUtilizador( u );
               // --- add stores ---
               code = "l" + (100 + i);
               String pass = "123";
               name = "nameStore " + (100 + i);
               l = new Lojas(code,pass,name,new Ponto(),100 + i, new HashMap<>());
               ta.addLoja( l );
               // --- add order ---
               code = "e" + (100 + i);
               e = new Encomenda(code, "u" + (100 + i), "l" + (100 + i), (float)100 + i, LocalDateTime.now(), new ArrayList<>() );
               ta.addEncomenda( e );
          }
          // --- add orders to users ---
          for(i = 0; i < n; i++){
               u = ta.getUtilizador( "u" + (100 + i) );
               for(i = 0; i < n; i++) {
                    u.adicionarEncomenda( ta.getEncomenda( "e" + (100 + i) )  );
               }
          }
          // --- calculate top10 users
          List<Utilizadores> uti = ta.ordenadoTop10U();
          uti.forEach( System.out::println );

          // --- recommends carriers ---
          for(i = 0; i < n; i++){
               e = ta.getEncomenda( "e" + (100 + i) );
               ta.recomendarClassificada( e, true );
          }
     }
}
