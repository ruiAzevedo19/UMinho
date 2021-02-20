package trazAqui;

import java.io.Serializable;

public class EncomendasAceites implements Serializable {

  /* STARTING functions */

  private String codEncomenda;

  public EncomendasAceites() {
    this.codEncomenda = new String();
  }

  public EncomendasAceites(String codEncomenda) {
    this.codEncomenda = codEncomenda;
  }

  public EncomendasAceites(EncomendasAceites ea) {
    this.codEncomenda = ea.getCodEncomenda();
  }


  /* GET functions */

  public String getCodEncomenda() {
    return this.codEncomenda;
  }


  /* SET functions */

  public void setCodEncomenda(String newCodEncomenda) {
    this.codEncomenda = newCodEncomenda;
  }


  /* OTHER functions */

  public EncomendasAceites clone() {
    return new EncomendasAceites(this);
  }

  public boolean equals(Object obj){
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
			return false;
			
		EncomendasAceites e = (EncomendasAceites) obj;

		return e.getCodEncomenda().equals(this.codEncomenda);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Código de encomenda: ").append(this.codEncomenda).append("\n");

		return sb.toString();
  }
  
  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Aceite:");
    sb.append(this.codEncomenda);

    return sb.toString();
  }
}