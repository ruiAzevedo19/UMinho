package model;

import controller.Controller;
import controller.LojaLogin;
import controller.TransportadoraLogin;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SistemaGestaoVendas implements PropertyChangeListener, Serializable {
    private final Set<Voluntario> voluntarios;
    private final Set<Utilizador> utilizadores;
    private final Set<Transportadora> transportadoras;
    private final Set<Loja> lojas;
    private final Set<Encomenda> encomendas;

    private final Set<Voluntario> voluntariosDisponiveis;
    private final Set<Transportadora> transportadorasDisponiveis;

    private final Set<Utilizador> comprasporUtilizador;
    private final Set<Transportadora> kmporTransportadora;

    private SistemaGestaoVendas() {
        this.voluntarios     = new TreeSet<>();
        this.utilizadores    = new TreeSet<>();
        this.transportadoras = new TreeSet<>();
        this.lojas           = new TreeSet<>();
        this.encomendas      = new TreeSet<>();

        this.transportadorasDisponiveis = new TreeSet<>();
        this.voluntariosDisponiveis = new TreeSet<>();

        this.comprasporUtilizador = new HashSet<>();
        this.kmporTransportadora = new HashSet<>();
    }

    public static SistemaGestaoVendas fromLogFile(String path) throws IOException {



            SistemaGestaoVendas sgv = new SistemaGestaoVendas();
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
        try{

            String line = reader.readLine();
            while (!line.equals("Dados de LOGS:")) // Encontra a linha com o indicador que os logs vao começar
                line = reader.readLine();

            String x=reader.readLine(); // Ignora as proximas duas linhas
            x=reader.readLine();

            System.out.println(x);

            // Ler os logs
            line = reader.readLine();
            while (line != null) {
                String[] args = line.split(",");
                String type = line.split(":")[0];
                switch (type) {
                    case "Utilizador":
                        Utilizador u = Utilizador.fromArgs(args);
                        if (u != null)
                            sgv.utilizadores.add(u);
                        sgv.utilizadores.add(u);
                        break;
                    case "Voluntario":
                        Voluntario v = Voluntario.fromArgs(args);
                        if (v != null)
                            sgv.voluntarios.add(v);
                        break;
                    case "Transportadora":
                        Transportadora t = Transportadora.fromArgs(args);
                        if (t != null) {
                            sgv.transportadoras.add(t);
                            sgv.kmporTransportadora.add(t);
                        }
                        break;
                    case "Loja":
                        Loja l = new Loja().fromArgs(args);
                        if (l != null)
                            sgv.lojas.add(l);
                        break;
                    case "Encomenda":
                        Encomenda e = Encomenda.fromArgs(args);
                        if (e != null)
                            sgv.encomendas.add(e);
                        break;
                    case "Aceite:":
                        String codEncomendaToAccept = args[0].split(":")[1];
                        sgv.acceptEncomenda(codEncomendaToAccept);
                        break;

                    default:
                        break;
                }
                line = reader.readLine();
            }



        }


        catch (IOException e){
            Logger logger = Logger.getLogger(Controller.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }


        finally {

            reader.close();

        }

        return sgv;

    }

    private void acceptEncomenda(String codEncomendaToAccept) {
        this.encomendas.stream()
                .filter(a -> a.getCodEncomenda().equals(codEncomendaToAccept))
                .findFirst()
                .ifPresent(a -> a.setAccepted(true));
    }

    public Set<Voluntario> getVoluntarios() {
        return voluntarios;
    }

    public Voluntario getSpecificVoluntario(String codVoluntario) {
        for (Voluntario a : this.voluntarios) {
            if (a.getCodigo().equals(codVoluntario))
                return a;
        }
        return null;
    }

    public Loja getSpecificLoja (String codLoja) {
        for (Loja a : this.lojas) {
            if (a.getCodigo().equals(codLoja))
                return a;
        }
        return null;
    }

    public Utilizador getSpecificUtilizador (String codUtilizador) {
        for (Utilizador a : this.utilizadores) {
            if (a.getCodigo().equals(codUtilizador))
                return a;
        }
        return null;
    }

    public Transportadora getSpecificTransportadora (String codTransportadora) {
        for (Transportadora a : this.transportadoras) {
            if (a.getCodigo().equals(codTransportadora))
                return a;
        }
        return null;
    }

    public Set<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public Set<Transportadora> getTransportadoras() {
        return transportadoras;
    }

    public Set<Loja> getLojas() {
        return lojas;
    }

    @Override
    public String toString() {
        return "SistemaGestaoVendas{" +
                "voluntarios=" + voluntarios +
                ", utilizadores=" + utilizadores +
                ", transportadoras=" + transportadoras +
                ", lojas=" + lojas +
                ", encomendas=" + encomendas +
                '}';
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        // Detect transportatdora_set_disponibilidade
        if (pce.getPropertyName().equals("transportadora_set_disponibilidade")) {
            TransportadoraLogin source = (TransportadoraLogin) pce.getSource();
            String codigo = source.getUsername();
            Boolean disponivel = (Boolean) pce.getNewValue();
            if (Boolean.TRUE.equals(disponivel)) {
                this.transportadoras.stream()
                        .filter(t -> t.getCodigo().equals(codigo))
                        .findFirst()
                        .ifPresent(this.transportadorasDisponiveis::add);
            } else {
                this.transportadorasDisponiveis.stream()
                        .filter(t -> t.getCodigo().equals(codigo))
                        .findFirst()
                        .ifPresent(this.transportadorasDisponiveis::remove);
            }
        }
        if (pce.getPropertyName().equals("voluntario_set_disponibilidade")) {
            LojaLogin source = (LojaLogin) pce.getSource();
            String codigo = source.getUsername();
            Boolean disponivel = (Boolean) pce.getNewValue();
            if (Boolean.TRUE.equals(disponivel)) {
                this.voluntarios.stream()
                        .filter(t -> t.getCodigo().equals(codigo))
                        .findFirst()
                        .ifPresent(this.voluntariosDisponiveis::add);
            } else {
                this.voluntariosDisponiveis.stream()
                        .filter(t -> t.getCodigo().equals(codigo))
                        .findFirst()
                        .ifPresent(this.voluntariosDisponiveis::remove);
            }
        }
    }

    public Set<Transportadora> getTransportadorasDisponiveis() {
        return new HashSet<>(this.transportadorasDisponiveis);
    }

    public String top10Utilizadores() {
        if (this.comprasporUtilizador.isEmpty())
            return "Nao ha compras registadas";

        List<Utilizador> top10 = this.comprasporUtilizador.stream()
                .sorted((a, b) -> b.getHistorico().size() - a.getHistorico().size())
                .limit(10)
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < top10.size(); i++) {
            Utilizador u = top10.get(i);
            int quant = top10.get(i).getHistorico().size();
            builder.append("#").append(i+1).append(" => ").append(u.getCodigo()).append(" - ").append(quant).append("\n");
        }
        return builder.toString();
    }

    public String top10Transportadoras() {
        if (this.kmporTransportadora.isEmpty())
            return "Nao ha compras registadas";

         List<Transportadora> top10 = this.kmporTransportadora.stream()
                 .sorted((a,b) -> b.getKmPercorridos().compareTo(a.getKmPercorridos()))
                 .limit(10)
                 .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < top10.size(); i++) {
            Transportadora t = top10.get(i);
            double quant = top10.get(i).getKmPercorridos();
            builder.append("#").append(i+1).append(" => ").append(t.getCodigo()).append(" - ").append(quant).append("\n");
        }

        return builder.toString();
    }
}
