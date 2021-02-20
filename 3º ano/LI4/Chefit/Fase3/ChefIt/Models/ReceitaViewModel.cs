using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class ReceitaViewModel
    {
        public int ReceitaID { get; set; }
        /* -------------------------------- */
        public string Nome { get; set; }
        /* -------------------------------- */
        public string Descricao { get; set; }
        /* -------------------------------- */
        public int Preparacao { get; set; }
        /* -------------------------------- */
        public int Duracao { get; set; }
        /* -------------------------------- */
        public float Dose { get; set; }
        /* -------------------------------- */
        public int Dificuldade { get; set; }
        /* -------------------------------- */
        public float Classificacao { get; set; }
        /* -------------------------------- */
        public Dictionary<Passo,List<Ingrediente>> PassoIngredientes { get; set; }
        /* -------------------------------- */
        public Dictionary<Ingrediente,string> Ingredientes { get; set; }
    }
}
