using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class Ingrediente
    {
        [Key]
        public int IngredienteID   { get; set; }
        /* ---------------------------------- */
        [Required] [StringLength(80)]
        public string Nome         { get; set; }
        /* ---------------------------------- */
        public float Calorias      { get; set; }
        /* ---------------------------------- */
        public virtual ICollection<ReceitaIngrediente> ReceitaIngredientes { get; set; }
        public virtual ICollection<ReceitaIngredientePasso> ReceitaIngredientePassos { get; set; }
    }
}
