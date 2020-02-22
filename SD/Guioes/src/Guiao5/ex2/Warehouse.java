package Guiao5.ex2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private Map<String,Item> stock;     /** Estrutura que guarda os artigos **/

    /**
     * Metodo construtor
     */
    public Warehouse(){
        this.stock = new HashMap<>();
    }

    /**
     * Adiciona um artigo
     *
     * @param item : artigo a inserir
     * @param stock : stock do artigo a inserir
     */
    public void addItem(String item, int stock){
        this.stock.put(item, new Item(stock));
    }

    /**
     * Devolve o stock de um artigo
     *
     * @param item : artigo a procurar
     * @return
     */
    public int getStock(String item){
        return this.stock.get(item).getStock();
    }

    /**
     * Adiciona stock a um artigo
     *
     * @param item : artigo a adicioanr stock
     * @param quantity : quantidade de stock a adicionar
     */
    public void supply(String item, int quantity){
        Item it = this.stock.get(item);

        if( it != null )
            it.supply(quantity);
    }

    /**
     * Remove stock de um conjunto de artigos
     *
     * @param items : lista de artigos
     * @param quantity : quantidade a remover
     * @throws InterruptedException
     */
    public void consume(List<String> items, int quantity) throws InterruptedException {
        Item item;
        for(String it : items){
            item = this.stock.get(it);
            if( item != null)
                item.consume(quantity);
        }
    }
}
