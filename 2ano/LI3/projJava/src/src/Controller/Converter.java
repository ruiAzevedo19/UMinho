package Controller;

import Model.Answer.*;
import Model.Interface.IProduct;
import Model.Interface.ISGV;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Converter implements IConverter {
    /* --- Variaveis de instancia ----------------------------------------------------------------------------------- */
    private int nrBranches;
    /* -------------------------------------------------------------------------------------------------------------- */

    public Converter(int nrBranches){
        this.nrBranches = nrBranches;
    }

    /* --- Funcionality --------------------------------------------------------------------------------------------- */

    public List<List<Object>> q1Converter(List<IProduct> data){
        List<List<Object>> r = new ArrayList<>();
        List<Object> d = data.stream().map(IProduct::toString).collect(Collectors.toList());
        r.add(d);
        return r;
    }

    public Object[][] q2Converter(SalesClients sc){
        Object[][] r = new Object[nrBranches + 1][2];
        r[0][0] = sc.getGlobalSales();
        r[0][1] = sc.getGlobalClients();

        for(int i = 1; i <= nrBranches; i++){
            r[i][0] = sc.getSalesBranch(i-1);
            r[i][1] = sc.getClientsBranch(i-1);
        }
        return r;
    }

    public Object[][] q3Converter(PurchProdsSpent pps){
        DecimalFormat df = new DecimalFormat("#.##");
        Object[][] r = new Object[3][12];
        r[0] = Arrays.stream(pps.getPurchased()).boxed().toArray();
        r[1] = Arrays.stream(pps.getProducts()).boxed().toArray();
        r[2] = Arrays.stream(pps.getSpent()).boxed().map(df::format).toArray();

        return r;
    }

    public Object[][] q4Converter(PurchClientsBillings pcb){
        DecimalFormat df = new DecimalFormat("#.##");
        Object[][] r = new Object[3][12];
        r[0] = Arrays.stream(pcb.getPurchased()).boxed().toArray();
        r[1] = Arrays.stream(pcb.getClientes()).boxed().toArray();
        r[2] = Arrays.stream(pcb.getBillings()).boxed().map(df::format).toArray();

        return r;
    }

    public List<List<Object>> q5Converter(Set<QuantProd> mpp){
        List<List<Object>> r = new ArrayList<>();
        List<Object> prods = mpp.stream().map(QuantProd::getCodProd).collect(Collectors.toList());
        List<Object> quant = mpp.stream().map(QuantProd::getPurchased).collect(Collectors.toList());
        r.add(prods);
        r.add(quant);
        return r;
    }

    public List<List<Object>> q6Converter(Set<QuantProd> mbp){
        List<List<Object>> r = new ArrayList<>();

        r.add(new ArrayList<>(mbp.stream().map(QuantProd::getCodProd).collect(Collectors.toList())));
        r.add(new ArrayList<>(mbp.stream().map(QuantProd::getClients).collect(Collectors.toList())));
        r.add(new ArrayList<>(mbp.stream().map(QuantProd::getPurchased).collect(Collectors.toList())));

        return r;
    }

    public Object[][] q7Converter(List<List<AbstractMap.SimpleEntry<String,Double>>> result){
        DecimalFormat df = new DecimalFormat("#.##");
        Object[][] r = new Object[nrBranches][3];
        for(int i = 0; i < nrBranches; i++){
            for(int j = 0; j < 3; j++){
                r[i][j] = result.get(i).get(j).getKey() + " ( " + df.format(result.get(i).get(j).getValue()) + " )";
            }
        }
        return r;
    }

    public List<List<Object>> q8Converter(List<AbstractMap.SimpleEntry<String,Integer>> c){
        List<List<Object>> r = new ArrayList<>();
        r.add(new ArrayList<>());
        r.add(new ArrayList<>());

        for(AbstractMap.SimpleEntry<String,Integer> e : c){
            r.get(0).add(e.getKey());
            r.get(1).add(e.getValue());
        }
        return r;
    }

    public List<List<Object>> q9Converter(List<ClientQuantBillings> cqb){
        DecimalFormat df = new DecimalFormat("#.##");
        List<List<Object>> r = new ArrayList<>();
        List<Object> prods = cqb.stream().map(ClientQuantBillings::getCodClient).collect(Collectors.toList());
        List<Object> bill = cqb.stream().map(f -> df.format(f.getBillings())).collect(Collectors.toList());
        r.add(prods);
        r.add(bill);
        return r;
    }

    public List<AbstractMap.SimpleEntry<Object,Object[][]>> q10Converter(Map<String,List<List<Double>>> result){
        List<AbstractMap.SimpleEntry<Object,Object[][]>> r = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        Object[][] data;

        for(Map.Entry<String,List<List<Double>>> e : result.entrySet()){
            data = new Object[nrBranches][12];
            for(int i = 0; i < nrBranches; i++){
                data[i] = e.getValue().get(i).stream().map(df::format).toArray();
            }
            r.add(new AbstractMap.SimpleEntry<>(e.getKey(),data));
        }
        return r;
    }
}
