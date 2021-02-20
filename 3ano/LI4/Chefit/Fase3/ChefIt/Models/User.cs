using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    [Table("Users")]
    public class User
    {
        [Key]
        public int UserID              { get; set; }
        /* -------------------------------------- */
        [Required]
        public char Tipo               { get; set; }
        /* -------------------------------------- */
        [Required] [StringLength(30)]
        public string Username         { get; set; }
        /* -------------------------------------- */
        [Required] [StringLength(50)]
        public string Password         { get; set; }
        /* -------------------------------------- */
        [Required] [StringLength(80)]
        public string Nome             { get; set; }
        /* -------------------------------------- */
        [Required] [DataType(DataType.EmailAddress)]
        public string Email            { get; set; }
        /* -------------------------------------- */
        [DataType(DataType.Date)]
        [DisplayName("Data de Nascimento")]
        public DateTime DataNascimento { get; set; }
        /* -------------------------------------- */
        [StringLength(500)]
        public string Biografia        { get; set; }
        /* -------------------------------------- */
        public virtual ICollection<Receita> Receitas { get; set; }

    }
}
