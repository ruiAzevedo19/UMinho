using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ChefIt.Models
{
    public class ChefitContext : DbContext
    {
        public ChefitContext(DbContextOptions<ChefitContext> options) : base(options) { }

        public DbSet<User> Users       { get; set; }
        public DbSet<Receita> Receitas { get; set; }
        public DbSet<Ingrediente>Ingredientes { get; set; }
        public DbSet<Passo> Passos     { get; set; }
        public DbSet<ReceitaIngrediente> ReceitaIngredientes { get; set; }
        public DbSet<ReceitaIngredientePasso> ReceitaIngredientePassos { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<ReceitaIngredientePasso>()
                        .HasKey(rip => new { rip.ReceitaID , rip.IngredienteID, rip.PassoID });
            modelBuilder.Entity<ReceitaIngrediente>()
                        .HasKey(ri => new { ri.ReceitaID, ri.IngredienteID });
        }
    }    
}
