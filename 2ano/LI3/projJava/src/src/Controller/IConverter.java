package Controller;

import Model.Answer.*;
import Model.Interface.IProduct;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public interface IConverter {

    List<List<Object>> q1Converter(List<IProduct> data);

    Object[][] q2Converter(SalesClients sc);

    Object[][] q3Converter(PurchProdsSpent pps);

    Object[][] q4Converter(PurchClientsBillings pcb);

    List<List<Object>> q5Converter(Set<QuantProd> mpp);

    List<List<Object>> q6Converter(Set<QuantProd> mbp);

    Object[][] q7Converter(List<List<AbstractMap.SimpleEntry<String,Double>>> result);

    List<List<Object>> q8Converter(List<AbstractMap.SimpleEntry<String,Integer>> c);

    List<List<Object>> q9Converter(List<ClientQuantBillings> cqb);

    List<AbstractMap.SimpleEntry<Object,Object[][]>> q10Converter(Map<String,List<List<Double>>> result);

}
