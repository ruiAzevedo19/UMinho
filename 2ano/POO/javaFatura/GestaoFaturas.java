/**
 * Write a description of class GestaoFaturas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Object;
import java.util.Collections;
import java.util.*;
import java.io.Serializable;

public class GestaoFaturas implements Serializable 
{
    /**
     * Variáveis de instância
     */
    private Map<String, Contribuinte> gestao_contribuintes; // key := NIF do contribuinte
    private Map<String, Fatura> gestao_faturas;             // key := número da fatura
    private Map<String, CAE> atividadeEconomica;            // key := código da atividade
    private Map<String, Double> incentivoFiscal;             // key := Distrito
    
    /*============================================================================================================
     - Métodos construtores 
     =============================================================================================================*/
    
    /**
     * Método construtor não parametrizado
     *
     * @return Uma class GestaoFaturas não inicializada
     */
    public GestaoFaturas(){
        this.gestao_contribuintes = new HashMap<>();
        this.gestao_faturas       = new HashMap<>();
        this.atividadeEconomica   = new HashMap<>();
        this.incentivoFiscal       = new HashMap<>();
    }

    /**
     * Método construtor parametrizado
     *
     * @param Map<String,Contribuinte> gc
     * @param Map<String,Fatura> gf
     */
    public GestaoFaturas(Map<String,Contribuinte> gc, Map<String,Fatura> gf, Map<String,CAE> ae, Map<String,Double> i){
        this.gestao_contribuintes = gc.values().stream().collect(Collectors.toMap(c -> c.getNIF(), c -> c.clone()));
        this.gestao_faturas       = gf.values().stream().collect(Collectors.toMap(f -> f.getCodFatura(), f -> f.clone()));
        this.atividadeEconomica   = ae.values().stream().collect(Collectors.toMap(a -> a.getCAE(), a -> a.clone()));
        this.incentivoFiscal       = i.entrySet().stream().collect(Collectors.toMap(a -> a.getKey(), a -> a.getValue()));
    }

    /**
     * Método construtor por cópia
     *
     * @param GestaoFaturas gf
     */
    public GestaoFaturas(GestaoFaturas gf){
        this.gestao_contribuintes = gf.getGestaoContribuintes();
        this.gestao_faturas       = gf.getGestaoFaturas();
        this.atividadeEconomica   = gf.getAtividadeEconomica();
        this.incentivoFiscal       = gf.getIncentivoFiscal();
    }
    
    /*=============================================================================================================
     - Get´s 
     =============================================================================================================*/
     
    /**
     * Método que devolve o map da gestao de contribuintes
     */
    public Map<String,Contribuinte> getGestaoContribuintes(){
        return this.gestao_contribuintes.values().stream().collect(Collectors.toMap(c -> c.getNIF(), c -> c.clone()));
    }

    /**
     * Método que devolve o map da gestao de faturas
     */
    public Map<String,Fatura> getGestaoFaturas(){
        return this.gestao_faturas.values().stream().collect(Collectors.toMap(f -> f.getCodFatura(), f -> f.clone()));
    }

    /**
     * Método que devolve o map das atividades economicas
     */
    public Map<String,CAE> getAtividadeEconomica(){
        return this.atividadeEconomica.values().stream().collect(Collectors.toMap(a -> a.getCAE(), a -> a.clone()));
    }
    
    /**
     * Método que devolve o map dos icentivos fiscais
     */
    public Map<String,Double> getIncentivoFiscal(){
        return this.incentivoFiscal.entrySet().stream().collect(Collectors.toMap(a -> a.getKey(), a -> a.getValue()));
    }
    
    /*=============================================================================================================
     - Set's 
     =============================================================================================================*/

    /**
     * Método que define o map da gestao de faturas
     *
     * @param Map<String,Contribuinte> gc
     */
    public void setGestaoContribuintes(Map<String,Contribuinte> gc){
        this.gestao_contribuintes = gc.values().stream().collect(Collectors.toMap(c -> c.getNIF(), c -> c.clone()));
    }

    /**
     * Método que define o map da gestao de faturas
     *
     * @param Map<String,Contribuinte> gc
     */
    public void setGestaoFaturas(Map<String,Fatura> gf){
        this.gestao_faturas = gf.values().stream().collect(Collectors.toMap(f -> f.getCodFatura(), f -> f.clone()));
    }

    /**
     * Método que define o map de actividades economicas
     */
    
    public void setAtividadeEconomica(Map<String,CAE> ae){
        this.atividadeEconomica = ae.values().stream().collect(Collectors.toMap(a -> a.getCAE(), a -> a.clone()));
    }
    
    /**
     * Método que define o map dos incentivos fisca
     */
    public void setIncentivoFiscal(Map<String,Double> i){
        this.incentivoFiscal = i.entrySet().stream().collect(Collectors.toMap(c -> c.getKey() , c -> c.getValue()));
    }
    
    /*=============================================================================================================
     - Login 
     =============================================================================================================*/
    
    /**
     * Método que devolve um contribuinte testando o login
     * 
     * @param nif NIF do utilizador
     * @param password password do utilizador
     */
    public Contribuinte login(String nif, String password){
        Contribuinte c = this.gestao_contribuintes.get(nif);
        return (c == null || !c.login(password)) ? null : c;
    }

    /*=============================================================================================================
     - Registos 
     =============================================================================================================*/

    /**
     * Método que regista um contribuinte individual
     *
     * @param nome nome do utilizador
     * @param nif nif do utilizador
     * @param email email do utilizador
     * @paaram morada morada do utilizador
     * @param password password do utilizador
     * @param numero_agregado número do agregado familizar do utilizador
     * @param coeficiente_fiscal  coeficiente fiscal do utilizador
     */
    public void registoIndividual(String nome, String nif, String email, String morada, String password, int numero_agregado,
                                  double coeficiente_fiscal){
        this.gestao_contribuintes.put(nif, new Individual(nome, nif, email, morada, password,numero_agregado, coeficiente_fiscal));
    }

    /**
     * Método que regista um contribuinte colectivo
     *
     * @param nome nome do utilizador
     * @param nif nif do utilizador
     * @param email email do utilizador
     * @paaram morada morada do utilizador
     * @param password password do utilizador
     * @param facotr_fiscal factor fiscal do utilizador
     */
    public void registoColectivo(String nome, String nif, String email, String morada, String password, double fator_fiscal){
        Colectivo cc = new Colectivo(nome, nif, email, morada, password, fator_fiscal);
        this.gestao_contribuintes.put(nif, cc.clone());
    }

    /**
     * Método que regista um contribuinte
     * 
     * @return validação do pedido de registo
     */
    public boolean registo(Contribuinte c){
        if (this.gestao_contribuintes.containsKey(c.getNIF())) return false;
        this.gestao_contribuintes.put(c.getNIF(), c.clone());
        return true;
    }

    /**
     * Método que devolve o Contribuinte associado a um número de identificação fiscal
     *
     * @param nif nif do utilizador
     * @throw ContribuinteNaoExistenteException
     * @return contribuinte referente ao NIF
     */
    public Contribuinte getContribuinte(String nif) throws ContribuinteNaoExistenteException {
        Contribuinte c = this.gestao_contribuintes.get(nif);
        
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);
        
        return c;
    }

    /*=============================================================================================================
     - Contribuintes 
     =============================================================================================================*/

    /**
     * Método que verifica o tipo de contribuinte
     *
     * @param nif nif do utilizador
     * @throw ContribuinteNaoExistenteException
     * @return Devolve true se o utilizador for um Contribuinte Individual, false caso contrário
     */
    public boolean verificaContribuinte(String nif) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);

        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        if( c instanceof Individual )
            return true;
        else
            return false;
    }

    /**
     * Método que permite adicionar um NIF à lista dos elementos do agregado familiar
     * 
     * @param nif nif do utilizador
     * @param nif1 nif do elemento do agregado familiar
     */
    public void addNifAgregado(String nif, String nif1){
        Contribuinte c = this.gestao_contribuintes.get(nif);
        Individual i   = (Individual)c;
        i.addNifAgr(nif1);
        i.setNumeroAgregado(i.getNumeroAgregado() + 1);
    }
    
    /*=============================================================================================================
     - Incentivos Fiscais
     =============================================================================================================*/
    
    /**
     * Método que adiciona incentivos fiscais
     */
    public void addIncentivos(){
        this.incentivoFiscal.put("Vila Real", 0.12);
        this.incentivoFiscal.put("Bragança", 0.10);
        this.incentivoFiscal.put("Viseu", 0.09);
        this.incentivoFiscal.put("Castelo Branco", 0.15);
        this.incentivoFiscal.put("Guarda", 0.05);
        this.incentivoFiscal.put("Santarém", 0.12);
        this.incentivoFiscal.put("Portalegre", 0.13);
        this.incentivoFiscal.put("Évora", 0.05);
        this.incentivoFiscal.put("Beja", 0.07);
    }
    
    public String incentivos(){
        StringBuilder sb = new StringBuilder();
        
        for(Map.Entry<String,Double> par: this.incentivoFiscal.entrySet()){
            sb.append("Distrito: " + par.getKey() + "\n" + "Incentivo: " + par.getValue() + "\n\n");
        }
        return sb.toString();
    }
    
    /**
     * Método que devolve um incentivo
     */
    public double takeIncentivo(String localidade){
        double incentivo = 0;
        
        if( this.incentivoFiscal.containsKey(localidade))
            incentivo = this.incentivoFiscal.get(localidade);
        
        return incentivo;   
    }
    
    /*=============================================================================================================
     - Códigos de actividade económica
     =============================================================================================================*/

    /**
     * Método que adiciona os códigos CAE ao map de códigos CAE
     */
    public void addCAE(){
        this.atividadeEconomica.put("1", new Restauracao());
        this.atividadeEconomica.put("2", new Saude());
        this.atividadeEconomica.put("3", new DespesasFamiliares());
        this.atividadeEconomica.put("4", new Habitacao());
        this.atividadeEconomica.put("5", new Educacao());
        this.atividadeEconomica.put("6", new SemDeducao());
    }
    
    /**
     * Método que permite ao Contribuinte Colectivo adicionar uma actividade económica
     * 
     * @param nif nif do utilizador
     * @param cae código de acitivadade a adicionar
     * @throw ContribuinteNaoExistenteException
     */
    public void addCAE(String nif, String cae) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if(c == null)
            throw new ContribuinteNaoExistenteException();

        if(c instanceof Colectivo){
            Colectivo cc = (Colectivo)c;
            cc.addAtividadeEconomica(cae);
        }
        if(c instanceof Individual){
            Individual ci = (Individual)c;
            ci.addCodigo(cae);
        }
    }
    
    /**
     * Método que devolve as actividades económicas relativas a um contribuinte colectivo
     * 
     * @param nif nif do utilizador
     * @throw ContribuinteNaoExistenteException
     * @return lista dos códigos de actividade associados a um Contribuinte Colectivo
     */
    public String showCAE(String nif) throws ContribuinteNaoExistenteException{
        StringBuilder sb = new StringBuilder();
        Contribuinte c = this.gestao_contribuintes.get(nif);
        ArrayList<String> cae = new ArrayList<>();
        
        if(c == null)
            throw new ContribuinteNaoExistenteException(nif);
        
        if(c instanceof Individual){
            Individual ci = (Individual)c;
            cae = (ArrayList)ci.getCodigo();
        }
        if(c instanceof Colectivo){
            Colectivo cc = (Colectivo)c;
            cae = (ArrayList)cc.getActividadeEconomica();
        }
            
        for(String cod: cae){
            sb.append("Código da actividade: "    + cod + "\n");
            sb.append("Descrição da actividade: " + this.atividadeEconomica.get(cod).getDescricao() + "\n\n");
        }
        return sb.toString();
    }
    
    /**
     * Método que devolve a string com todos CAE
     */
    public String showCAE(){
        StringBuilder sb = new StringBuilder();
        
        for(Map.Entry<String,CAE> par : this.atividadeEconomica.entrySet()){
            sb.append("Código da actividade: "    + par.getKey() + "\n");
            sb.append("Descrição da actividade: " + par.getValue().getDescricao() + "\n\n");
        }
        return sb.toString();
    }
    
    /**
     * Método que verifica se existe uma determinada actividade economica
     * 
     * @param cae código de actividade económica
     * @return true se existe uma actividade com o código cae
     */
    public boolean existeCAE(String cae){
        return this.atividadeEconomica.containsKey(cae);
    }
    
    /*=============================================================================================================
     - Listagem de faturas
     =============================================================================================================*/

    /**
     * Método que devolve uma fatura relativa a um nif dado o seu código de fatura
     * 
     * @param nif nif do utilizador
     * @param codFatura codigo da fatura
     * @throw ContribuinteNaoExistenteException
     * @throw FaturaInexistenteException
     * @return fatura pretendida
     */
    public Fatura getFatura(String nif, String codFatura) throws ContribuinteNaoExistenteException,FaturaInexistenteException {
        Contribuinte c = this.gestao_contribuintes.get(nif);
        
        if(c == null)
            throw new ContribuinteNaoExistenteException();
            
        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        Fatura f;
        
        if( cods.contains(codFatura) )
            f = this.gestao_faturas.get(codFatura);
        else
            throw new FaturaInexistenteException();
        return f;
    }
    
    /**
     * Método que devolve um conjunto de faturas de um contribuinte ordenadas por data de emissão
     *
     * @param nif nif do utlizador
     * @throw ContribuinteNaoExistenteException
     * @return conjunto das faturas de um utilizador ordenadas por data de emissão
     */
    public Set<Fatura> faturasOrdData(String nif) throws ContribuinteNaoExistenteException{
        TreeSet<Fatura> faturas = new TreeSet<>(new FaturaDataComparator());
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<String> codFat  = (TreeSet)c.getCodFaturas();

        for(String cod: codFat){
            faturas.add(this.gestao_faturas.get(cod).clone());
        }
        return faturas;
    }

    /**
     * Método que devolve um conjunto de faturas de um contribuinte ordenadas por ordem decrescente de valor de despesas
     *
     * @param nif nif do utilizador
     * @throw ContribuinteNaoExistenteException
     * @return conjunto das faturas de um utilizador ordenadas decrescente por valor de despesa
     */
    public Set<Fatura> faturasOrdValor(String nif) throws ContribuinteNaoExistenteException{
        TreeSet<Fatura> faturas = new TreeSet<>(new FaturaValorComparator());
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<String> codFat  = (TreeSet)c.getCodFaturas();

        for(String cod: codFat){
            faturas.add(this.gestao_faturas.get(cod).clone());
        }
        return faturas;
    }

    /**
     * Método que devolve uma listagem de faturas num determinado intervalo de datas
     *
     * @param nif nif do utlizador
     * @param data_inicial data inicial de procura
     * @param data_final data final de procura
     * @throw ContribuinteNaoExistenteException
     * @return conjunto de faturas de um utilizador compreendidas entre duas datas
     */
    public Set<Fatura> faturasEntreDatas(String nif, LocalDate data_inicial, LocalDate data_final)
                                         throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<Fatura> faturas = new TreeSet<>(new FaturaDataComparator());
        TreeSet<String> cods    = (TreeSet)c.getCodFaturas();
        Fatura f;

        for(String cod: cods){
            f = this.gestao_faturas.get(cod).clone();
            if( f.getDataDespesa().compareTo(data_inicial) >= 0 && f.getDataDespesa().compareTo(data_final) <= 0 )
                faturas.add(f);
        }
        return faturas;
    }

    /**
     * Método que devolve a listagem de faturas pendentes
     * 
     * @param nif nif do utilizador
     * @throw ContribuinteNaoExistenteException
     * @return conjunto de faturas pendentes
     */
    public Set<Fatura> faturasPendentes(String nif) throws ContribuinteNaoExistenteException{
        TreeSet<Fatura> faturas = new TreeSet<>(new FaturaDataComparator());
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        Fatura f;

        for(String cod: cods){
            f = this.gestao_faturas.get(cod);
            if( f.getEstadoFatura() == false )
                faturas.add(f);
        }
        return faturas;
    }

    /**
     * Método que devolve uma listagem dos contribuintes e respetivas faturas num determinado intervalo de tempo
     *
     * @param nif nif 
     * @param LocalDateTime data_inicial
     * @param LocalDateTime data_final
     *
     */
    public Map<String,List<Fatura>> faturaTotalUser(String nif, LocalDate data_inicial, LocalDate data_final)
                                                    throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        HashMap<String,List<Fatura>> users = new HashMap<>();
        TreeSet<String> cods = new TreeSet <>();
        cods = (TreeSet)c.getCodFaturas();
        List<Fatura> l = new ArrayList<>();
        
        for(String cod: cods){
            l.add(this.gestao_faturas.get(cod));
        }

        for(Fatura i: l){
            if (i.getDataDespesa().compareTo(data_inicial) < 0 && i.getDataDespesa().compareTo(data_final) > 0)
            l.remove(i);
        }

        int n=0;
        String ni;
        ArrayList<Fatura> res;

        for(Fatura i: l){
            ni = i.getNifCliente();
            if (users.containsKey(ni)){
                users.get(ni).add(i);
            }else {
                res = new ArrayList<>();
                res.add(i);
                users.put(ni, res);
            }
        }
        return users;
    }

    /**
     * Método que devolve uma listagem dos contribuintes e respetivas faturas ordenadas por despesa da maior para a menor
     *
     * @param nif empresa
     *
     */
    public Map<String,List<Fatura>> faturaTotalOrDesp(String nif) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        HashMap<String,List<Fatura>> users = new HashMap<>();
        TreeSet<String> cods = new TreeSet <>();
        cods = (TreeSet)c.getCodFaturas();
        List<Fatura> l = new ArrayList<>();
        Fatura d;

        for(String cod: cods){//lista com as faturas todas -l
            l.add(this.gestao_faturas.get(cod));
        }

        l.sort(new FaturaValorComparator());

        int n=0;
        String ni;
        ArrayList<Fatura> res;
        for(Fatura i: l){
            ni = i.getNifCliente();
            if (users.containsKey(ni)){
                users.get(ni).add(i);
            }else {
                res = new ArrayList<>();
                res.add(i);
                users.put(ni, res);
            }
        }
        return users;
    }
    
    /**
     * Método que devolve a listagem dos 10 contribuintes mais gastam em todo o sistema
     */
    public TreeSet<Par> contribuintesMaisFaturam() throws ContribuinteNaoExistenteException{
        TreeSet<Par> par = new TreeSet<>(new ParValorComparator());
        
        try{
            for(Contribuinte c: this.gestao_contribuintes.values()){
                if(c instanceof Individual)
                    par.add(new Par(c.clone() , faturacaoTotal(c.getNIF())));
            }
        }catch(ContribuinteNaoExistenteException e){
            e.getMessage();
        }
        
        int i;
        TreeSet<Par> par10 = new TreeSet<>(new ParValorComparator());
        Iterator<Par> it   = par.iterator();
        
        for(i = 0; it.hasNext() && i < 10; i++){
            par10.add(it.next());
        }
        return par10;
    }
    
    /**
     * Método que devolve as n empresas com mais faturas
     */
    public TreeSet<Par> empresasMaisFaturas(int n) throws ContribuinteNaoExistenteException{
        TreeSet<Par> par = new TreeSet<>(new ParValorComparator());
        
        for(Contribuinte c: this.gestao_contribuintes.values()){
                if(c instanceof Colectivo){
                    Colectivo cc = (Colectivo)c;
                    par.add(new Par(cc.clone() , cc.getCodFaturas().size(), cc.getFactorFiscal() * faturacaoTotal(cc.getNIF())));
                }
                if(c instanceof EmpresaInterior){
                    EmpresaInterior cc = (EmpresaInterior)c;
                    par.add(new Par(cc.clone() , cc.getCodFaturas().size(), 
                            cc.getFactorFiscal() * faturacaoTotal(cc.getNIF()) * cc.getIncentivo()));
                }
        }
        
        int i;
        TreeSet<Par> parX = new TreeSet<>(new ParValorComparator());
        Iterator<Par> it = par.iterator();
        
        for(i = 0; it.hasNext() && i < n; i++){
            parX.add(it.next());
        }
        return parX;
    }
    
    /**
     * Método que devolve um conjunto de todas as faturas do sistema
     */
    public TreeSet<Fatura> todasFaturas(){
        return (TreeSet)this.gestao_faturas.values()
                                           .stream()
                                           .collect(Collectors.toCollection(()->new TreeSet<>(new FaturaDataComparator())));
    }
    
    /**
     * Método que devolve o conjunto de contribuintes associados a uma empresa
     */
    public TreeSet<Contribuinte> contribuintesEmpresa(String nif) throws ContribuinteNaoExistenteException{
        Colectivo c = (Colectivo)this.gestao_contribuintes.get(nif);
        
        if(c == null)
            throw new ContribuinteNaoExistenteException();
        
        TreeSet<String> cods = c.getCodFaturas();
        Fatura f;
        TreeSet<Contribuinte> cc = new TreeSet<>((a1,a2)->a1.getNIF().compareTo(a2.getNIF()));
        
        for(String cod: cods){
            f = this.gestao_faturas.get(cod);
            if(cc.contains(this.gestao_contribuintes.get(f.getNifCliente())) == false)
                cc.add(this.gestao_contribuintes.get(f.getNifCliente()));
        }
        return cc;
    }
    
    /**
     * Método que devolve a lista de todas as empresas do sistema
     */
    public TreeSet<Contribuinte> getEmpresas(){
        return this.gestao_contribuintes.values()
                                .stream()
                                .filter(c -> (c instanceof Colectivo))
                                .map(c -> (Colectivo)c)
                                .collect(Collectors.toCollection(()->new TreeSet<>((a1,a2)->(a1.getNIF().compareTo(a2.getNIF())))));
    }
    
    /**
     * Método que devolve a lista de todos os contribuintes individuais do sistema
     */
    public TreeSet<Contribuinte> getContribuintes(){
        return this.gestao_contribuintes.values()
                                .stream()
                                .filter(c -> (c instanceof Individual))
                                .map(c -> (Individual)c)
                                .collect(Collectors.toCollection(()->new TreeSet<>((a1,a2)->(a1.getNIF().compareTo(a2.getNIF())))));
    }
    
    /*=============================================================================================================
     - Faturação
     =============================================================================================================*/

    /**
     * Método que devolve a faturação total de uma empresa num determinado periodo
     *
     * @param String nif
     * @param LocalDate data_inicial
     * @param LocalDate data_final
     */
    public double faturacaoTotal(String nif, LocalDate data_inicial, LocalDate data_final)
                                 throws ContribuinteNaoExistenteException{
        double faturacao = 0;
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        Fatura f;

        for(String cod: cods){
            f = this.gestao_faturas.get(cod);
            if( f.getDataDespesa().compareTo(data_inicial) >= 0 && f.getDataDespesa().compareTo(data_final) <= 0)
                faturacao += f.getValorDespesa();
        }
        return faturacao;
    }
    
    /**
     * Método que devolve o total faturado por um contribuinte
     */
    public double faturacaoTotal(String nif)throws ContribuinteNaoExistenteException{
        double faturacao = 0;
        Contribuinte c = this.gestao_contribuintes.get(nif);
        if( c == null )
            throw new ContribuinteNaoExistenteException(nif);

        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        Fatura f;
        
        for(String cod: cods){
            f = this.gestao_faturas.get(cod);
            faturacao += f.getValorDespesa();
        }
        return faturacao;
    }
    
    /*=============================================================================================================
     - Faturas
     =============================================================================================================*/

    /**
     * Método que adiciona uma fatura
     *
     * @param String nif_emitente
     * @param String emitente
     * @param String nif_cliente
     * @param String descricao_despesa
     * @param valor_despesa
     */
    public void addFaturas(String nif_emitente, String emitente,String nif_cliente,String descricao_despesa,
                            float valor_despesa) throws ContribuinteNaoExistenteException{
        Integer codFat = this.gestao_faturas.size() + 1;
        boolean estadoFat;
        String codCAE;

        Colectivo c = (Colectivo)this.gestao_contribuintes.get(nif_emitente);
        if( c == null)
            throw new ContribuinteNaoExistenteException(nif_emitente);

        estadoFat   = c.getActividadeEconomica().size() == 1;

        if( estadoFat )
            codCAE = c.getActividadeEconomica().get(0);
        else
            codCAE = "";

        Fatura f = new Fatura(codFat.toString(), nif_emitente, emitente, nif_cliente, descricao_despesa,
                              valor_despesa, codCAE);
        
        if(estadoFat)
            f.setEstadoFatura(true);
        else
            f.setEstadoFatura(false);
            
        c.addFatura(codFat.toString());
        Contribuinte cc = this.gestao_contribuintes.get(nif_cliente);
        if(cc == null)
            throw new ContribuinteNaoExistenteException();
        cc.addFatura(codFat.toString());
        this.gestao_faturas.put(codFat.toString(), f.clone());
    }

    /**
     * Método que adiciona faturas
     */
    public void adicionaFaturas(Fatura f) throws ContribuinteNaoExistenteException{
        Contribuinte cc = this.gestao_contribuintes.get(f.getNifCliente());
        Contribuinte ce = this.gestao_contribuintes.get(f.getNifEmitente());

        if(cc == null)
            throw new ContribuinteNaoExistenteException(f.getNifCliente());
        if(ce == null)
            throw new ContribuinteNaoExistenteException(f.getNifEmitente());

        cc.addFatura(f.getCodFatura());
        ce.addFatura(f.getCodFatura());
        this.gestao_faturas.put(f.getCodFatura(),f.clone());
    }

    /**
     * Método que permite ao contribuinte individual finalizar uma fatura
     *
     * @param String nif
     * @param String cae
     */
    public boolean finalizaFatura(String codFatura, String cae) {
        Fatura f = this.gestao_faturas.get(codFatura);

        if( f == null || this.atividadeEconomica.containsKey(cae) == false )
            return false;
            
        Colectivo c = (Colectivo)this.gestao_contribuintes.get(f.getEmitente());
        
        if(c.getActividadeEconomica() != null && c.getActividadeEconomica().contains(cae) && c.getActividadeEconomica().contains(cae)){
            f.setAtividadeEconomica(cae);
            f.setEstadoFatura(true);
            return true;
        }
        else
            return false;
    }
    
    /**
     * Método que verifica se existe uma determinada fatura no sistema
     */
    public boolean existeFatura(String codFatura){
        return this.gestao_faturas.containsKey(codFatura);
    }
    
    /**
     * Método que modifica uma atividade económica de uma fatura
     */
    public void mudaAtividadeFatura(String nif, String codF, String novoCod) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        
        if(c == null)
            throw new ContribuinteNaoExistenteException();
            
        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        Fatura f;
        
        if(cods.contains(codF)){
            f = this.gestao_faturas.get(codF);
            f.setAtividadeEconomica(novoCod);
            f.setEstadoFatura(true);
        }
    }
    
    /*=============================================================================================================
     - Cálculo de deduções
     =============================================================================================================*/
     
    /**
     * Método que calcula o montante de dedução fiscal
     */
    public double montanteDeducao(String nif) throws ContribuinteNaoExistenteException{
        Contribuinte e = (Individual)this.gestao_contribuintes.get(nif); 
        Individual c   = (Individual)e;
        Colectivo cc;
        double despesa, tetoMaximo, valorActual, factor_fiscal;
        String codCae;
        
        if(c == null)
            throw new ContribuinteNaoExistenteException(nif);
        
        TreeSet<String> cods = (TreeSet)c.getCodFaturas();
        HashMap<String,Double> cae = new HashMap<>();
        Fatura f;
        
        for(String cod: cods){
            f = this.gestao_faturas.get(cod);
            cc = (Colectivo)this.gestao_contribuintes.get(f.getNifEmitente());
            factor_fiscal = cc.getFactorFiscal();
            codCae  = f.getCodigoAtividade();
            if(f.getEstadoFatura() == true && c.getCodigo().contains(codCae)){
                despesa = f.getValorDespesa();
                tetoMaximo = this.atividadeEconomica.get(codCae).getValorMaximo();
                if(cae.containsKey(codCae) == true){
                    valorActual = cae.get(codCae);
                    if(valorActual + (despesa  * (1 + factor_fiscal)) > tetoMaximo)
                        cae.put(codCae, tetoMaximo);
                    else
                        cae.put(codCae, valorActual + despesa * (1 + factor_fiscal));
                }          
                else
                    cae.put(codCae, despesa);
            }
        }
        
        double deducao = 0;            
        for(Map.Entry<String,Double> par: cae.entrySet()){
            deducao += this.atividadeEconomica.get(par.getKey()).calcula(par.getValue(), c.getCoeficienteFiscal());
        }
        
        return deducao;
    }
    
    /**
     * Método que calcula o montante de deducao do agregado familiar
     */
    public double montanteDeducaoAgregado(String nif) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        Individual i   = (Individual)c;
        double deducao = 0;
        
        if(c == null)
            throw new ContribuinteNaoExistenteException();
            
        ArrayList<String> agr = (ArrayList)i.getListaAgregado();
        
        for(String n: agr)
            deducao += deducaoIncentivo(n);
        return deducao;
    }
    
    /**
     * Método que calcula a dedução fiscal com incentivo
     */
    public double deducaoIncentivo(String nif) throws ContribuinteNaoExistenteException{
        Contribuinte c = this.gestao_contribuintes.get(nif);
        FamiliaNumerosa fm;
        EmpresaInterior ei;
        double deducao = montanteDeducao(nif);
        
        if(c instanceof FamiliaNumerosa){
            fm = (FamiliaNumerosa)c;
            return fm.reducaoImposto(deducao);
        }
        
        if(c instanceof EmpresaInterior){
            ei = (EmpresaInterior)c;
            return ei.reducaoImposto(deducao);
        }
        
        if (c instanceof Individual){
            return deducao;
        }
        
        return -1;
    }
    
    /*=============================================================================================================
     - Ler e gravar o estado em ficheiro
     =============================================================================================================*/

    /**
     * Método que grava, em modo binário, o estado da aplicação num ficheiro
     *
     * @param String nomeFicheiro
     */
    public void gravaEstado(String nomeFicheiro) throws FileNotFoundException, IOException{
        FileOutputStream fos   = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    /**
     * Método que lê de um ficheiro o estado da aplicação e carrega as estruturas da aplicação com essa informação
     *
     * @param String nomeFicheiro
     */
    public GestaoFaturas carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis   = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GestaoFaturas gf      = (GestaoFaturas) ois.readObject();
        ois.close();
        return gf;
    }
    
    /*=============================================================================================================
     - Preenchimento de estruturas 
     =============================================================================================================*/

    /**
     * Método de teste
     */
    public void registos(){
        Individual i1  = new Individual ("nome1","nif1","email1","morada1","pass1",0,0.07);
        Individual i2  = new Individual ("nome2","nif2","email2","morada2","pass2",0,0.13);
        Individual i3  = new Individual ("nome3","nif3","email3","morada3","pass3",0,0.11);
        Individual i4  = new Individual ("nome4","nif4","email4","morada4","pass4",0,0.13);
        Individual i5  = new Individual ("nome5","nif5","email5","morada5","pass5",0,0.12);
        Individual i6  = new Individual ("nome6","nif6","email6","morada6","pass6",0,0.05);
        Individual i7  = new Individual ("nome7","nif7","email7","morada7","pass7",0,0.04);
        Individual i8  = new Individual ("nome8","nif8","email8","morada8","pass8",0,0.02);
        Individual i9  = new Individual ("nome9","nif9","email9","morada9","pass9",0,0.02);
        Individual i10 = new Individual ("nome10","nif10","email10","morada10","pass10",0,0.12);
        Individual i11 = new Individual ("nome11","nif11","email11","morada11","pass11",0,0.02);
        Individual i12 = new Individual ("nome12","nif12","email12","morada12","pass12",0,0.04);
        Individual i13 = new Individual ("nome13","nif13","email13","morada13","pass13",0,0.05);
        Individual i14 = new Individual ("nome14","nif14","email14","morada14","pass14",0,0.13);
        Individual i15 = new Individual ("nome15","nif15","email15","morada15","pass15",0,0.11);
        Individual i16 = new Individual ("nome16","nif16","email16","morada16","pass16",0,0.07);
        Individual i17 = new Individual ("nome17","nif17","email17","morada17","pass17",0,0.13);
        Individual i18 = new Individual ("nome18","nif18","email18","morada18","pass18",0,0.11);
        Individual i19 = new Individual ("nome19","nif19","email19","morada19","pass19",0,0.02);
        Individual i20 = new Individual ("nome20","nif20","email20","morada20","pass20",0,0.04);


        Colectivo c1   = new Colectivo  ("emp1","#nif1","email#1","morada#1","pass1",0.12);
        Colectivo c2   = new Colectivo  ("emp2","#nif2","email#2","morada#2","pass2",0.13);
        Colectivo c3   = new Colectivo  ("emp3","#nif3","emai#3","morada#3","pass3",0.11);
        Colectivo c4   = new Colectivo  ("emp4","#nif4","emai#4","morada#4","pass4",0.08);
        Colectivo c5   = new Colectivo  ("emp5","#nif5","email#5","morada#5","pass5",0.02);
        Colectivo c6   = new Colectivo  ("emp6","#nif6","email#6","morada#6","pass6",0.14);
        Colectivo c7   = new Colectivo  ("emp7","#nif7","email#7","morada#7","pass7",0.13);
        Colectivo c8   = new Colectivo  ("emp8","#nif8","email#8","morada#8","pass8",0.12); 
        Colectivo c9   = new Colectivo  ("emp9","#nif9","email#9","morada#9","pass9",0.12);
        Colectivo c10  = new Colectivo  ("emp10","#nif10","email#10","morada#10","pass10",0.14);
        Colectivo c11  = new Colectivo  ("emp11","#nif11","email#11","morada#11","pass11",0.08);
        Colectivo c12  = new Colectivo  ("emp12","#nif12","email#12","morada#12","pass12",0.03);
        Colectivo c13  = new Colectivo  ("emp13","#nif13","email#13","morada#13","pass13",0.05);
        Colectivo c14  = new Colectivo  ("emp14","#nif14","email#14","morada#14","pass14",0.11);
        Colectivo c15  = new Colectivo  ("emp15","#nif15","email#15","morada#15","pass15",0.08);
        Colectivo c16  = new Colectivo  ("emp16","#nif16","email#16","morada#16","pass16",0.02);
        Colectivo c17  = new Colectivo  ("emp17","#nif17","email#17","morada#17","pass17",0.14);
        Colectivo c18  = new Colectivo  ("emp18","#nif18","email#18","morada#18","pass18",0.13);
        Colectivo c19  = new Colectivo  ("emp19","#nif19","email#19","morada#19","pass19",0.04);
        Colectivo c20  = new Colectivo  ("emp20","#nif20","email#20","morada#20","pass20",0.12);

        Fatura f1 = new Fatura ("1","#nif1","emp1",1,14,"nif1","desp1",35.31,"");
        Fatura f2 = new Fatura ("2","#nif8","emp8",1,20,"nif1","desp2",5.31,"");
        Fatura f3 = new Fatura ("3","#nif2","emp2",2,15,"nif7","desp3",40.45,"");
        Fatura f4 = new Fatura ("4","#nif6","emp6",5,26,"nif8","desp4",2.34,"");
        Fatura f5 = new Fatura ("5","#nif7","emp7",2,15,"nif1","desp5",5.34,"");
        Fatura f6 = new Fatura ("6","#nif3","emp3",3,1,"nif6","desp6",4.34,"");
        
        Fatura f7 = new Fatura ("7","#nif1","emp1",12,15,"nif1","desp7",5.31,"");
        Fatura f8 = new Fatura ("8","#nif4","emp4",11,23,"nif1","desp8",22.10,"");
        Fatura f9 = new Fatura ("9","#nif2","emp2",6,12,"nif1","desp9",1.99,"");
        Fatura f10 = new Fatura ("10","#nif8","emp8",2,14,"nif1","desp10",5.99,"");
        Fatura f11 = new Fatura ("11","#nif4","emp4",5,15,"nif1","desp11",3.49,"");
        Fatura f12 = new Fatura ("12","#nif7","emp7",8,3,"nif1","desp12",20.10,"");
        Fatura f13 = new Fatura ("13","#nif1","emp1",2,11,"nif1","desp13",354.31,"");
        Fatura f14 = new Fatura ("14","#nif2","emp2",10,11,"nif1","desp14",10.00,"");
        Fatura f15 = new Fatura ("15","#nif3","emp3",9,22,"nif1","desp15",124.51,"");
        Fatura f16 = new Fatura ("16","#nif5","emp5",4,25,"nif1","desp16",9.99,"");
        
        Fatura f17 = new Fatura ("17","#nif3","emp3",5,4,"nif7","desp17",20.25,"");
        Fatura f18 = new Fatura ("18","#nif2","emp2",6,3,"nif7","desp18",14.50,"");
        Fatura f19 = new Fatura ("19","#nif2","emp2",9,17,"nif7","desp19",100.45,"");
        Fatura f20 = new Fatura ("20","#nif7","emp7",12,9,"nif7","desp20",200.00,"");
        Fatura f21 = new Fatura ("21","#nif5","emp5",7,1,"nif7","desp21",10.45,"");
        Fatura f22 = new Fatura ("22","#nif5","emp5",1,20,"nif7","desp22",0.45,"");
        Fatura f23 = new Fatura ("23","#nif3","emp3",1,15,"nif7","desp23",0.65,"");
        Fatura f24 = new Fatura ("24","#nif6","emp6",5,1,"nif7","desp24",100.35,"");
        Fatura f25 = new Fatura ("25","#nif6","emp6",4,5,"nif7","desp25",25.00,"");
        Fatura f26 = new Fatura ("26","#nif2","emp2",3,6,"nif7","desp26",31.12,"");
        Fatura f27 = new Fatura ("27","#nif1","emp1",2,27,"nif7","desp27",144.50,"");
        Fatura f28 = new Fatura ("28","#nif1","emp1",8,19,"nif7","desp28",50.45,"");
        Fatura f29 = new Fatura ("29","#nif6","emp6",2,24,"nif7","desp29",12.99,"");
        Fatura f30 = new Fatura ("30","#nif3","emp3",10,28,"nif7","desp30",15.01,"");
        Fatura f31 = new Fatura ("31","#nif8","emp8",1,14,"nif7","desp31",412.15,"");
        
        Fatura f32 = new Fatura ("32","#nif8","emp8",2,24,"nif20","desp32",4.15,"");
        Fatura f33 = new Fatura ("33","#nif14","emp14",6,12,"nif20","desp33",12.15,"");
        Fatura f34 = new Fatura ("34","#nif14","emp14",9,12,"nif19","desp34",49.13,"");
        Fatura f35 = new Fatura ("35","#nif15","emp15",4,20,"nif17","desp35",200.15,"");
        Fatura f36 = new Fatura ("36","#nif15","emp15",12,20,"nif18","desp36",100.99,"");
        Fatura f37 = new Fatura ("37","#nif16","emp16",11,14,"nif17","desp37",5.00,"");
        Fatura f38 = new Fatura ("38","#nif16","emp16",10,14,"nif19","desp38",20.15,"");
        Fatura f39 = new Fatura ("39","#nif17","emp17",2,10,"nif16","desp39",35.15,"");
        Fatura f40 = new Fatura ("40","#nif17","emp17",2,24,"nif15","desp40",12.99,"");
        Fatura f41 = new Fatura ("41","#nif8","emp8",3,15,"nif14","desp41",1000.14,"");
        Fatura f42 = new Fatura ("42","#nif18","emp18",4,29,"nif13","desp42",20.12,"");
        Fatura f43 = new Fatura ("43","#nif18","emp18",6,10,"nif14","desp43",30.12,"");
        Fatura f44 = new Fatura ("44","#nif18","emp18",7,19,"nif15","desp44",60.99,"");
        Fatura f45 = new Fatura ("45","#nif19","emp19",3,13,"nif11","desp45",2.15,"");
        Fatura f46 = new Fatura ("46","#nif19","emp19",8,20,"nif12","desp46",70.89,"");
        Fatura f47 = new Fatura ("47","#nif19","emp19",8,25,"nif14","desp47",1.99,"");
        Fatura f48 = new Fatura ("48","#nif20","emp20",10,1,"nif19","desp48",2.99,"");
        Fatura f49 = new Fatura ("49","#nif20","emp20",11,1,"nif20","desp49",4120.00,"");
        Fatura f50 = new Fatura ("50","#nif20","emp20",9,27,"nif5","desp50",20.99,"");
        
        Fatura f51 = new Fatura ("51","#nif1","emp1",2,24,"nif1","desp51",35.31,"");
        Fatura f52 = new Fatura ("52","#nif8","emp8",1,29,"nif1","desp52",5.31,"");
        Fatura f53 = new Fatura ("53","#nif2","emp2",3,20,"nif7","desp53",40.45,"");
        Fatura f54 = new Fatura ("54","#nif6","emp6",5,14,"nif8","desp54",2.34,"");
        Fatura f55 = new Fatura ("55","#nif7","emp7",1,26,"nif1","desp55",5.34,"");
        Fatura f56 = new Fatura ("56","#nif3","emp3",5,18,"nif6","desp56",4.34,"");
        
        Fatura f57 = new Fatura ("57","#nif1","emp1",9,20,"nif1","desp57",5.31,"");
        Fatura f58 = new Fatura ("58","#nif4","emp4",1,4,"nif1","desp58",22.10,"");
        Fatura f59 = new Fatura ("59","#nif2","emp2",8,12,"nif7","desp59",1.99,"");
        Fatura f60 = new Fatura ("60","#nif8","emp8",1,15,"nif15","desp60",5.99,"");
        Fatura f61 = new Fatura ("61","#nif4","emp4",12,29,"nif18","desp61",3.49,"");
        Fatura f62 = new Fatura ("62","#nif7","emp7",11,1,"nif12","desp62",20.10,"");
        Fatura f63 = new Fatura ("63","#nif1","emp1",1,12,"nif6","desp63",354.31,"");
        Fatura f64 = new Fatura ("64","#nif2","emp2",5,1,"nif5","desp64",10.00,"");
        Fatura f65 = new Fatura ("65","#nif3","emp3",2,28,"nif4","desp65",124.51,"");
        Fatura f66 = new Fatura ("66","#nif5","emp5",9,12,"nif4","desp66",9.99,"");
        
        Fatura f67 = new Fatura ("67","#nif3","emp3",11,19,"nif10","desp67",2.25,"");
        Fatura f68 = new Fatura ("68","#nif2","emp2",10,10,"nif11","desp68",142.50,"");
        Fatura f69 = new Fatura ("69","#nif2","emp2",12,15,"nif14","desp69",1005.45,"");
        Fatura f70 = new Fatura ("70","#nif7","emp7",1,12,"nif13","desp70",20.00,"");
        Fatura f71 = new Fatura ("71","#nif5","emp5",9,10,"nif14","desp71",15.45,"");
        Fatura f72 = new Fatura ("72","#nif5","emp5",1,14,"nif4","desp72",14.45,"");
        Fatura f73 = new Fatura ("73","#nif3","emp3",9,2,"nif3","desp73",66.65,"");
        Fatura f74 = new Fatura ("74","#nif6","emp6",4,4,"nif2","desp74",100.35,"");
        Fatura f75 = new Fatura ("75","#nif6","emp6",5,9,"nif6","desp75",55.00,"");
        Fatura f76 = new Fatura ("76","#nif2","emp2",2,1,"nif3","desp76",41.12,"");
        Fatura f77 = new Fatura ("77","#nif1","emp1",10,1,"nif4","desp77",134.50,"");
        Fatura f78 = new Fatura ("78","#nif1","emp1",9,4,"nif6","desp78",10.45,"");
        Fatura f79 = new Fatura ("79","#nif6","emp6",10,28,"nif5","desp79",167.99,"");
        Fatura f80 = new Fatura ("80","#nif3","emp3",4,23,"nif8","desp80",15.01,"");
        Fatura f81 = new Fatura ("81","#nif8","emp8",5,16,"nif4","desp81",42.15,"");
        Fatura f82 = new Fatura ("82","#nif8","emp8",4,12,"nif20","desp82",45.15,"");
        Fatura f83 = new Fatura ("83","#nif14","emp14",6,14,"nif20","desp83",2.15,"");
        Fatura f84 = new Fatura ("84","#nif14","emp14",9,24,"nif15","desp84",4.13,"");
        Fatura f85 = new Fatura ("85","#nif15","emp15",12,14,"nif10","desp85",200.15,"");
        Fatura f86 = new Fatura ("86","#nif15","emp15",1,16,"nif18","desp86",100.99,"");
        Fatura f87 = new Fatura ("87","#nif16","emp16",1,19,"nif11","desp87",5.00,"");
        Fatura f88 = new Fatura ("88","#nif16","emp16",2,24,"nif5","desp88",20.15,"");
        Fatura f89 = new Fatura ("89","#nif17","emp17",3,25,"nif6","desp89",2000.15,"");
        Fatura f90 = new Fatura ("90","#nif17","emp17",1,1,"nif15","desp90",124.99,"");
        Fatura f91 = new Fatura ("91","#nif8","emp8",12,1,"nif4","desp91",1000.14,"");
        Fatura f92 = new Fatura ("92","#nif18","emp18",12,24,"nif3","desp92",25.12,"");
        Fatura f93 = new Fatura ("93","#nif18","emp18",2,4,"nif14","desp93",36.12,"");
        Fatura f94 = new Fatura ("94","#nif18","emp18",1,10,"nif15","desp94",67.99,"");
        Fatura f95 = new Fatura ("95","#nif19","emp19",9,12,"nif1","desp95",221.15,"");
        Fatura f96 = new Fatura ("96","#nif19","emp19",3,13,"nif12","desp96",30.89,"");
        Fatura f97 = new Fatura ("97","#nif19","emp19",7,15,"nif3","desp97",15.99,"");
        Fatura f98 = new Fatura ("98","#nif20","emp20",6,19,"nif2","desp98",22.99,"");
        Fatura f99 = new Fatura ("99","#nif20","emp20",12,10,"nif9","desp99",4.00,"");
        Fatura f100 = new Fatura ("100","#nif20","emp20",10,10,"nif5","desp100",214.99,"");
        
        Fatura f101 = new Fatura ("101","#nif1","emp1",12,13,"nif1","desp101",3.31,"");
        Fatura f102 = new Fatura ("102","#nif8","emp8",3,24,"nif1","desp102",53.51,"");
        Fatura f103 = new Fatura ("103","#nif2","emp2",4,5,"nif7","desp103",4.55,"");
        Fatura f104 = new Fatura ("104","#nif6","emp6",2,14,"nif8","desp104",6.54,"");
        Fatura f105 = new Fatura ("105","#nif7","emp7",6,12,"nif1","desp105",73.34,"");
        Fatura f106 = new Fatura ("106","#nif3","emp3",5,13,"nif6","desp106",324.34,"");
        
        Fatura f107 = new Fatura ("107","#nif1","emp1",12,14,"nif5","desp107",6.33,"");
        Fatura f108 = new Fatura ("108","#nif4","emp4",2,1,"nif4","desp108",25.03,"");
        Fatura f109 = new Fatura ("109","#nif2","emp2",3,1,"nif3","desp109",19.99,"");
        Fatura f110 = new Fatura ("110","#nif8","emp8",12,1,"nif1","desp110",1.99,"");
        Fatura f111 = new Fatura ("111","#nif4","emp4",10,1,"nif1","desp111",5.21,"");
        Fatura f112 = new Fatura ("112","#nif7","emp7",2,2,"nif1","desp112",2.99,"");
        Fatura f113 = new Fatura ("113","#nif1","emp1",3,3,"nif4","desp113",23.31,"");
        Fatura f114 = new Fatura ("114","#nif2","emp2",4,4,"nif3","desp114",1.00,"");
        Fatura f115 = new Fatura ("115","#nif3","emp3",5,6,"nif7","desp115",231.51,"");
        Fatura f116 = new Fatura ("116","#nif5","emp5",6,23,"nif7","desp116",14.99,"");
        
        Fatura f117 = new Fatura ("117","#nif3","emp3",4,25,"nif18","desp117",21.25,"");
        Fatura f118 = new Fatura ("118","#nif2","emp2",1,14,"nif17","desp118",16.50,"");
        Fatura f119 = new Fatura ("119","#nif2","emp2",1,15,"nif1","desp119",1020.45,"");
        Fatura f120 = new Fatura ("120","#nif7","emp7",5,11,"nif2","desp120",20230.00,"");
        Fatura f121 = new Fatura ("121","#nif5","emp5",12,1,"nif3","desp121",104.45,"");
        Fatura f122 = new Fatura ("122","#nif5","emp5",11,2,"nif7","desp122",01.45,"");
        Fatura f123 = new Fatura ("123","#nif3","emp3",10,13,"nif9","desp123",20.55,"");
        Fatura f124 = new Fatura ("124","#nif6","emp6",5,28,"nif5","desp124",0.35,"");
        Fatura f125 = new Fatura ("125","#nif6","emp6",1,21,"nif15","desp125",25.40,"");
        Fatura f126 = new Fatura ("126","#nif2","emp2",3,21,"nif17","desp126",23.12,"");
        Fatura f127 = new Fatura ("127","#nif1","emp1",1,2,"nif17","desp127",14.54,"");
        Fatura f128 = new Fatura ("128","#nif1","emp1",7,1,"nif17","desp128",5.45,"");
        Fatura f129 = new Fatura ("129","#nif6","emp6",7,14,"nif20","desp129",1.99,"");
        Fatura f130 = new Fatura ("130","#nif3","emp3",7,15,"nif13","desp130",123.01,"");
        Fatura f131 = new Fatura ("131","#nif8","emp8",8,21,"nif20","desp131",42.15,"");
        
        Fatura f132 = new Fatura ("132","#nif8","emp8",9,22,"nif13","desp132",41.15,"");
        Fatura f133 = new Fatura ("133","#nif14","emp14",4,11,"nif12","desp133",122.15,"");
        Fatura f134 = new Fatura ("134","#nif14","emp14",12,12,"nif10","desp134",9.13,"");
        Fatura f135 = new Fatura ("135","#nif15","emp15",10,25,"nif15","desp135",20.15,"");
        Fatura f136 = new Fatura ("136","#nif15","emp15",1,14,"nif19","desp136",130.99,"");
        Fatura f137 = new Fatura ("137","#nif16","emp16",9,23,"nif19","desp137",6.00,"");
        Fatura f138 = new Fatura ("138","#nif16","emp16",10,20,"nif18","desp138",223.15,"");
        Fatura f139 = new Fatura ("139","#nif17","emp17",11,15,"nif19","desp139",3.15,"");
        Fatura f140 = new Fatura ("140","#nif17","emp17",7,10,"nif12","desp140",32.99,"");
        Fatura f141 = new Fatura ("141","#nif8","emp8",8,1,"nif13","desp141",120.14,"");
        Fatura f142 = new Fatura ("142","#nif18","emp18",3,4,"nif1","desp142",40.12,"");
        Fatura f143 = new Fatura ("143","#nif18","emp18",4,20,"nif4","desp143",6.12,"");
        Fatura f144 = new Fatura ("144","#nif18","emp18",5,1,"nif12","desp144",666.99,"");
        Fatura f145 = new Fatura ("145","#nif19","emp19",6,23,"nif1","desp145",32.15,"");
        Fatura f146 = new Fatura ("146","#nif19","emp19",7,1,"nif15","desp146",7.89,"");
        Fatura f147 = new Fatura ("147","#nif19","emp19",8,8,"nif13","desp147",12.99,"");
        Fatura f148 = new Fatura ("148","#nif20","emp20",9,10,"nif1","desp148",28.99,"");
        Fatura f149 = new Fatura ("149","#nif20","emp20",10,14,"nif2","desp149",40.00,"");
        Fatura f150 = new Fatura ("150","#nif20","emp20",11,23,"nif1","desp150",2.99,"");
        
        Fatura f151 = new Fatura ("151","#nif1","emp1",12,23,"nif3","desp151",33.31,"");
        Fatura f152 = new Fatura ("152","#nif8","emp8",1,3,"nif2","desp152",3.31,"");
        Fatura f153 = new Fatura ("153","#nif2","emp2",2,16,"nif1","desp153",45.45,"");
        Fatura f154 = new Fatura ("154","#nif6","emp6",3,25,"nif9","desp154",22.34,"");
        Fatura f155 = new Fatura ("155","#nif7","emp7",4,27,"nif2","desp155",54.34,"");
        Fatura f156 = new Fatura ("156","#nif3","emp3",5,23,"nif5","desp156",41.34,"");
        
        Fatura f157 = new Fatura ("157","#nif1","emp1",6,24,"nif13","desp157",0.31,"");
        Fatura f158 = new Fatura ("158","#nif4","emp4",7,1,"nif12","desp158",2.10,"");
        Fatura f159 = new Fatura ("159","#nif2","emp2",8,2,"nif2","desp159",32.99,"");
        Fatura f160 = new Fatura ("160","#nif8","emp8",9,6,"nif5","desp160",41.99,"");
        Fatura f161 = new Fatura ("161","#nif4","emp4",10,25,"nif8","desp161",5.49,"");
        Fatura f162 = new Fatura ("162","#nif7","emp7",11,20,"nif2","desp162",220.10,"");
        Fatura f163 = new Fatura ("163","#nif1","emp1",12,5,"nif16","desp163",3.31,"");
        Fatura f164 = new Fatura ("164","#nif2","emp2",1,8,"nif15","desp164",103.00,"");
        Fatura f165 = new Fatura ("165","#nif3","emp3",2,10,"nif3","desp165",12.51,"");
        Fatura f166 = new Fatura ("166","#nif5","emp5",2,4,"nif2","desp166",91.99,"");
        
        Fatura f167 = new Fatura ("167","#nif3","emp3",3,4,"nif13","desp167",21.25,"");
        Fatura f168 = new Fatura ("168","#nif2","emp2",4,23,"nif1","desp168",12.50,"");
        Fatura f169 = new Fatura ("169","#nif2","emp2",5,11,"nif4","desp169",15.45,"");
        Fatura f170 = new Fatura ("170","#nif7","emp7",6,20,"nif3","desp170",223.00,"");
        Fatura f171 = new Fatura ("171","#nif5","emp5",7,28,"nif4","desp171",154.45,"");
        Fatura f172 = new Fatura ("172","#nif5","emp5",8,12,"nif14","desp172",145.45,"");
        Fatura f173 = new Fatura ("173","#nif3","emp3",6,15,"nif10","desp173",6.65,"");
        Fatura f174 = new Fatura ("174","#nif6","emp6",9,23,"nif3","desp174",10.35,"");
        Fatura f175 = new Fatura ("175","#nif6","emp6",10,11,"nif5","desp175",5.00,"");
        Fatura f176 = new Fatura ("176","#nif2","emp2",11,25,"nif4","desp176",4.12,"");
        Fatura f177 = new Fatura ("177","#nif1","emp1",12,15,"nif2","desp177",34.50,"");
        Fatura f178 = new Fatura ("178","#nif1","emp1",1,25,"nif16","desp178",1.45,"");
        Fatura f179 = new Fatura ("179","#nif6","emp6",2,24,"nif20","desp179",17.99,"");
        Fatura f180 = new Fatura ("180","#nif3","emp3",3,14,"nif20","desp180",125.01,"");
        Fatura f181 = new Fatura ("181","#nif8","emp8",4,25,"nif14","desp181",4.15,"");
        Fatura f182 = new Fatura ("182","#nif8","emp8",5,13,"nif2","desp182",5.15,"");
        Fatura f183 = new Fatura ("183","#nif14","emp14",1,14,"nif2","desp183",12.15,"");
        Fatura f184 = new Fatura ("184","#nif14","emp14",6,23,"nif3","desp184",4.13,"");
        Fatura f185 = new Fatura ("185","#nif15","emp15",7,2,"nif1","desp185",20.15,"");
        Fatura f186 = new Fatura ("186","#nif15","emp15",8,15,"nif8","desp186",10.99,"");
        Fatura f187 = new Fatura ("187","#nif16","emp16",9,1,"nif1","desp187",52.00,"");
        Fatura f188 = new Fatura ("188","#nif16","emp16",10,2,"nif20","desp188",2.15,"");
        Fatura f189 = new Fatura ("189","#nif17","emp17",11,10,"nif16","desp189",20.15,"");
        Fatura f190 = new Fatura ("190","#nif17","emp17",12,1,"nif5","desp190",124.99,"");
        Fatura f191 = new Fatura ("191","#nif8","emp8",1,14,"nif14","desp191",120.14,"");
        Fatura f192 = new Fatura ("192","#nif18","emp18",2,15,"nif15","desp192",254.12,"");
        Fatura f193 = new Fatura ("193","#nif18","emp18",3,10,"nif16","desp193",6.12,"");
        Fatura f194 = new Fatura ("194","#nif18","emp18",5,12,"nif15","desp194",7.99,"");
        Fatura f195 = new Fatura ("195","#nif19","emp19",6,1,"nif10","desp195",21.15,"");
        Fatura f196 = new Fatura ("196","#nif19","emp19",7,9,"nif3","desp196",35.89,"");
        Fatura f197 = new Fatura ("197","#nif19","emp19",8,20,"nif2","desp197",13.99,"");
        Fatura f198 = new Fatura ("198","#nif20","emp20",10,1,"nif3","desp198",22.99,"");
        Fatura f199 = new Fatura ("199","#nif20","emp20",9,14,"nif5","desp199",45.00,"");
        Fatura f200 = new Fatura ("200","#nif20","emp20",12,24,"nif6","desp200",200.99,"");
        
        
        

        registo(i1) ; registo(i2) ; registo(i3) ; registo(i4) ; registo(i5) ; registo(i6) ; registo(i7) ; registo(i8);
        registo(i9) ; registo(i10) ; registo(i11) ; registo(i12) ; registo(i13) ; registo(i14) ; registo(i15) ; registo(i16);
        registo(i17) ; registo(i18) ; registo(i19) ; registo(i20) ;
        
        registo(c1) ; registo(c2) ; registo(c3) ; registo(c4) ; registo(c5) ; registo(c6) ; registo(c7) ; registo(c8);
        registo(c9) ; registo(c10) ; registo(c11) ; registo(c12) ; registo(c13) ; registo(c14) ; registo(c15) ; registo(c16);
        registo(c17) ; registo(c18) ; registo(c19) ; registo(c20) ;

        try{
            adicionaFaturas(f1) ; adicionaFaturas(f2) ; adicionaFaturas(f3) ; adicionaFaturas(f4);
            adicionaFaturas(f5) ; adicionaFaturas(f6) ; adicionaFaturas(f7) ; adicionaFaturas(f8) ;
            adicionaFaturas(f9) ; adicionaFaturas(f10) ; adicionaFaturas(f11) ; adicionaFaturas(f12) ;
            adicionaFaturas(f13) ; adicionaFaturas(f14) ; adicionaFaturas(f15) ; adicionaFaturas(f16) ; 
            adicionaFaturas(f17) ; adicionaFaturas(f18) ; adicionaFaturas(f19) ; adicionaFaturas(f20) ;
            adicionaFaturas(f21) ; adicionaFaturas(f22) ; adicionaFaturas(f23) ; adicionaFaturas(f24) ; 
            adicionaFaturas(f25) ; adicionaFaturas(f26) ; adicionaFaturas(f27) ; adicionaFaturas(f28) ;
            adicionaFaturas(f29) ; adicionaFaturas(f30) ; adicionaFaturas(f31) ; adicionaFaturas(f32) ;
            adicionaFaturas(f33) ; adicionaFaturas(f34) ; adicionaFaturas(f35) ; adicionaFaturas(f36) ;
            adicionaFaturas(f37) ; adicionaFaturas(f38) ; adicionaFaturas(f39) ; adicionaFaturas(f40) ;
            adicionaFaturas(f41) ; adicionaFaturas(f42) ; adicionaFaturas(f43) ; adicionaFaturas(f44) ; 
            adicionaFaturas(f45) ; adicionaFaturas(f46) ; adicionaFaturas(f47) ; adicionaFaturas(f48) ;
            adicionaFaturas(f49) ; adicionaFaturas(f50) ;
            adicionaFaturas(f51) ; adicionaFaturas(f52) ; adicionaFaturas(f53) ; adicionaFaturas(f54);
            adicionaFaturas(f55) ; adicionaFaturas(f56) ; adicionaFaturas(f57) ; adicionaFaturas(f58) ;
            adicionaFaturas(f59) ; adicionaFaturas(f60) ; adicionaFaturas(f61) ; adicionaFaturas(f62) ;
            adicionaFaturas(f63) ; adicionaFaturas(f64) ; adicionaFaturas(f65) ; adicionaFaturas(f66) ; 
            adicionaFaturas(f67) ; adicionaFaturas(f68) ; adicionaFaturas(f69) ; adicionaFaturas(f70) ;
            adicionaFaturas(f71) ; adicionaFaturas(f72) ; adicionaFaturas(f73) ; adicionaFaturas(f74) ; 
            adicionaFaturas(f75) ; adicionaFaturas(f76) ; adicionaFaturas(f77) ; adicionaFaturas(f78) ;
            adicionaFaturas(f79) ; adicionaFaturas(f80) ; adicionaFaturas(f81) ; adicionaFaturas(f82) ;
            adicionaFaturas(f83) ; adicionaFaturas(f84) ; adicionaFaturas(f85) ; adicionaFaturas(f86) ;
            adicionaFaturas(f87) ; adicionaFaturas(f88) ; adicionaFaturas(f89) ; adicionaFaturas(f90) ;
            adicionaFaturas(f91) ; adicionaFaturas(f92) ; adicionaFaturas(f93) ; adicionaFaturas(f94) ; 
            adicionaFaturas(f95) ; adicionaFaturas(f96) ; adicionaFaturas(f97) ; adicionaFaturas(f98) ;
            adicionaFaturas(f99) ; adicionaFaturas(f100) ;
            adicionaFaturas(f101) ; adicionaFaturas(f102) ; adicionaFaturas(f103) ; adicionaFaturas(f104);
            adicionaFaturas(f105) ; adicionaFaturas(f106) ; adicionaFaturas(f107) ; adicionaFaturas(f108) ;
            adicionaFaturas(f109) ; adicionaFaturas(f110) ; adicionaFaturas(f111) ; adicionaFaturas(f112) ;
            adicionaFaturas(f113) ; adicionaFaturas(f114) ; adicionaFaturas(f115) ; adicionaFaturas(f116) ; 
            adicionaFaturas(f117) ; adicionaFaturas(f118) ; adicionaFaturas(f119) ; adicionaFaturas(f120) ;
            adicionaFaturas(f121) ; adicionaFaturas(f122) ; adicionaFaturas(f123) ; adicionaFaturas(f124) ; 
            adicionaFaturas(f125) ; adicionaFaturas(f126) ; adicionaFaturas(f127) ; adicionaFaturas(f128) ;
            adicionaFaturas(f129) ; adicionaFaturas(f130) ; adicionaFaturas(f131) ; adicionaFaturas(f132) ;
            adicionaFaturas(f133) ; adicionaFaturas(f134) ; adicionaFaturas(f135) ; adicionaFaturas(f136) ;
            adicionaFaturas(f137) ; adicionaFaturas(f138) ; adicionaFaturas(f139) ; adicionaFaturas(f140) ;
            adicionaFaturas(f141) ; adicionaFaturas(f142) ; adicionaFaturas(f143) ; adicionaFaturas(f144) ; 
            adicionaFaturas(f145) ; adicionaFaturas(f146) ; adicionaFaturas(f147) ; adicionaFaturas(f148) ;
            adicionaFaturas(f149) ; adicionaFaturas(f150) ;
            adicionaFaturas(f151) ; adicionaFaturas(f152) ; adicionaFaturas(f153) ; adicionaFaturas(f154);
            adicionaFaturas(f155) ; adicionaFaturas(f156) ; adicionaFaturas(f157) ; adicionaFaturas(f158) ;
            adicionaFaturas(f159) ; adicionaFaturas(f160) ; adicionaFaturas(f161) ; adicionaFaturas(f162) ;
            adicionaFaturas(f163) ; adicionaFaturas(f164) ; adicionaFaturas(f165) ; adicionaFaturas(f166) ; 
            adicionaFaturas(f167) ; adicionaFaturas(f168) ; adicionaFaturas(f169) ; adicionaFaturas(f170) ;
            adicionaFaturas(f171) ; adicionaFaturas(f172) ; adicionaFaturas(f173) ; adicionaFaturas(f174) ; 
            adicionaFaturas(f175) ; adicionaFaturas(f176) ; adicionaFaturas(f177) ; adicionaFaturas(f178) ;
            adicionaFaturas(f179) ; adicionaFaturas(f180) ; adicionaFaturas(f181) ; adicionaFaturas(f182) ;
            adicionaFaturas(f183) ; adicionaFaturas(f184) ; adicionaFaturas(f185) ; adicionaFaturas(f186) ;
            adicionaFaturas(f187) ; adicionaFaturas(f188) ; adicionaFaturas(f189) ; adicionaFaturas(f190) ;
            adicionaFaturas(f191) ; adicionaFaturas(f192) ; adicionaFaturas(f193) ; adicionaFaturas(f194) ; 
            adicionaFaturas(f195) ; adicionaFaturas(f196) ; adicionaFaturas(f197) ; adicionaFaturas(f198) ;
            adicionaFaturas(f199) ; adicionaFaturas(f200) ;
        }catch(ContribuinteNaoExistenteException e){
            System.out.println("Contribuinte não existente");
        }
    }
}
