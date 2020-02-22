using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class ReceitaIngredientePasso
    {
        public int ReceitaID          { get; set; }
        public Receita Receita        { get; set; }
        /* -------------------------------------- */
        public int IngredienteID       { get; set; }
        public Ingrediente Ingrediente { get; set; }
        /* -------------------------------------- */
        public int PassoID             { get; set; }
        public Passo Passo             { get; set; }
        /* -------------------------------------- */
    }
}
