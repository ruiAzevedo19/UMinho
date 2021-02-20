package trazAqui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App implements Serializable {

     private TrazAqui ta;
     private mInicial menuInicial;
     private mLogIn menuLogIn;
     private mSignUp menuSignUp;
     private mCliente menuCliente;
     private mPlataformas menuPlataformas;
     private String codigoE;
     private String codigoPE;
     private String codigoU;

     public static void main(String[] args) {
          new App().startInicial();
     }

     // Método que lê o ficheiro object (se este nao existir lê o ficheiro csv)
     public App() {
          ta = new TrazAqui();

          try {
               ta = ta.leFicheiro( "./TrazAqui.object" );
               System.out.println("\nFicheiro de dados encontrado.\nA iniciar...\n" );
          } catch(FileNotFoundException e) {
               System.out.println( "\nNão existe nenhum ficheiro compatível.\nA iniciar...\n" );
               ta.parseTotal( "TrazAquiLOGS.csv" );
          } catch(ClassNotFoundException | IOException e) {
               System.out.println( e.getMessage() );
               return;
          }

          this.menuInicial = new mInicial();
          this.menuLogIn = new mLogIn();
          this.menuSignUp = new mSignUp();
          this.menuCliente = new mCliente();
          this.menuPlataformas = new mPlataformas();
     }

     // Método que lê a opção pretendida do menu inicial e redireciona para essa função (login/signup/leaderbords)
     public void startInicial() {
          boolean flagB = true;
          this.menuInicial.setOpcao( 0 );
          this.codigoE = "";
          this.codigoPE = "";
          this.codigoU = "";

          while( this.menuInicial.getOpcao() != 4 ) {
               System.out.print( "\tTraz Aqui\n\n" );
               this.menuInicial.mostrarMenuInicial();

               switch(this.menuInicial.getOpcao()) {
                    case 1: // Log in
                         this.menuInicial.clearScreen();
                         startLogIn();

                         flagB = false;
                         break;
                    case 2: // Sign up
                         this.menuInicial.clearScreen();
                         startSignUp();

                         flagB = false;
                         break;
                    case 3: // Leaderboards
                         this.menuInicial.clearScreen();
                         startClassificacoes();

                         flagB = false;
                         break;
                    case 4: // Sair

                         break;
                    default:
                         this.menuInicial.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               System.out.print( "\nA desligar...\nAté breve!!!\n" );

               try {
                    this.ta.gravaCSV( "TrazAquiLOGS.csv" );
               } catch(FileNotFoundException e) {
                    System.out.println( e.getMessage() );
               }

               try {
                    this.ta.gravaFicheiro( "TrazAqui.object" );
               } catch(FileNotFoundException e) {
                    System.out.println( e.getMessage() );
               } catch(IOException e) {
                    System.out.println( e.getMessage() );
               }
          }
     }


     // Método que lê a opção pretendida do menu de signup podendo fazer signup com qualquer um dos tipos de utilizadores da plataforma
     public void startSignUp() {
          boolean flagB = true;
          boolean flagSignUp = true;
          this.menuSignUp.setOpcao( 0 );
          this.menuSignUp.clearScreen();
          String codigo;
          String email;

          while( this.menuSignUp.getOpcao() != 5 && flagSignUp ) {
               this.menuSignUp.escolhaMenu();

               switch(this.menuSignUp.getOpcao()) {
                    case 1: // Utilizador
                         this.menuSignUp.clearScreen();
                         this.menuSignUp.mostrarMenuSignUpU();

                         try {
                              codigo = this.menuSignUp.getCodigo();
                              boolean codigoEX = ta.codigoSignUp( codigo );
                         } catch(exCodigoAlreadyExists e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         try {
                              email = this.menuSignUp.getEmail();

                              String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                              boolean flag = email.matches( regex );

                              boolean emailEX = false;
                              if( flag )
                                   emailEX = ta.emailSignUp( email );
                              else {
                                   menuSignUp.clearScreen();
                                   System.out.print( "\nInseriu um email inválido.\n" );
                                   System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                                   startInicial();
                                   return;
                              }
                         } catch(exEmailAlreadyExists e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         Ponto GPSU = this.menuSignUp.getGPS();
                         String nomeU = this.menuSignUp.getNome();
                         Map<String, Encomenda> encomendasGuardadasU = new HashMap<>();
                         Utilizadores u = new Utilizadores( codigo, nomeU, email, "TrazAqui", GPSU, encomendasGuardadasU );
                         ta.addUtilizador( u );

                         flagSignUp = false;
                         menuSignUp.clearScreen();
                         System.out.print( "Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n" );
                         startInicial();

                         flagB = false;
                         break;
                    case 2: // Loja
                         this.menuSignUp.clearScreen();
                         this.menuSignUp.mostrarMenuSignUpL();

                         try {
                              boolean lojaEX = ta.lojaVerifica( this.menuSignUp.getNome(), this.menuSignUp.getGPS() );
                         } catch(exLojaAlreadyExists e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         String nomeL = this.menuSignUp.getNome();
                         String codigoL = nomeL.replaceAll( " ", "" );
                         Ponto GPSL = this.menuSignUp.getGPS();
                         Map<String, Encomenda> registoEncomendas = new HashMap<>();
                         Lojas l = new Lojas( codigoL, "TrazAqui", nomeL, GPSL, 0, registoEncomendas );
                         ta.addLoja( l );

                         flagSignUp = false;
                         menuSignUp.clearScreen();
                         System.out.print( "Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n" );
                         startInicial();

                         flagB = false;
                         break;
                    case 3: // Transportadora
                         this.menuSignUp.clearScreen();
                         this.menuSignUp.mostrarMenuSignUpT();

                         try {
                              boolean tEX = ta.peVerifica( this.menuSignUp.getNome(), this.menuSignUp.getGPS() );
                         } catch(exPEAlreadyExists e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         try {
                              boolean raioEXT = ta.verificaValoresDOUBLE( this.menuSignUp.getRaio() );
                              boolean precoPorKM_EX = ta.verificaValoresDOUBLE( this.menuSignUp.getPrecoPorKM() );
                         } catch(exNegativeValues e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         try {
                              boolean NIF_EX = ta.verificaNIF( this.menuSignUp.getNIF() );
                         } catch(exInvalidNIF e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         String nomeT = this.menuSignUp.getNome();
                         String codigoT = nomeT.replaceAll( " ", "" );
                         Ponto GPST = this.menuSignUp.getGPS();
                         double raioT = this.menuSignUp.getRaio();
                         boolean transporteMedicamentosT = this.menuSignUp.getTransporteMedicamentos();
                         List<Integer> classificacoesT = new ArrayList<>();
                         Map<String, Encomenda> encomendasGuardadasT = new HashMap<>();
                         String NIF = this.menuSignUp.getNIF();
                         Double precoPorKMT = this.menuSignUp.getPrecoPorKM();
                         Transportadoras t = new Transportadoras( codigoT, "TrazAqui", nomeT, GPST, raioT, transporteMedicamentosT, true, classificacoesT, encomendasGuardadasT, NIF, precoPorKMT, 5 );
                         ta.addPlataforma( t );

                         flagSignUp = false;
                         menuSignUp.clearScreen();
                         System.out.print( "Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n" );
                         startInicial();

                         flagB = false;
                         break;
                    case 4: // Voluntário
                         this.menuSignUp.clearScreen();
                         this.menuSignUp.mostrarMenuSignUpV();

                         try {
                              boolean vEX = ta.peVerifica( this.menuSignUp.getNome(), this.menuSignUp.getGPS() );
                         } catch(exPEAlreadyExists e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         try {
                              boolean raioEXV = ta.verificaValoresDOUBLE( this.menuSignUp.getRaio() );
                         } catch(exNegativeValues e) {
                              this.menuSignUp.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         String nomeV = this.menuSignUp.getNome();
                         String codigoV = nomeV.replaceAll( " ", "" );
                         Ponto GPSV = this.menuSignUp.getGPS();
                         double raioV = this.menuSignUp.getRaio();
                         boolean transporteMedicamentosV = this.menuSignUp.getTransporteMedicamentos();
                         List<Integer> classificacoesV = new ArrayList<>();
                         Map<String, Encomenda> encomendasGuardadasV = new HashMap<>();
                         Voluntarios v = new Voluntarios( codigoV, "TrazAqui", nomeV, GPSV, raioV, transporteMedicamentosV, true, classificacoesV, encomendasGuardadasV );
                         ta.addPlataforma( v );

                         flagSignUp = false;
                         menuSignUp.clearScreen();
                         System.out.print( "Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n" );
                         startInicial();

                         flagB = false;
                         break;
                    case 5:

                         break;
                    default:
                         menuInicial.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );
                         startInicial();

                         continue;
               }
          }

          if( flagB ) {
               this.menuLogIn.clearScreen();
               System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de login podendo fazer login com qualquer um dos tipos de utilizadores da plataforma
     public void startLogIn() {
          boolean flagB = true;
          boolean flagLogIn = true;
          this.menuLogIn.clearScreen();
          this.menuLogIn.setOpcao( 0 );
          String codigo = new String();
          String email = new String();
          String password = new String();

          while( this.menuLogIn.getOpcao() != 5 && flagLogIn ) {
               this.menuLogIn.escolhaMenuGERAL();

               switch(this.menuLogIn.getOpcao()) {
                    case 1: // Utilizador
                         this.menuLogIn.clearScreen();
                         this.menuLogIn.escolhaMenuLogIn();

                         if( this.menuLogIn.getOpcao() == 1 ) { // Email
                              menuLogIn.clearScreen();
                              menuLogIn.mostrarMenuLogInE();

                              try {
                                   email = this.menuLogIn.getEmail();
                                   boolean emailEX = ta.emailLogIn( email );
                              } catch(exEmailDoesNotExist e) {
                                   this.menuSignUp.clearScreen();
                                   System.out.println( e.getMessage() );
                                   System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                                   startInicial();
                                   return;
                              }

                              try {
                                   password = this.menuLogIn.getPassword();
                                   boolean passwordEX = ta.pwVerificaE( email, password );
                              } catch(exPWIncorrect e) {
                                   this.menuLogIn.clearScreen();
                                   System.out.println( e.getMessage() );
                                   System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                                   startInicial();
                                   return;
                              }

                              Utilizadores u = ta.getUtilizador( email );
                              this.codigoU = u.getCodigo();

                              flagLogIn = false;
                              this.menuLogIn.clearScreen();
                              startCliente();
                         }

                         if( this.menuLogIn.getOpcao() == 2 ) { // Código
                              try {
                                   this.menuLogIn.clearScreen();
                                   this.menuLogIn.mostrarMenuLogInC();
                                   codigo = this.menuLogIn.getCodigo();
                                   boolean codigoEX = ta.codigoLogIn( codigo, 1 );
                              } catch(exCodigoDoesNotExist e) {
                                   this.menuSignUp.clearScreen();
                                   System.out.println( e.getMessage() );
                                   System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                                   startInicial();
                                   return;
                              }

                              try {
                                   password = this.menuLogIn.getPassword();
                                   boolean passwordEX = ta.pwVerificaC( codigo, password, 1 );
                              } catch(exPWIncorrect e) {
                                   this.menuLogIn.clearScreen();
                                   System.out.println( e.getMessage() );
                                   System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                                   startInicial();
                                   return;
                              }

                              this.codigoU = codigo;

                              flagLogIn = false;
                              this.menuLogIn.clearScreen();
                              startCliente();
                         }

                         if( this.menuLogIn.getOpcao() == 3 ) {
                              this.menuLogIn.clearScreen();
                              System.out.print( "A redireciona-lo para o menu de log in...\n\n\n" );
                              startLogIn();
                         }

                         flagB = false;
                         break;
                    case 2:
                    case 3:
                    case 4: // Loja|Utilizadores|Voluntários
                         int opcaoAUX = this.menuLogIn.getOpcao();
                         boolean codigoEX;
                         boolean passwordEX;

                         try {
                              this.menuLogIn.clearScreen();
                              this.menuLogIn.mostrarMenuLogInExtra();

                              codigo = this.menuLogIn.getCodigo();
                              if( opcaoAUX == 2 )
                                   codigoEX = ta.codigoLogIn( codigo, 2 );
                              if( opcaoAUX == 3 )
                                   codigoEX = ta.codigoLogIn( codigo, 3 );
                              if( opcaoAUX == 4 )
                                   codigoEX = ta.codigoLogIn( codigo, 4 );
                         } catch(exCodigoDoesNotExist e) {
                              this.menuLogIn.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         try {
                              password = this.menuLogIn.getPassword();
                              if( opcaoAUX == 2 )
                                   passwordEX = ta.pwVerificaC( codigo, password, 2 );
                              if( opcaoAUX == 3 )
                                   passwordEX = ta.pwVerificaC( codigo, password, 3 );
                              if( opcaoAUX == 4 )
                                   passwordEX = ta.pwVerificaC( codigo, password, 4 );
                         } catch(exPWIncorrect e) {
                              this.menuLogIn.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
                              startInicial();
                              return;
                         }

                         flagLogIn = false;
                         this.codigoU = codigo;
                         this.menuLogIn.clearScreen();

                         if( opcaoAUX == 2 )
                              startLoja();
                         if( opcaoAUX == 3 )
                              startTransportadora();
                         if( opcaoAUX == 4 )
                              startVoluntarios();

                         flagB = false;
                         break;
                    case 5: // Voltar atrás

                         break;
                    default:
                         menuLogIn.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuLogIn.clearScreen();
               System.out.print( "A redireciona-lo para o menu inicial...\n\n\n" );
               startInicial();
          }
     }


     // Método que lê a opção pretendida do menu de loja podendo verificar o número de pessoas na fila, ver as encomendas feitas ate à data ou periodo de tempo
     // e acesso as defeniçoes de uma transportadora
     public void startLoja() {
          boolean flagB = true;
          Lojas l = ta.getLoja( codigoU );
          this.menuPlataformas.setOpcao( 0 );

          while( this.menuPlataformas.getOpcao() != 5 ) {
               System.out.print( "\n\t" + l.getNome() + "\n\n" );
               this.menuPlataformas.menuLoja();

               switch(this.menuPlataformas.getOpcao()) {
                    case 1: // Número de pessoas na fila
                         this.menuPlataformas.clearScreen();

                         int fila = l.getNumeroEncomendas();
                         if( fila == 1 )
                              System.out.print( "Neste momento está " + fila + " pessoa na fila.\n" );
                         else
                              System.out.print( "Neste momento estão " + fila + " pessoas na fila.\n" );

                         System.out.print( "\nInsira um caracter para voltar para o menu principal.\n" );
                         String i1 = this.menuCliente.leString();
                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu principal...\n\n\n" );

                         continue;
                    case 2: // Registo de encomendas até à data
                         this.menuPlataformas.clearScreen();

                         List<Encomenda> historicoL = l.historicoL();
                         if( historicoL.size() > 0 ) {
                              System.out.print( "Histórico de encomendas realizadas:\n\n" );
                              for(Encomenda e : historicoL)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer encomenda." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i2 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 3: // Registo de encomendas num certo período de tempo
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataI();
                         LocalDateTime dataI = this.menuPlataformas.getDataI();

                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataF();
                         LocalDateTime dataF = this.menuPlataformas.getDataF();

                         List<Encomenda> historicoLD = l.historicoLdata( dataI, dataF );
                         this.menuPlataformas.clearScreen();
                         if( historicoLD.size() > 0 ) {
                              System.out.print( "Histórico de encomendas realizadas:\n" );
                              System.out.print( "\tDe " + dataI + " até " + dataF + "\n\n" );
                              for(Encomenda e : historicoLD)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer encomenda." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i3 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 4: // Alterar password
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarPW();

                         String pwN = this.menuPlataformas.getPW();
                         l.setPW( pwN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Password modificada com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu principal.\n" );
                         String i4 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 5: // Log out

                         break;
                    default:
                         this.menuPlataformas.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuPlataformas.clearScreen();
               System.out.print( "Sessão encerrada com sucesso.\n\n\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de transportadora podendo verificar as encomendas feitas ou total faturado ate à data ou periodo de tempo,
     // classificações da empresa e ainda aceder às definições
     public void startTransportadora() {
          boolean flagB = true;
          PlataformaEntrega pe = ta.getPlataformaEntrega( codigoU );
          Transportadoras t = (Transportadoras) pe;
          this.menuPlataformas.setOpcao( 0 );

          while( this.menuPlataformas.getOpcao() != 7 ) {
               System.out.print( "\n\t" + t.getNome() + "\n\n" );
               this.menuPlataformas.menuTransportadoras();

               switch(this.menuPlataformas.getOpcao()) {
                    case 1: // Faturado até à data
                         this.menuPlataformas.clearScreen();

                         double faturadoT = ta.totalDinheiro( codigoU );
                         System.out.print( "O total faturado é de " + faturadoT + ".\n" );

                         System.out.print( "\nInsira um caracter para voltar ao menu principal.\n" );
                         String i1 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 2: // Faturado certo período de tempo
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataI();
                         LocalDateTime dataI = this.menuPlataformas.getDataI();

                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataF();
                         LocalDateTime dataF = this.menuPlataformas.getDataF();

                         if( dataI.equals( dataF ) ) {
                              this.menuPlataformas.clearScreen();
                              System.out.print( "No período de tempo escolhido, não houve qualquer ganho.\n" );
                         }else if( dataI.isBefore( dataF ) ) {
                              this.menuPlataformas.clearScreen();
                              double faturadoD = ta.totalDinheiroTempo( codigoU, dataI, dataF );
                              System.out.print( "O total faturado de " + dataI + " até " + dataF + " é de " + faturadoD + ".\n" );
                         }else {
                              this.menuPlataformas.clearScreen();
                              double faturadoD = ta.totalDinheiroTempo( codigoU, dataF, dataI );
                              System.out.print( "O total faturado de " + dataF + " até " + dataI + " é de " + faturadoD + ".\n" );
                         }

                         System.out.print( "\nInsira um caracter para voltar ao menu principal.\n" );
                         String i2 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 3: // Classificação atual
                         this.menuPlataformas.clearScreen();

                         System.out.print( "A classificação atual da sua empresa é de " + t.mediaClassificacao( t.getClassificacoes() ) + ".\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu principal.\n" );
                         String i3 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 4: // Registo de entregas realizadas até à data
                         this.menuPlataformas.clearScreen();

                         List<Encomenda> historicoT = t.historicoPE();
                         if( historicoT.size() > 0 ) {
                              System.out.print( "Histórico de entregas realizadas:\n\n" );
                              for(Encomenda e : historicoT)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer entrega." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i4 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 5: // Registo de entregas realizadas num certo período
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataI();
                         LocalDateTime dataIe = this.menuPlataformas.getDataI();

                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataF();
                         LocalDateTime dataFe = this.menuPlataformas.getDataF();

                         List<Encomenda> historicoPEd = t.historicoPEdata( dataIe, dataFe );
                         this.menuPlataformas.clearScreen();
                         if( historicoPEd.size() > 0 ) {
                              System.out.print( "Histórico de encomendas realizadas:\n" );
                              System.out.print( "\tDe " + dataIe + " até " + dataFe + "\n\n" );
                              for(Encomenda e : historicoPEd)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer encomenda." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i5 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 6: // Definições
                         this.menuPlataformas.clearScreen();
                         startDefinicoesT();

                         flagB = false;
                         break;
                    case 7: // Log out

                         break;
                    default:
                         this.menuPlataformas.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuPlataformas.clearScreen();
               System.out.print( "Sessão encerrada com sucesso.\n\n\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de voluntário podendo consultar a sua clasificação, ver as encomendas feitas ate à data ou periodo de tempo
     // e ainda aceder às definições
     public void startVoluntarios() {
          boolean flagB = true;
          PlataformaEntrega pe = ta.getPlataformaEntrega( codigoU );
          Voluntarios v = (Voluntarios) pe;
          this.menuPlataformas.setOpcao( 0 );

          while( this.menuPlataformas.getOpcao() != 5 ) {
               System.out.print( "\n\t" + v.getNome() + "\n\n" );
               this.menuPlataformas.menuVoluntarios();

               switch(this.menuPlataformas.getOpcao()) {
                    case 1: // Classificação
                         this.menuPlataformas.clearScreen();

                         System.out.print( "A sua classificação atual é de " + v.mediaClassificacao( v.getClassificacoes() ) + ".\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu principal.\n" );
                         String i1 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 2: // Registo das entregas até à data
                         this.menuPlataformas.clearScreen();

                         List<Encomenda> historicoV = v.historicoPE();
                         if( historicoV.size() > 0 ) {
                              System.out.print( "Histórico de entregas realizadas:\n\n" );
                              for(Encomenda e : historicoV)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer entrega." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i2 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 3: // Registo das entregas num certo período de tempo
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataI();
                         LocalDateTime dataI = this.menuPlataformas.getDataI();

                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuDataF();
                         LocalDateTime dataF = this.menuPlataformas.getDataF();

                         List<Encomenda> historicoVd = v.historicoPEdata( dataI, dataF );
                         this.menuPlataformas.clearScreen();
                         if( historicoVd.size() > 0 ) {
                              System.out.print( "Histórico de entregas realizadas:\n" );
                              System.out.print( "\tDe " + dataI + " até " + dataF + "\n\n" );
                              for(Encomenda e : historicoVd)
                                   System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                         }else
                              System.out.print( "Ainda não realizou qualquer entrega." );

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i3 = this.menuPlataformas.leString();
                         this.menuPlataformas.clearScreen();

                         continue;
                    case 4: // Definições
                         this.menuPlataformas.clearScreen();
                         startDefinicoesV();

                         flagB = false;
                         break;
                    case 5: // Log out

                         break;
                    default:
                         this.menuPlataformas.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuPlataformas.clearScreen();
               System.out.print( "Sessão encerrada com sucesso.\n\n\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de cliente podendo aceder ao menu de encomenda, definições de cliente e informações adicionais
     public void startCliente() {
          boolean flagB = true;
          Utilizadores u = ta.getUtilizador( codigoU );
          this.menuCliente.setOpcao( 0 );

          while( this.menuCliente.getOpcao() != 4 ) {
               System.out.print( "\n\t" + u.getNome() + "\n\n" );
               this.menuCliente.escolhaMenu();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // Encomenda
                         this.menuCliente.clearScreen();
                         startEncomenda();

                         flagB = false;
                         break;
                    case 2: // Definiçoes
                         this.menuCliente.clearScreen();
                         startDefinicoesC();

                         flagB = false;
                         break;
                    case 3: // Informações adicionais
                         this.menuCliente.clearScreen();
                         startInformacoesAdicionais();

                         flagB = false;
                         break;
                    case 4: // LogOut

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "Sessão encerrada com sucesso.\n\n\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de encomenda podendo um cliente efetuar uma encomenda, ver o estado da mesma, pedir a entrega da encomenda
     // depois de a ter efetuado e classificar
     public void startEncomenda() {
          boolean flagB = true;
          this.menuCliente.setOpcao( 0 );

          while( this.menuCliente.getOpcao() != 5 ) {
               this.menuCliente.menuEncomenda();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // Estado da encomenda
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuEstadoEncomenda();

                         if( this.menuCliente.getFlag() ) {
                              String codigoL = this.menuCliente.getCodigoL();
                              Lojas l = ta.getLoja( codigoL );
                              int tempoM = ta.tempoMedioAtendimento( codigoL );
                              float custoEncomenda = ta.getEncomenda( codigoE ).custoEncomendaTotal();
                              System.out.print( "\t" + tempoM + " minutos\n\nCusto da encomenda\n\t" + custoEncomenda + " €\n" );
                         }

                         System.out.print( "\n\nInsira um caracter para voltar atrás.\n" );
                         String i1 = this.menuCliente.leString();
                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );

                         continue;
                    case 2: // Inserir encomenda
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuInserirEncomenda();

                         String codigoEAUX = this.menuCliente.getCodigoE();
                         Encomenda e = new Encomenda();

                         try {
                              e = ta.getEncomenda( codigoEAUX );
                              boolean eAUX = ta.verificaEncomenda( codigoEAUX );
                         } catch(exEncomendaDoesNotExist ex) {
                              this.menuCliente.clearScreen();
                              System.out.println( ex.getMessage() );
                              System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                              startEncomenda();
                         }

                         try {
                              String lCodigo = e.getCodLoja();
                              boolean lCodigoAUX = ta.verificaLoja( lCodigo );
                         } catch(exLojaDoesNotExist ex) {
                              this.menuCliente.clearScreen();
                              System.out.println( ex.getMessage() );
                              System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                              startEncomenda();
                         }

                         if( e.getCodUtilizador().equals( codigoU ) ) {
                              this.menuCliente.setFlag( true );
                              this.codigoE = codigoEAUX;
                              ta.encomendaAceite( e );
                              this.menuCliente.clearScreen();
                              System.out.print( "Encomenda adicionada com sucesso!\n" );
                         }else {
                              this.menuCliente.clearScreen();
                              System.out.print( "Inseriu uma encomenda inválida.\n" );
                         }

                         System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );

                         continue;
                    case 3: // Pedir entrega
                         this.menuCliente.clearScreen();
                         startEntrega();

                         flagB = false;
                         break;
                    case 4: // Classificar serviço entrega
                         this.menuCliente.clearScreen();
                         boolean flagRating = true;

                         if( this.menuCliente.getFlagPE() )
                              System.out.print( "Avalie o serviço de 1 a 10\n" );

                         while( flagRating ) {
                              if( this.menuCliente.getFlagPE() ) {
                                   int rating = this.menuCliente.leInt();
                                   if( rating < 1 || rating > 10 ) {
                                        this.menuCliente.clearScreen();
                                        System.out.print( "Por favor, introduza uma classificação entre 1 e 10.\n" );
                                   }else {
                                        ta.classificarPlataformaEntrega( codigoPE, rating );
                                        flagRating = false;
                                   }
                              }else {
                                   System.out.print( "Ainda não pediu nenhum transporte para a sua encomenda.\n" );
                                   System.out.print( "\nInsira um caracter para voltar atrás.\n" );
                                   String i2 = this.menuCliente.leString();
                                   this.menuCliente.clearScreen();
                                   System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                                   startEncomenda();
                              }
                         }

                         this.menuCliente.clearScreen();
                         System.out.print( "Obrigado pela avaliação!\n" );
                         System.out.print( "A redireciona-lo para o menu cliente...\n\n\n" );
                         startCliente();

                         flagB = false;
                         break;
                    case 5: // Voltar atrás

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu cliente...\n\n\n" );
               startCliente();
          }
     }

     // Método que lê a opção pretendida do menu de informações adicionais podendo um cliente ver o registo de encomenda até à data e entre datas
     public void startInformacoesAdicionais() {
          this.menuCliente.setOpcao( 0 );
          boolean flagB = true;

          while( this.menuCliente.getOpcao() != 2 ) {
               this.menuCliente.menuInformacoesAdicionais();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // Registo de encomendas de voluntário/transportadora
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuRegistoEscolha();

                         int opcaoAUX = this.menuCliente.getOpcao();

                         if( opcaoAUX == 1 ) {
                              this.menuCliente.clearScreen();
                              this.menuCliente.menuRegistoV();
                              String codigoRegisto = this.menuCliente.getCodigoRegisto();

                              List<Encomenda> historicoE = new ArrayList<>();
                              PlataformaEntrega pe = ta.getPlataformaEntrega( codigoRegisto );

                              try {
                                   String codigoPEAUX = codigoRegisto;
                                   boolean codigoPEb = ta.verificaT( codigoPEAUX );
                              } catch(exTransportadoraDoesNotExist e) {
                                   this.menuCliente.clearScreen();
                                   System.out.println( "Código inválido.\n" );
                                   System.out.print( "A redireciona-lo para o menu de informações adicionais...\n\n\n" );
                                   startInformacoesAdicionais();
                              }

                              if( pe.tipoPlataformaEntrega().equals( "Voluntario" ) ) {
                                   Voluntarios v = (Voluntarios) pe;
                                   historicoE = v.historicoPE();
                              }else {
                                   Transportadoras t = (Transportadoras) pe;
                                   historicoE = t.historicoPE();
                              }

                              this.menuCliente.clearScreen();
                              if( historicoE.size() > 0 ) {
                                   System.out.print( "Histórico de entregas realizadas:\n\n" );
                                   for(Encomenda e : historicoE)
                                        System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                              }else
                                   System.out.print( "Ainda não realizou qualquer entrega." );
                         }

                         if( opcaoAUX == 2 ) {
                              this.menuCliente.clearScreen();
                              this.menuCliente.menuRegistoV();
                              String codigoRegisto = this.menuCliente.getCodigoRegisto();

                              this.menuCliente.clearScreen();
                              this.menuCliente.menuDataI();
                              LocalDateTime dataI = this.menuCliente.getDataI();

                              this.menuCliente.clearScreen();
                              this.menuCliente.menuDataF();
                              LocalDateTime dataF = this.menuCliente.getDataF();


                              List<Encomenda> historicoE = new ArrayList<>();
                              PlataformaEntrega pe = ta.getPlataformaEntrega( codigoRegisto );

                              try {
                                   String codigoPEAUX = codigoRegisto;
                                   boolean codigoPEb = ta.verificaT( codigoPEAUX );
                              } catch(exTransportadoraDoesNotExist e) {
                                   this.menuCliente.clearScreen();
                                   System.out.println( "Código inválido.\n" );
                                   System.out.print( "A redireciona-lo para o menu de informações adicionais...\n\n\n" );
                                   startInformacoesAdicionais();
                              }

                              if( pe.tipoPlataformaEntrega().equals( "Voluntario" ) ) {
                                   Voluntarios v = (Voluntarios) pe;
                                   historicoE = v.historicoPEdata( dataI, dataF );
                              }else {
                                   Transportadoras t = (Transportadoras) pe;
                                   historicoE = t.historicoPEdata( dataI, dataF );
                              }

                              this.menuCliente.clearScreen();
                              if( historicoE.size() > 0 ) {
                                   System.out.print( "Histórico de entregas realizadas:\n\n" );
                                   for(Encomenda e : historicoE)
                                        System.out.print( "Código da encomenda: " + e.getCodEncomenda() + "\nCódigo da loja: " + e.getCodLoja() + "\nCódigo do utilizador: " + e.getCodUtilizador() + "\nPeso total: " + e.getPeso() + "\nData: " + e.getData() + "\nProdutos: " + e.numeroProdutos() );
                              }else
                                   System.out.print( "Ainda não realizou qualquer entrega." );
                         }

                         if( opcaoAUX == 3 ) {
                              this.menuCliente.clearScreen();
                              System.out.print( "A redireciona-lo para o menu principal...\n\n\n" );
                              startCliente();
                         }

                         System.out.print( "\n\nInsira um caracter para voltar ao menu principal.\n" );
                         String i2 = this.menuCliente.leString();
                         this.menuCliente.clearScreen();

                         continue;
                    case 2: // Voltar atrás

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n\n\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu cliente...\n\n\n" );
               startCliente();
          }
     }

     // Método que lê a opção pretendida do menu de entrega podendo um cliente escolher a empresa, ver o estado da mesma, pedir a entrega da encomenda
     // depois de a ter efetuado e classificar
     public void startEntrega() {
          this.menuCliente.setOpcao( 0 );
          boolean flagB = true;

          if( this.menuCliente.getFlag() == false ) {
               System.out.print( "Ainda não inseriu nenhuma encomenda.\n" );
               System.out.print( "\nInsira um caracter para voltar atrás.\n" );
               String i = this.menuCliente.leString();
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
               startEncomenda();
          }

          while( this.menuCliente.getOpcao() != 3 ) {
               this.menuCliente.clearScreen();
               this.menuCliente.menuPedirEntrega();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // Transportadora
                         this.menuCliente.clearScreen();

                         Utilizadores u1 = ta.getUtilizador( codigoU );
                         Encomenda e1 = ta.getEncomenda( codigoE );

                         Transportadoras tC = new Transportadoras();
                         Transportadoras tB = new Transportadoras();
                         if( this.menuCliente.getFlagTM() ) {
                              tC = ta.recomendarClassificada( e1, true );
                              tB = ta.recomendarBarata( e1, true );
                         }else {
                              tC = ta.recomendarClassificada( e1, false );
                              tB = ta.recomendarBarata( e1, false );
                         }

                         if( tC == null || tB == null ) {
                              this.menuCliente.clearScreen();
                              System.out.print( "Não existem transportadoras disponíveis.\n" );
                              System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                              startEncomenda();
                         }

                         System.out.print( "Transportadoras disponíveis\n\n" );
                         List<Transportadoras> lT = ta.listPodeTransportarT( e1 );
                         for(Transportadoras t : lT) {
                              if( !this.menuCliente.getFlagTM() )
                                   System.out.print( "Código: " + t.getCodigo() + "\nNome: " + t.getNome() + "\nTransporte de medicamentos: " + t.getTransporteMedicamentos() + "\nClassificação: " + t.mediaClassificacao( t.getClassificacoes() ) + "\nPreço para a sua entrega: " + ta.custoTransporte( t.getCodigo(), e1 ) + "\n\n" );
                              else if( this.menuCliente.getFlagTM() && t.getTransporteMedicamentos() )
                                   System.out.print( "Código: " + t.getCodigo() + "\nNome: " + t.getNome() + "\nTransporte de medicamentos: " + t.getTransporteMedicamentos() + "\nClassificação: " + t.mediaClassificacao( t.getClassificacoes() ) + "\nPreço para a sua entrega: " + ta.custoTransporte( t.getCodigo(), e1 ) + "\n\n" );
                         }

                         System.out.print( "Recomendações para si\n\tMais barata: " + tB.getCodigo() + "\n\tMais bem classificada: " + tC.getCodigo() + "\n\n" );

                         this.menuCliente.menuEntregaT();

                         try {
                              String codigoPEAUX = this.menuCliente.getCodigoT();
                              boolean codigoPEb = ta.verificaT( codigoPEAUX );
                         } catch(exTransportadoraDoesNotExist e) {
                              this.menuCliente.clearScreen();
                              System.out.println( e.getMessage() );
                              System.out.print( "A redireciona-lo para o menu de entrega...\n\n\n" );
                              this.menuCliente.setFlag( true );
                              startEntrega();
                         }

                         this.codigoPE = this.menuCliente.getCodigoT();
                         ta.iniciarEntregaT( codigoPE );
                         ta.finalizarEntregaT( codigoPE, e1 );
                         u1.adicionarEncomenda( e1 );
                         ta.addEncomendaAceite( e1 );
                         ta.encomendaFinalizada( e1 );

                         int tempoT = ta.tempoTransporteLoja( codigoPE, e1 ) + ta.tempoTransporteUtilizador( codigoPE, e1 );
                         double custoTotal = ta.custoTransporte( codigoPE, e1 );

                         this.menuCliente.clearScreen();
                         System.out.print( "Tempo estimado de entrega\n\t" + tempoT + " minutos\n" );
                         System.out.print( "\nCusto total de entrega\n\t" + custoTotal + " €\n" );
                         System.out.print( "\nObrigado pela sua escolha!!\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu de encomenda.\n" );
                         String i = this.menuCliente.leString();

                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                         startEncomenda();

                         flagB = false;
                         break;
                    case 2: // Voluntário
                         this.menuCliente.clearScreen();

                         Utilizadores u2 = ta.getUtilizador( codigoU );
                         Encomenda e2 = ta.getEncomenda( codigoE );

                         try {
                              String codigoV = ta.escolhaVoluntario( e2 );
                              PlataformaEntrega pe = ta.getPlataformaEntrega( codigoV );
                              Voluntarios v = (Voluntarios) pe;

                              this.menuCliente.setFlag( false );
                              this.menuCliente.setFlagPE( true );
                              this.codigoPE = codigoV;
                              ta.iniciarEntregaV( codigoV );
                              ta.finalizarEntregaV( codigoV, e2 );
                              u2.adicionarEncomenda( e2 );
                              ta.addEncomendaAceite( e2 );
                              ta.encomendaFinalizada( e2 );

                              int tempoV = ta.tempoTransporteLoja( codigoV, e2 ) + ta.tempoTransporteUtilizador( codigoV, e2 );
                              System.out.print( "Foi escolhido um voluntário para entregar a sua encomenda.\n\tNome: " + v.getNome() + "\n\tTempo estimado para a sua entrega: " + tempoV );
                              System.out.print( "\n\nInsira um caracter para voltar ao menu de encomenda.\n" );
                              String i2 = this.menuCliente.leString();

                              this.menuCliente.clearScreen();
                              System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                              startEncomenda();
                         } catch(exPENotAvailable ex) {
                              System.out.println( ex.getMessage() );
                              System.out.print( "Insira um caracter para voltar ao menu de encomenda.\n" );
                              String i3 = this.menuCliente.leString();

                              this.menuCliente.clearScreen();
                              System.out.print( "A redireciona-lo para o menu de encomenda...\n\n\n" );
                              startEncomenda();
                         }

                         flagB = false;
                         break;
                    case 3: // Voltar atrás

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu de encomenda...\n" );
               startEntrega();
          }
     }

     // Método que lê a opção pretendida do menu das leaderboards podendo verificar quais os Top 10 clientes,
     // voluntários e transportadoras
     public void startClassificacoes() {
          boolean flagB = true;
          this.menuCliente.clearScreen();
          this.menuCliente.setOpcao( 0 );

          while( this.menuCliente.getOpcao() != 4 ) {
               this.menuCliente.menuClassificacoes();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // TOP 10 - Utilizadores
                         this.menuCliente.clearScreen();
                         System.out.print( "\tTOP 10 - Utilizadores\n\n" );

                         int i = 1;
                         List<Utilizadores> lU = ta.ordenadoTop10U();

                         if( lU.size() == 0 )
                              System.out.print( "Nenhum utilizador efetuou qualquer encomenda." );

                         for(Utilizadores u : lU) {
                              if( u.getEncomendasGuardadas().size() == 1 )
                                   System.out.print( i + ". " + u.getNome() + " - " + u.getEncomendasGuardadas().size() + " encomenda\n" );
                              else
                                   System.out.print( i + ". " + u.getNome() + " - " + u.getEncomendasGuardadas().size() + " encomendas\n" );

                              i++;
                         }

                         System.out.print( "\n\nInsira um caracter para voltar ao menu das leaderboards.\n" );
                         String i1 = this.menuCliente.leString();

                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu das leaderboards...\n\n\n" );

                         continue;
                    case 2: // TOP 10 - Voluntários
                         this.menuCliente.clearScreen();
                         System.out.print( "\tTOP 10 - Voluntários\n\n" );

                         i = 1;
                         List<PlataformaEntrega> lPEv = ta.ordenadoTop10V();

                         if( lPEv.size() == 0 )
                              System.out.print( "Nenhum voluntário efetuou qualquer entrega." );

                         for(PlataformaEntrega pe : lPEv) {
                              if( pe.getEncomendasGuardadas().size() == 1 )
                                   System.out.print( i + ". " + pe.getNome() + " - " + pe.getEncomendasGuardadas().size() + " entrega\n" );
                              else
                                   System.out.print( i + ". " + pe.getNome() + " - " + pe.getEncomendasGuardadas().size() + " entregas\n" );

                              i++;
                         }

                         System.out.print( "\n\nInsira um caracter para voltar ao menu das leaderboards.\n" );
                         i1 = this.menuCliente.leString();

                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu das leaderboards...\n\n\n" );

                         continue;
                    case 3: // TOP 10 - Transportadoras
                         this.menuCliente.clearScreen();
                         System.out.print( "\tTOP 10 - Transportadoras\n\n" );

                         i = 1;
                         List<PlataformaEntrega> lPEt = ta.ordenadoTop10T();

                         if( lPEt.size() == 0 )
                              System.out.print( "Nenhuma transportadora efetuou qualquer entrega." );

                         for(PlataformaEntrega pe : lPEt) {
                              if( pe.getEncomendasGuardadas().size() == 1 )
                                   System.out.print( i + ". " + pe.getNome() + " - " + pe.getEncomendasGuardadas().size() + " entrega\n" );
                              else
                                   System.out.print( i + ". " + pe.getNome() + " - " + pe.getEncomendasGuardadas().size() + " entregas\n" );

                              i++;
                         }

                         System.out.print( "\n\nInsira um caracter para voltar ao menu das leaderboards.\n" );
                         i1 = this.menuCliente.leString();

                         this.menuCliente.clearScreen();
                         System.out.print( "A redireciona-lo para o menu das leaderboards...\n\n\n" );

                         continue;
                    case 4: // Voltar atrás

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu de encomenda...\n" );
               startInicial();
          }
     }

     // Método que lê a opção pretendida do menu de definições de cliente podendo verificar/alterar
     public void startDefinicoesC() {
          boolean flagB = true;
          Utilizadores u = ta.getUtilizador( codigoU );
          this.menuCliente.setOpcao( 0 );

          while( this.menuCliente.getOpcao() != 6 ) {
               this.menuCliente.clearScreen();
               this.menuCliente.menuDefinicoes();

               switch(this.menuCliente.getOpcao()) {
                    case 1: // Localização atual
                         this.menuCliente.clearScreen();
                         String gpsA = ta.getUtilizador( codigoU ).getGPS().toString();
                         System.out.print( "Localização definida: " + gpsA + "\n" );

                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i1 = this.menuCliente.leString();

                         continue;
                    case 2: // Histórico de encomendas realizadas
                         this.menuCliente.clearScreen();

                         if( u.getEncomendasGuardadas().size() == 0 )
                              System.out.print( "Ainda não realizou nenhuma encomenda.\n" );
                         else
                              System.out.print( "\tHistórico de encomendas:\n\n" + u.historicoEncomenda().toString() );

                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i2 = this.menuCliente.leString();

                         continue;
                    case 3: // Alterar email
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuAlterarEmail();

                         String emailN = this.menuCliente.getEmail();
                         u.setEmail( emailN );

                         this.menuCliente.clearScreen();
                         System.out.print( "Email modificado, para " + emailN + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i3 = this.menuCliente.leString();

                         continue;
                    case 4: // Alterar password
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuAlterarPW();

                         String pwN = this.menuCliente.getPassword();
                         u.setPW( pwN );

                         this.menuCliente.clearScreen();
                         System.out.print( "Password modificada com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i4 = this.menuCliente.leString();

                         continue;
                    case 5: // Alterar localização
                         this.menuCliente.clearScreen();
                         this.menuCliente.menuAlterarLocalizacao();

                         Ponto gpsN = this.menuCliente.getGPS();
                         u.setGPS( gpsN );

                         this.menuCliente.clearScreen();
                         System.out.print( "Localização modificada, para " + gpsN.toString() + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i5 = this.menuCliente.leString();

                         continue;
                    case 6: // Voltar atrás

                         break;
                    default:
                         this.menuCliente.clearScreen();
                         System.out.print( "A opcão que escolheu é inválida.\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuCliente.clearScreen();
               System.out.print( "A redireciona-lo para o menu principal...\n" );
               startCliente();
          }
     }

     // Método que lê a opção pretendida do menu de definições de transportadora podendo verificar/alterar
     public void startDefinicoesT() {
          boolean flagB = true;
          PlataformaEntrega pe = ta.getPlataformaEntrega( codigoU );
          Transportadoras t = (Transportadoras) pe;
          this.menuPlataformas.setOpcao( 0 );

          while( this.menuPlataformas.getOpcao() != 6 ) {
               this.menuPlataformas.clearScreen();
               this.menuPlataformas.menuTransportadorasDefinicoes();

               switch(this.menuPlataformas.getOpcao()) {
                    case 1: // Alterar password
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarPW();

                         String pwN = this.menuPlataformas.getPW();
                         t.setPW( pwN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Password modificada com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i1 = this.menuPlataformas.leString();

                         continue;
                    case 2: // Alterar raio
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarRaio();

                         double raioN = this.menuPlataformas.getRaio();
                         t.setRaio( raioN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Raio modificado, para " + raioN + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i2 = this.menuCliente.leString();

                         continue;
                    case 3: // Alterar capacidade da empresa
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarCapacidade();

                         int capacidadeN = this.menuPlataformas.getCapacidade();
                         t.setCapacidade( capacidadeN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Capacidade modificada, para " + capacidadeN + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i3 = this.menuCliente.leString();

                         continue;
                    case 4: // Alterar preço por KM
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarPrecoPorKM();

                         double precoPorKmN = this.menuPlataformas.getPrecoPorKM();
                         t.setPrecoPorKM( precoPorKmN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Preço por KM modificado, para " + precoPorKmN + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i4 = this.menuCliente.leString();

                         continue;
                    case 5: // Validar transporte de medicamentos
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuValidarTM();

                         boolean tmN = this.menuPlataformas.getTM();
                         t.setTransporteMedicamentos( tmN );

                         this.menuPlataformas.clearScreen();
                         if( tmN )
                              System.out.print( "Transporte de medicamentos ativado.\n" );
                         else
                              System.out.print( "Transporte de medicamentos desativado.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i5 = this.menuPlataformas.leString();

                         continue;
                    case 6: // Voltar atrás

                         break;
                    default:
                         this.menuPlataformas.clearScreen();
                         System.out.print( "A opção que escolheu é inválida.\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuPlataformas.clearScreen();
               System.out.print( "A redireciona-lo para o menu principal...\n" );
               startTransportadora();
          }
     }

     // Método que lê a opção pretendida do menu de definições de transportadora podendo verificar/alterar
     public void startDefinicoesV() {
          boolean flagB = true;
          PlataformaEntrega pe = ta.getPlataformaEntrega( codigoU );
          Voluntarios v = (Voluntarios) pe;
          this.menuPlataformas.setOpcao( 0 );

          while( this.menuPlataformas.getOpcao() != 4 ) {
               this.menuPlataformas.clearScreen();
               this.menuPlataformas.menuVoluntariosDefinicoes();

               switch(this.menuPlataformas.getOpcao()) {
                    case 1: // Alterar password
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarPW();

                         String pwN = this.menuPlataformas.getPW();
                         v.setPW( pwN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Password modificada com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i1 = this.menuPlataformas.leString();

                         continue;
                    case 2: // Alterar raio
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuAlterarRaio();

                         double raioN = this.menuPlataformas.getRaio();
                         v.setRaio( raioN );

                         this.menuPlataformas.clearScreen();
                         System.out.print( "Raio modificado, para " + raioN + ", com sucesso.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i2 = this.menuCliente.leString();

                         continue;
                    case 3: // Validar o transporte de medicamentos
                         this.menuPlataformas.clearScreen();
                         this.menuPlataformas.menuValidarTM();

                         boolean tmN = this.menuPlataformas.getTM();
                         v.setTransporteMedicamentos( tmN );

                         this.menuPlataformas.clearScreen();
                         if( tmN )
                              System.out.print( "Transporte de medicamentos ativado.\n" );
                         else
                              System.out.print( "Transporte de medicamentos desativado.\n" );
                         System.out.print( "\nInsira um caracter para voltar ao menu das definições.\n" );
                         String i3 = this.menuCliente.leString();

                         continue;
                    case 4: // Voltar atrás

                         break;
                    default:
                         this.menuPlataformas.clearScreen();
                         System.out.print( "A opção que escolheu é inválida.\n" );

                         continue;
               }
          }

          if( flagB ) {
               this.menuPlataformas.clearScreen();
               System.out.print( "A redireciona-lo para o menu principal...\n" );
               startVoluntarios();
          }
     }
}