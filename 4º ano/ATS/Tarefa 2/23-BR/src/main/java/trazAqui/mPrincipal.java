package trazAqui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;
import java.time.LocalDateTime;

public class mPrincipal implements Serializable {
  
  /* STARTING functions */

  private int opcao;
  private int ano;
  private int mes;
  private int dia;
  private int hora;
  private int minuto;
  private LocalDateTime dataI;
  private LocalDateTime dataF;

  public mPrincipal() {
    this.opcao = 0;
    this.ano = 0;
    this.mes = 0;
    this.dia = 0;
    this.hora = 0;
    this.minuto = 0;
    this.dataI = LocalDateTime.now();
    this.dataF = LocalDateTime.now();
  }

  public mPrincipal(int opcao, int ano, int mes, int dia, int hora, int minuto, LocalDateTime dataI, LocalDateTime dataF) {
    this.opcao = opcao;
    this.ano = ano;
    this.mes = mes;
    this.dia = dia;
    this.hora = hora;
    this.minuto = minuto;
    this.dataI = dataI;
    this.dataF = dataF;
  }

  public mPrincipal(mPrincipal m) {
    this.opcao = m.getOpcao();
    this.ano = m.getAno();
    this.mes = m.getMes();
    this.dia = m.getDia();
    this.hora = m.getHora();
    this.minuto = m.getMinuto();
    this.dataI = m.getDataI();
    this.dataF = m.getDataF();
  }


  /* GET functions */

  public int getOpcao() {
    return this.opcao;
  }

  public int getAno() {
    return this.ano;
  }

  public int getMes() {
    return this.mes;
  }

  public int getDia() {
    return this.dia;
  }

  public int getHora() {
    return this.hora;
  }

  public int getMinuto() {
    return this.minuto;
  }

  public LocalDateTime getDataI() {
    return this.dataI;
  }

  public LocalDateTime getDataF() {
    return this.dataF;
  }
  
  /* SET functions */

  public void setOpcao(int newOpcao) {
    this.opcao = newOpcao;
  }

  public void setAno(int newAno) {
    this.ano = newAno;
  }

  public void setMes(int newMes) {
    this.mes = newMes;
  }

  public void setDia(int newDia) {
    this.dia = newDia;
  }

  public void setHora(int newHora) {
    this.hora = newHora;
  }

  public void setMinuto(int newMinuto) {
    this.minuto = newMinuto;
  }

  public void setDataI(LocalDateTime newDataI) {
    this.dataI = newDataI;
  }

  public void setDataF(LocalDateTime newDataF) {
    this.dataF = newDataF;
  }

  /* ASKED functions */

  // Método que lê uma opção
  public int leOpcao() {
    Scanner in = new Scanner(System.in);
    int opcao = -1;

    try {
      opcao = in.nextInt();
    } catch (InputMismatchException e) {
      return Integer.MAX_VALUE;
    }

    return opcao;
  }

  // Método que lê uma string
  public String leString() {
    Scanner in = new Scanner(System.in);
    String result = new String();

    try {
      result = in.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("Não foi detetado nenhum input compatível com o pedido.");
    }

    return result;
  }

  // Método que lê um float
  public float leFloat() {
    Scanner in = new Scanner(System.in);
    float result = 0;

    try {
      result = in.nextFloat();
    } catch (InputMismatchException e) {
      return Float.POSITIVE_INFINITY;
    }

    return result;
  }

  // Método que lê um double
  public double leDouble() {
    Scanner in = new Scanner(System.in);
    double result = 0;

    try {
      result = in.nextDouble();
    } catch (InputMismatchException e) {
      return Double.POSITIVE_INFINITY;
    }

    return result;
  }

  // Método que lê um inteiro
  public int leInt() {
    Scanner in = new Scanner(System.in);
    int result = 0;

    try {
      result = in.nextInt();
    } catch (InputMismatchException e) {
      return Integer.MAX_VALUE;
    }

    return result;
  }

  // Método que lê Yes ou No
  public String leYesOrNo() {
    String result = leString();

    if (result.equals("Yes"))
      return "true";
    else
      if (result.equals("No"))
        return "false";
   
    return "null";
  }

  // Método que verifica se uma data é válida
  public boolean verificaData() {
    if (this.mes == 1 || this.mes == 3 || this.mes == 5 || this.mes == 7 || this.mes == 8 || this.mes == 10 || this.mes == 12)
      if (this.dia > 0 && this.dia < 32 && verificaHora())
        return true;
  
    if (this.mes == 2 && this.dia > 0 && this.dia < 30 && verificaHora())
      return true;
    
    if (this.mes == 4 || this.mes == 6 || this.mes == 9 || this.mes == 11)
      if (this.dia > 0 && this.dia < 31 && verificaHora())
        return true;

    return false;
  }
  
  // Método que verifica se uma hora é válida
  public boolean verificaHora() {
    if (this.hora >= 0 && this.hora <= 23)
      if (this.minuto >= 0 && this.minuto <= 59)
        return true;

    return false;
  }

  // Método que limpa o ecrã  (funciona no VisualStudioCode)
  /*
  public void clearScreen() {  
    System.out.print("\u001B[2J");
    System.out.flush();
  }
  */

  // Método que limpa o ecrã  (funciona no BlueJ)
  public void clearScreen() {
    System.out.print('\u000C');
    System.out.flush();
  }
}