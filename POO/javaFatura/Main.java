
/**
 * Escreva a descrição da classe Main aqui.
 * 
 * @author Rui Costa
 * @version 00001
 */
 
import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.lang.System;
import java.util.Scanner;
import java.time.LocalDate;

public class Main
{   
    public static void main(String[] args) throws ContribuinteNaoExistenteException,FaturaInexistenteException{
        GestaoFaturas gf = new GestaoFaturas();
        
        try{
            gf = gf.carregaEstado("JavaFatura.txt");
        }catch(FileNotFoundException e){
            gf = new GestaoFaturas();
            gf.registos();
            gf.addCAE();
            gf.addIncentivos();
        }catch(IOException e){
            out.println("Oops, algo correu mal!");
        }catch(ClassNotFoundException e){
            out.println("Oops, algo correu mal!");
        }
        
        MenuInicial j = new MenuInicial(gf);
        j.menuInicial();
        
        try{
            gf.gravaEstado("JavaFatura.txt");
        }catch(FileNotFoundException e){
            out.println("Oops, algo correu mal!");
        }catch(IOException e){
            out.println("Oops, algo correu mal!");
        }
    }
}

