/**
 * Write a description of class Fatura here.
 *
 * @author Gonçalo Costeira
 * @author Rui Costa
 * @author Rui Azevedo
 * @version 00003 (modificado por: Rui)
 */

import java.time.LocalDate;
import java.lang.StringBuilder;
import java.io.Serializable;
import java.util.Stack;
import java.util.Iterator;

public class Fatura implements Serializable
{
   /**
    * Variáveis de instância
    */    
   private String codFatura;              // Código da fatura
   private String nif_emitente;           // nif do emitente da fatura;
   private String emitente;               // designação do emitente;
   private LocalDate data_despesa;    // data da despesa;
   private String nif_cliente;            // nif do cliente;
   private String descricao_despesa;      // descrição da empresa;
   private double valor_despesa;           // valor_despesa da despesa;
   private Stack atividade_economica;    // actividade económica há qual a despesa está associada.
   private boolean estadoFatura;          // Estado da fatura 
   
   /**
    * Método construtor não parametrizado 
    */
   public Fatura(){
       this.codFatura           = "";
       this.nif_emitente        = "";
       this.emitente            = "";
       this.data_despesa        = LocalDate.now();
       this.nif_cliente         = "";
       this.descricao_despesa   = "";
       this.valor_despesa       = 0;
       this.atividade_economica = new Stack();//""; 
       this.estadoFatura        = false;
   }
   
   /**
    * Método construtor parametrizado
    * 
    * @param long nif_emitente
    * @param String emitente
    * @param long nif_cliente
    * @param String descricao_despesa
    * @param float valor_despesa
    * @param String atividade_economica
    */
   public Fatura(String codFatura, String nif_emitente, String emitente,String nif_cliente,String descricao_despesa,
                 double valor_despesa, String atividade_economica){
       this.codFatura           = codFatura;
       this.nif_emitente        = nif_emitente;
       this.emitente            = emitente;
       this.data_despesa        = LocalDate.now();
       this.nif_cliente         = nif_cliente;
       this.descricao_despesa   = descricao_despesa;
       this.valor_despesa       = valor_despesa;
       this.atividade_economica = new Stack();
       this.atividade_economica.push(atividade_economica); 
       this.estadoFatura        = false;
   }
   
   public Fatura(String codFatura, String nif_emitente, String emitente, int m, int d, String nif_cliente,
                 String descricao_despesa, double valor_despesa, String atividade_economica){
       this.codFatura           = codFatura;
       this.nif_emitente        = nif_emitente;
       this.emitente            = emitente;
       this.data_despesa        = LocalDate.of(2018,m,d);
       this.nif_cliente         = nif_cliente;
       this.descricao_despesa   = descricao_despesa;
       this.valor_despesa       = valor_despesa;
       this.atividade_economica = new Stack();
       this.atividade_economica.push(atividade_economica);// = atividade_economica; 
       this.estadoFatura        = false;
   }
   
   /**
    * Método construtor por cópia
    * 
    * @param Fatura 
    */
   public Fatura(Fatura f){
       this.codFatura           = f.getCodFatura();
       this.nif_emitente        = f.getNifEmitente();
       this.emitente            = f.getEmitente();
       this.data_despesa        = f.getDataDespesa();
       this.nif_cliente         = f.getNifCliente();
       this.descricao_despesa   = f.getDescricaoDespesa();
       this.valor_despesa       = f.getValorDespesa();
       this.atividade_economica = f.getAtividadeEconomica();
       this.estadoFatura        = f.getEstadoFatura();
   }
   
   /* - Get's - */
   
   /**
    * Método que devolve o código da fatura
    */
   public String getCodFatura(){
       return this.codFatura;
   }
   
   /**
    * Método que devolve o número de identificação fiscal do emitente da fatura
    */
   public String getNifEmitente(){
       return this.nif_emitente;
   }
   
   /**
    * Método que devolve a designação do emitente da fatura
    */
   public String getEmitente(){
       return this.emitente;
   }
   
   /**
    * Método que devolve a data da despesa
    */
   public LocalDate getDataDespesa(){
       return this.data_despesa;
   }
   
   /**
    * Método que devolve o número de identificação fiscal do cliente
    */
   public String getNifCliente(){
       return this.nif_cliente;
   }
   
   /**
    * Método que devolve a descrição da despesa
    */
   public String getDescricaoDespesa(){
       return this.descricao_despesa;
   }
   
   /**
    * Método que devolve o valor_despesa da despesa
    */
   public double getValorDespesa(){
       return this.valor_despesa;
   }
   
