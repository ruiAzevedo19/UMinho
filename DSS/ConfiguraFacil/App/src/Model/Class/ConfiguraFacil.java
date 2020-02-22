package Model.Class;

import Model.DAOs.*;

import java.util.*;
import java.util.stream.Collectors;

public class ConfiguraFacil {

    String user;
    private ComponenteDAO componentes;
    private UserDAO  users;
    private PacoteDAO pacotes;
    private ModeloDAO modelos;
    private EncomendaDAO encomendas;
    private ClienteDAO clientes;
    private Configuracao config;

    public ConfiguraFacil() {
        this.componentes = new ComponenteDAO();
        this.users = new UserDAO();
        this.pacotes = new PacoteDAO();
        this.modelos = new ModeloDAO();
        this.encomendas = new EncomendaDAO();
        this.clientes = new ClienteDAO();
        this.config = new Configuracao();
    }

    public int login(String user, String password) {
        int res = 0;
        User u = this.users.get(user);
        if (u != null && u.getPassword().equals(password)) {
            if (u.getTipo().equals("Admin"))
                res = 1;
            if (u.getTipo().equals("FuncionarioFabril"))
                res = 2;
            if (u.getTipo().equals("FuncionarioLoja"))
                res = 3;
        }
        return res;
    }

    // -- Users --

    public String getUser() {
        return this.user;
    }

    public void setUser(String us) {
        User u = this.users.get(us);
        this.user = u.getNif();
    }

    public List<User> getUsers() throws Exception {
        return users.list();
    }

    public void addUser(String nif, String nome, String username, String password, String contacto, String tipo){
        User u = new User(nif,nome,username,password,contacto,tipo);
        this.users.put(u.getNif(),u.clone());
    }

    public void removeUser(String nif){
        this.users.remove(nif);
    }

    public boolean updateUsers(List<User> lista){
        return users.setUsers(lista);
    }

    // -- Componentes --

    public List<Componente> getComponentes() throws Exception {
        return componentes.list();
    }

    public Componente getComponente(Object key){
        return componentes.get(key);
    }

    public boolean updateComponentes(List<Componente> lista){
        return componentes.setComponentes(lista);
    }

    public String selecionaCompExtra(String codigo){
        Componente c = componentes.get(codigo);
        StringBuilder sb = new StringBuilder();
        String comp;

        List<String> obr = componentes.getObrInc(c.getCodigo(),"compObrigatorio");
        List<String> inc = componentes.getObrInc(c.getCodigo(),"compIncompativel");

        if(obr.isEmpty() == false){
            sb.append("O componente " + c.getDescricao() + " obriga a ter os compontes: \n");
            for(String cod : obr){
                comp = componentes.get(cod).getDescricao();
                sb.append("\t" + comp + "\n");
            }
        }

        if(inc.isEmpty() == false){
            sb.append("O componente " + c.getDescricao() + " é incompatível com: \n");
            for(String cod : inc){
                comp = componentes.get(cod).getDescricao();
                sb.append("\t" + comp + "\n");
            }
        }
        return sb.toString();
    }

    public void seleccionaComponente (Componente comp) throws Exception{
        StringBuilder sb = new StringBuilder();
        String tipo = comp.getTipo();
        if (tipo.equals("Motor") || tipo.equals("Transmissao") || tipo.equals("Jantes") || tipo.equals("Estofos")){
            getConfig().removeCategoria(tipo);
            getConfig().getComponentes().add(comp);
        }
        else {
            for (Componente c : getConfig().getComponentes()) {
                for (String inc : c.getIncompativeis()) {
                    if (comp.getCodigo().equals(inc)) sb.append("O componente da sua configuração " + c.getDescricao() + " é incompativel com " + comp.getDescricao() + "\n");
                }
            }
            for (String obr : comp.getObrigatorios()) {
                if (!getConfig().containsComponente(obr)) {
                    sb.append("O componente " + getComponente(obr).getDescricao() + " é obrigatorio.\n");
                }
            }
            if(this.getConfig().getComponentes().contains(comp)){
                sb.append("Componente já existe!");
                throw new Exception(sb.toString());
            }
            if (!sb.toString().isEmpty()) throw new Exception(sb.toString());
        }
        this.getConfig().actualizaPreco();
    }

    public String validaConfiguracao() {
        List<Componente> compConf = this.config.getComponentes();
        List<String> res = new ArrayList<>();
        List<String> codConf = compConf.stream().map(Componente::getCodigo).collect(Collectors.toList());
        for (Componente c : compConf) {
            List<String> cods = c.getObrigatorios();
            for (String cod : cods)
                if (codConf.contains(cod) == false)
                    res.add(cod);
        }
        StringBuilder sb = new StringBuilder();
        String comp;
        if (res.isEmpty() == false) {
            sb.append("Tem que adicioanr os seguintes componentes: \n");
            for (String codigo : res) {
                comp = this.componentes.get(codigo).getDescricao();
                sb.append("\t" + comp + "\n");
            }
        }
        return sb.toString();
    }

    public List<Modelo> getModelos() throws Exception {
        return modelos.list();
    }

    public List<Pacote> getPacotes() throws Exception {
        return pacotes.list();
    }

    public List<Encomenda> getEncomendas() throws Exception {
        return encomendas.list();
    }

    public Configuracao getConfig() {
        return this.config;
    }

    // -- Encomenda e cliente --
    public void addCliente(String n, String nif, String c, String m){
        Cliente cliente = new Cliente(n, nif, c, m);
        this.clientes.put(cliente);
    }

    public String matchPacote(List<Componente> config) {
        List<Pacote> lista = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            lista = this.getPacotes();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (Pacote p : lista) {
            if(convertList(config).containsAll(p.getComponentes()) /*&& !getConfig().containsPacote(p.getCod())*/){
                sb.append("Os componentes perfazem o pacote " + p.getDesignacao());
            }
        }
        return sb.toString();
    }

    public List<String> convertList(List<Componente> componentes){
        List<String> lista = new ArrayList<>();
        for(Componente c: componentes){
            lista.add(c.getCodigo());
        }
        return lista;
    }

    public void seleccionaPacote (String key) throws Exception {
        List<String> comp = pacotes.getCompPack(key);
        for (String i : comp) {
            Componente c = componentes.get(i);
            this.config.getComponentes().add(c);
        }
        double p = this.config.getPreco();
        Pacote pk = this.pacotes.get(key);
        this.config.setPreco(p * pk.getDesconto());
    }

    public void removePacote (String key){
        List<String> comp = pacotes.getCompPack(key);
        for (String i : comp) {
            this.config.removeComponente(i);
        }
    }

    public void addEncomenda(String modelo, double preco, String metodo, String vendedor, String cliente, List<String> comp){
        Encomenda e = new Encomenda(modelo, preco, metodo, vendedor, cliente, comp);
        int x = this.encomendas.lastEntry() + 1;
        System.out.println("Encomenda actual: " + x);
        e.setCodigo(Integer.toString(x));
        List<String> c = config.getComponentes().stream().map(Componente::getCodigo).collect(Collectors.toList());
        e.setComponentes(c);
        this.encomendas.put(e);
    }

    public List<String> getPacoteComp (int key) {
        return this.pacotes.getCompPack(key);
    }
}
