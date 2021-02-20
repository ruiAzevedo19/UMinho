package ParserTest;

import Utilities.Time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RunTest {
    private static int runs = 10;
    private static String pathToFile = "../../../../gestVendasDB/Vendas_5M.txt";
    //private static String pathToFile = "DB/Vendas_1M.txt";

    public static Set<String> read(String pathToFile){
        Set<String> clog = new HashSet<>();
        String line = "";
        int lines = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            while( (line = br.readLine()) != null ) {
                lines++;
                clog.add(line);
            }
            System.out.println(">> Read " + lines + " lines");
        }catch (IOException e){
            e.printStackTrace();
        }
        return clog;
    }

    public static void main(String[] args) {
        Set<String> clients  = read("DB/Clientes.txt");
        Set<String> products = read("DB/Produtos.txt");
        List<Double> readBRP = new ArrayList<>();
        List<Double> readNIO = new ArrayList<>();

        double avgBRP = 0;
        double avgNIO = 0;

        /* read without parsing and validation */

        for(int i = 0; i < runs; i++) {
            /* read with buffer reader */
            Time.start();
            BufferReaderParser bfp = new BufferReaderParser(pathToFile, clients, products);
            bfp.readFile();
            readBRP.add(Time.stop());
            /* read with nio */
            Time.start();
            NIOParser nio = new NIOParser(pathToFile, clients, products);
            nio.readFile();
            readNIO.add(Time.stop());
        }
        avgBRP = readBRP.stream().mapToDouble(d->d).sum() / runs;
        avgNIO = readNIO.stream().mapToDouble(d->d).sum() / runs;
        System.out.println("Reading without parsing and without validation");
        System.out.println("Average with BufferReader = " + avgBRP);
        System.out.println("Average with NIO = " + avgNIO);

        readBRP = new ArrayList<>();
        readNIO = new ArrayList<>();

        /* read with parsing and without validation */
        for(int i = 0; i < runs; i++){
            /* read with buffer reader */
            Time.start();
            BufferReaderParser bfp = new BufferReaderParser(pathToFile, clients, products);
            bfp.readFileParsing();
            readBRP.add(Time.stop());
            /* read with nio */
            Time.start();
            NIOParser nio = new NIOParser(pathToFile, clients, products);
            nio.readFileParsing();
            readNIO.add(Time.stop());
        }
        avgBRP = readBRP.stream().mapToDouble(d->d).sum() / runs;
        avgNIO = readNIO.stream().mapToDouble(d->d).sum() / runs;
        System.out.println("Reading with parsing and without validation");
        System.out.println("Average with BufferReader = " + avgBRP);
        System.out.println("Average with NIO = " + avgNIO);

        readBRP = new ArrayList<>();
        readNIO = new ArrayList<>();

        /* read with parsing and validation */
        for(int i = 0; i < runs; i++){
            /* read with buffer reader */
            Time.start();
            BufferReaderParser bfp = new BufferReaderParser(pathToFile, clients, products);
            bfp.readFileParsingValidation();
            readBRP.add(Time.stop());
            /* read with nio */
            Time.start();
            NIOParser nio = new NIOParser(pathToFile, clients, products);
            nio.readFileParsingValidation();
            readNIO.add(Time.stop());
        }
        avgBRP = readBRP.stream().mapToDouble(d->d).sum() / runs;
        avgNIO = readNIO.stream().mapToDouble(d->d).sum() / runs;
        System.out.println("Reading with parsing and with validation");
        System.out.println("Average with BufferReader = " + avgBRP);
        System.out.println("Average with NIO = " + avgNIO);
    }
}
