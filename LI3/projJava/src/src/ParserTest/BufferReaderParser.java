package ParserTest;

import Model.Sale;

import java.io.*;
import java.util.Set;

public class BufferReaderParser extends Parser{

    /**
     * Método construtor
     */
    public BufferReaderParser(String pathToFile, Set<String> clients, Set<String> products) {
        super(pathToFile,clients,products);
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    public void readFile(){
        String line = "";
        int lines = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(super.getPathToFile()));
            while( (line = br.readLine()) != null )
                lines++;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFileParsing() {
        String line = "";
        String[] s;
        Sale sale = null;
        int lines = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(super.getPathToFile()));
            while( (line = br.readLine()) != null ) {
                s = line.split(" ");
                sale = new Sale(s[0],s[4],Float.parseFloat(s[1]),Integer.parseInt(s[2]),s[3],Integer.parseInt(s[5]),Integer.parseInt(s[6]));
                super.addSale(sale);
                lines++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFileParsingValidation() {
        String line = "";
        String[] s;
        Sale sale = null;
        int lines = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(super.getPathToFile()));
            while( (line = br.readLine()) != null ) {
                s = line.split(" ");
                if( super.validate(s) ) {
                    sale = new Sale(s[0], s[4], Float.parseFloat(s[1]), Integer.parseInt(s[2]), s[3], Integer.parseInt(s[5]), Integer.parseInt(s[6]));
                    lines++;
                    super.addSale(sale);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
