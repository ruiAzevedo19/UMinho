using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class ReceitaIngrediente
    {
        public int ReceitaID           { get; set; }
        public virtual Receita Receita { get; set; }
        /* -------------------------------------- */
        public int IngredienteID       { get; set; }
        public virtual Ingrediente Ingrediente
                                       { get; set; }
        /* -------------------------------------- */
        public string Quantidade       { get; set; }
        /* -------------------------------------- */
    }
}
