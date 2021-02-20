using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class Utensilio
    {
        [Key]
        public int UtensilioID { get; set; }
        /* ------------------------------ */
        [Required] [StringLength(50)]
        public string Nome;
        /* ------------------------------ */
    }
}
