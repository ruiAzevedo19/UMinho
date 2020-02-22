using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class Passo
    {
        [Key]
        public int PassoID      { get; set; }
        /* ------------------------------- */
        [Required] [StringLength(500)]
        public string Descricao { get; set; }
        /* ------------------------------- */
        [StringLength(50)]
        public string Acao      { get; set; }
        /* ------------------------------- */
        [Required]
        public int NrPasso      { get; set; }
        /* ------------------------------- */
        // Relacionamentos
        public virtual ICollection<ReceitaIngredientePasso> ReceitaIngredientePassos { get; set; }
    }
}
