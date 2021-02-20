using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ChefIt.Models;

namespace ChefIt.Controllers
{
    public class ReceitasController : Controller
    {
        private readonly ChefitContext _context;

        public ReceitasController(ChefitContext context)
        {
            _context = context;
        }

        /* ----------------------------------------------------------------------------------------------- */

        public IActionResult Receitas()
        {
            return RedirectToAction("Index");
        }

        public IActionResult PrepararReceita(int id)
        {
            ReceitaViewModel RT_Receita = getReceitaId(id);
            return View("Passo", RT_Receita);
        }

        public ReceitaViewModel getReceitaId(int id)
        {
            var receita = _context.Receitas.Find(id);
            ReceitaViewModel RT_Receita = new ReceitaViewModel();
            // Variaveis de instancia
            RT_Receita.ReceitaID = receita.ReceitaID;
            RT_Receita.Nome = receita.Nome;
            RT_Receita.Descricao = receita.Descricao;
            RT_Receita.Preparacao = receita.Preparacao;
            RT_Receita.Duracao = receita.Duracao;
            RT_Receita.Dose = receita.Dose;
            RT_Receita.Dificuldade = receita.Dificuldade;
            RT_Receita.Classificacao = receita.Classificacao;
            RT_Receita.PassoIngredientes = new Dictionary<Passo, List<Ingrediente>>();
            RT_Receita.Ingredientes = new Dictionary<Ingrediente, string>();
            // Passos de uma receita
            var passos = _context.ReceitaIngredientePassos
                        .Where(passo => passo.ReceitaID == id)//receitaID
                        .ToList();
            foreach (var rip in passos)
            {
                Passo p = _context.Passos.Find(rip.PassoID);
                Ingrediente i = _context.Ingredientes.Where(t => t.IngredienteID == rip.IngredienteID)
                                             .Single();

                if (RT_Receita.PassoIngredientes.ContainsKey(p))
                {
                    RT_Receita.PassoIngredientes[p].Add(i);
                }
                else
                {
                    List<Ingrediente> temp = new List<Ingrediente>();
                    temp.Add(i);
                    RT_Receita.PassoIngredientes.Add(p, temp);
                }
            }
            RT_Receita.PassoIngredientes = RT_Receita.PassoIngredientes
                                           .OrderBy(z => z.Key.NrPasso)
                                           .ToDictionary(p => (Passo)p.Key, l => (List<Ingrediente>)l.Value);

            var ingredientes_tuple = _context.ReceitaIngredientes.Where(i => i.ReceitaID == id);
            foreach(var ingr in ingredientes_tuple)
            {
                Ingrediente ing = _context.Ingredientes.Where(i => i.IngredienteID == ingr.IngredienteID).Single();
                var quantidade = ingr.Quantidade;
                RT_Receita.Ingredientes.Add(ing, quantidade);
            }
            return  RT_Receita;
        }


        public IActionResult ApresentaReceita(int id)
        {

            ReceitaViewModel RT_Receita = getReceitaId(id);
           
            return View("Receita", RT_Receita);
        }

        /* ----------------------------------------------------------------------------------------------- */

        // GET: Receitas
        public async Task<IActionResult> Index()
        {
            return View(await _context.Receitas.ToListAsync());
        }

        // GET: Receitas/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var receita = await _context.Receitas
                .Include(r => r.User)
                .FirstOrDefaultAsync(m => m.ReceitaID == id);
            if (receita == null)
            {
                return NotFound();
            }

            return View(receita);
        }

        // GET: Receitas/Create
        public IActionResult Create()
        {
            ViewData["UserID"] = new SelectList(_context.Users, "UserID", "Email");
            return View();
        }

        // POST: Receitas/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ReceitaID,Nome,Descricao,Preparacao,Duracao,Dose,Dificuldade,Classificacao,Estado,UserID")] Receita receita)
        {
            if (ModelState.IsValid)
            {
                _context.Add(receita);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["UserID"] = new SelectList(_context.Users, "UserID", "Email", receita.UserID);
            return View(receita);
        }

        // GET: Receitas/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var receita = await _context.Receitas.FindAsync(id);
            if (receita == null)
            {
                return NotFound();
            }
            ViewData["UserID"] = new SelectList(_context.Users, "UserID", "Email", receita.UserID);
            return View(receita);
        }

        // POST: Receitas/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("ReceitaID,Nome,Descricao,Preparacao,Duracao,Dose,Dificuldade,Classificacao,Estado,UserID")] Receita receita)
        {
            if (id != receita.ReceitaID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(receita);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ReceitaExists(receita.ReceitaID))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["UserID"] = new SelectList(_context.Users, "UserID", "Email", receita.UserID);
            return View(receita);
        }

        // GET: Receitas/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var receita = await _context.Receitas
                .Include(r => r.User)
                .FirstOrDefaultAsync(m => m.ReceitaID == id);
            if (receita == null)
            {
                return NotFound();
            }

            return View(receita);
        }

        // POST: Receitas/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var receita = await _context.Receitas.FindAsync(id);
            _context.Receitas.Remove(receita);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ReceitaExists(int id)
        {
            return _context.Receitas.Any(e => e.ReceitaID == id);
        }
    }
}
