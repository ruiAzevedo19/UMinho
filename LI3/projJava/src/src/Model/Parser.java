package Model;

import Model.Interface.IParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Parser implements IParser {

    public void readClients(SGV sgv, String pathToFile) throws IOException {
        String line;
        int valid = 0, invalid = 0;
        BufferedReader br = new BufferedReader(new FileReader(pathToFile));
        while ((line = br.readLine()) != null)
            if (sgv.isClientValid(line)) {
                sgv.addClient(line);
                valid++;
            }
            else
                invalid++;
        sgv.setVClients(valid);
        sgv.setIClients(invalid);
    }

    public void readProducts(SGV sgv, String pathToFile) throws IOException{
        String line = "";
        int valid = 0, invalid = 0;
        BufferedReader br = new BufferedReader(new FileReader(pathToFile));
        while ((line = br.readLine()) != null)
            if (sgv.isProductValid(line)) {
                sgv.addProduct(line);
                sgv.addProductToBilling(line);
                valid++;
            }
            else
                invalid++;

        sgv.setVProducts(valid);
        sgv.setIProducts(invalid);
    }

    public void readSales(SGV sgv, String pathToFile) throws IOException {
        String line;
        String[] s;
        Sale sale;
        int valid = 0, invalid = 0, price = 0;
        BufferedReader br = new BufferedReader(new FileReader(pathToFile));
        while ((line = br.readLine()) != null) {
            s = line.split(" ");
            if (validate(sgv, s)) {
                sale = new Sale(s[0], s[4], Float.parseFloat(s[1]), Integer.parseInt(s[2]), s[3], Integer.parseInt(s[5]), Integer.parseInt(s[6]));
                if( sale.getPrice() == 0.0 )
                    price++;
                sgv.addSaleToBilling(sale);
                sgv.addSaleToBranch(sale);
                valid++;
            }
            else
                invalid++;
        }
        sgv.setPurchasedNull(price);
        sgv.setVSales(valid);
        sgv.setISales(invalid);
        sgv.setLastSaleFile(pathToFile);
    }

    public boolean validate(SGV sgv, String[] sale){
       if( sale.length != 7 )
           return false;
       if(!sgv.containsProduct(sale[0]))
           return false;
       if(!sgv.containsClient(sale[4]))
           return false;
       if(Double.parseDouble(sale[1]) < 0.0 || Double.parseDouble(sale[1]) > 999.99)
           return false;
       if(Integer.parseInt(sale[2]) <= 0 || Integer.parseInt(sale[2]) > 201)
           return false;
       if(sale[3].length() != 1 || (!sale[3].equals("P") && !sale[3].equals("N")))
           return false;
       if(Integer.parseInt(sale[5]) <= 0 && Integer.parseInt(sale[5]) > 13)
           return false;
       if(Integer.parseInt(sale[6]) <= 0 || Integer.parseInt(sale[6]) >= 4)
           return false;
       return true;
    }
}
