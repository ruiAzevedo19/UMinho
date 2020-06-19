package ParserTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NIOParser extends Parser{
    /**
     * Método construtor
     */
    public NIOParser(String pathToFile, Set<String> clients, Set<String> products){
        super(pathToFile, clients, products);
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    Function<String,String[]> tokenizer = str -> str.split(" ");

   // Predicate<String[]> validate = null;


    @Override
    public void readFile() {
        Path p = Paths.get(super.getPathToFile());
        try{
            List<String> sales = Files.readAllLines(p);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFileParsing() {
        Path p = Paths.get(super.getPathToFile());
        try{
            ArrayList sales = (ArrayList)Files. readAllLines(p)
                                              . stream()
                                              . map(tokenizer)
                                              . collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFileParsingValidation() {
        Path p = Paths.get(super.getPathToFile());
        try{
            ArrayList sales = (ArrayList)Files. readAllLines(p)
                                              . stream()
                                              . map(tokenizer)
                                              . filter(this::validate)
                                              . collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
