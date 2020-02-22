using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class Receita
    {
        [Key]
        public int ReceitaID     { get; set; }
        /* -------------------------------- */
        [Required] [StringLength(100)]
        public string Nome       { get; set; }
        /* -------------------------------- */
        [StringLength(500)]
        public string Descricao  { get; set; }
        /* -------------------------------- */
        public int Preparacao    { get; set; }
        /* -------------------------------- */
        [Required]
        public int Duracao       { get; set; }
        /* -------------------------------- */
        public float Dose        { get; set; }
        /* -------------------------------- */
        [Required] [Range(0,5)]
        public int Dificuldade   { get; set; }
        /* -------------------------------- */
        [Range(0,5)]
        public float Classificacao { get; set; }
        /* -------------------------------- */
        [Required] 
        public char Estado       { get; set; }
        /* -------------------------------- */
        // Relacionamentos
        public int UserID        { get; set; }
        public virtual User User { get; set; }
        public virtual ICollection<ReceitaIngrediente> ReceitaIngredientes { get; set; }
        public virtual ICollection<ReceitaIngredientePasso> ReceitaIngredientePassos { get; set; }
    }
}