   /**
    * Método que devolve a lista de atividades económicas associadas à fatura
    */
   public Stack getAtividadeEconomica(){
       Stack s = new Stack();
       Iterator it = this.atividade_economica.iterator();
       
       while(it.hasNext()){
           s.push(it.next());
       }
       return s;
   }
   
   /**
    * Método que devolve o rasto das actividades económicas
    */
   public String getRastoAtividades(){
       return this.atividade_economica.toString();
   }
   
   /**
    * Método que devolve a atividade económica associada à fatura
    */
   public String getCodigoAtividade(){
       return (String)this.atividade_economica.peek();
   }
   
   /**
    * Método que devolve o estado da fatura
    */
   public boolean getEstadoFatura(){
       return this.estadoFatura;
   }
   
   /* - Set's - */
   
   /**
    * Método que define o código da fatura
    * 
    * @param String codFatura
    */
   public void setCodFatura(String codFatura){
       this.codFatura = codFatura;
   }
   
   /**
    * Método que define o número de identificação fiscal do emitente da fatura
    * 
    * @param long nif_emitente
    */
   public void setNifEmitente(String nif_emitente){
       this.nif_emitente = nif_emitente;
   }
   
   /**
    * Método que define a designação do emitente da fatura
    * 
    * @param String emitente
    */
   public void setEmitente(String emitente){
       this.emitente = emitente;
   }
   
   /**
    * Método que define a data da despesa
    * 
    * @param LocalDate data_despesa
    */
   public void setDataDespesa(LocalDate data_despesa){
       this.data_despesa = data_despesa;
   }
   
   /**
    * Método que define o número de identificação fiscal do cliente 
    * 
    * @param long nif_cliente
    */
   public void setNifCliente(String nif_cliente){
       this.nif_cliente = nif_cliente;
   }
   
   /**
    * Método que define a descrição da despesa
    */
   public void setDescricaoDespesa(String descricao_despesa){
       this.descricao_despesa = descricao_despesa;
   }
   
   /**
    * Método que define o valor da despesa
    * 
    * @param float valor
    */
   public void setValorDespesa(float valor_despesa){
       this.valor_despesa = valor_despesa;
   }
   
   /**
    * Método que define a atividade económica à qual a despesa está associada
    * 
    * @param String atividade_economica
    */
   public void setAtividadeEconomica(String atividade_economica){
       this.atividade_economica.push(atividade_economica);
   }
   
   /**
    * Método que define o estado da fatura
    * 
    * @param boolean estadoFatura
    */
   public void setEstadoFatura(boolean estadoFatura){
       this.estadoFatura = estadoFatura;
   }
   
   /* - Métodos Necessários - */
   
   /**
    * Método que finaliza uma fatura
    */
   public void resolveFatura(){
       this.estadoFatura = true;
   }
   
   /**
    * Método que verifica se uma fatura e um Object são o mesmo
    * 
    * @param Object o
    */
   public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
       
       Fatura f = (Fatura) o;
       
       return this.nif_emitente == f.getNifEmitente()                    && this.emitente.equals(f.getEmitente())     &&
              this.data_despesa.equals(f.getDataDespesa())               && this.nif_cliente == f.getNifCliente()     &&
              this.descricao_despesa.equals(f.getDescricaoDespesa())     && this.valor_despesa == f.getValorDespesa() &&
              this.atividade_economica.equals(f.getAtividadeEconomica()) && this.codFatura.equals(f.getCodFatura())   &&
              this.estadoFatura == f.getEstadoFatura();
   }
   
   /**
    * Método que compara duas faturas
    * 
    * @param Fatura f
    */
   public long compareTo(Fatura f){
       return this.codFatura.compareTo(f.getCodFatura());
   }
   
   /**
    * Método que cria uma nova instância de Fatura com o mesmo valor das variáveis de instância
    */
   public Fatura clone(){
       return new Fatura(this);
   }
   
   /**
    * Método que converte uma fatura numa String
    */
   public String toString(){
       StringBuilder sb = new StringBuilder();
       
       sb.append("Código da Fatura: "        + this.codFatura                       + "\n");
       sb.append("NIF do emitente: "         + this.nif_emitente                    + "\n"); 
       sb.append("Descrição do Emitente: "   + this.emitente.toString()             + "\n");
       sb.append("Data da Despesa: "         + this.data_despesa.toString()         + "\n");
       sb.append("NIF do cliente: "          + this.nif_cliente                     + "\n");
       sb.append("Descrição da despesa: "    + this.descricao_despesa.toString()    + "\n");
       sb.append("Valor da Despesa: "        + this.valor_despesa                   + "\n");
       sb.append("Atividade Económica: "     + this.atividade_economica.peek()      + "\n");
       sb.append("Estado da fatura: "        + this.estadoFatura                    + "\n");
       
       return sb.toString();
   }
}