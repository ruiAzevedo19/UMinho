using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class Restricao
    {
        [Key]
        public int RestricaoID  { get; set; }
        /* ------------------------------- */
        [Required] [StringLength(100)]
        public string Descricao { get; set; }
        /* --------------------------------*/
        [Required] [StringLength(100)]
        public string Acao      { get; set; }
        /* ------------------------------- */
    }
}
