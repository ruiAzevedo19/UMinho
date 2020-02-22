using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ChefIt.Models;
using Microsoft.AspNetCore.Http;

namespace ChefIt.Controllers
{
    public class UsersController : Controller
    {
        private readonly ChefitContext _context;

        public UsersController(ChefitContext context)
        {
            _context = context;
        }

        /* --------------------- Métodos para registar utilizadores --------------------- */ 
        [HttpGet]
        public IActionResult RegisterUser()
        {
            return View();
        }

        [HttpPost]
        public IActionResult RegisterUser(User user)
        {
            try
            {
                user.Tipo = 'c';
                _context.Users.Add(user);
                _context.SaveChanges();
                ViewBag.Message = "Utilizador " + user.Username + " registado com sucesso!";
            }
            catch
            {
                ViewBag.Message = "Erro ao guardar o novo utilziador, por favor tente de novo!";
            }
            return View();
        }

        /* --------------------- Métodos para fazer autenticacao --------------------- */
        [HttpGet]
        public IActionResult Login()
        {
            if(Request.Cookies["LastLoggedTime"] != null)
                ViewBag.LastLogin = Request.Cookies["LastLoggedTime"].ToString();
            return View();
        }

        [HttpPost]
        public IActionResult Login(User user)
        {
            User loggedUser = _context.Users
                                      .Where(u => u.Username == user.Username && u.Password == user.Password)
                                      .FirstOrDefault();
            if (loggedUser == null)
            {
                ViewBag.Message = "Username ou Password incorretos, tente outra vez!";
                return View();
            }
            // guardar informacao do utilizador na sessao
            HttpContext.Session.SetString("Username", loggedUser.Username);
            HttpContext.Session.SetString("Tipo", loggedUser.Tipo.ToString());

            // guardar o ultimo loggin feito pelo utilizador
            Response.Cookies.Append("LastLoggedTime",DateTime.Now.ToString());

            return RedirectToAction("Dashboard");
        }

        /* 
         * Método que apos o login abre a view do utilizador
         */
        public IActionResult Dashboard()
        {
            if(HttpContext.Session.GetString("Username") == null)
            {
                return RedirectToAction("Login");
            }
            ViewBag.Username = HttpContext.Session.GetString("Username");
            ViewBag.Tipo = HttpContext.Session.GetString("Tipo");
            return View();
        }

        /*
         * Método que faz logout limpando a sessao
         */
        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }

        /* ------------------------------------------------------------------------ */

        // GET: Users
        public async Task<IActionResult> Index()
        {
            return View(await _context.Users.ToListAsync());
        }

        // GET: Users/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserID == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // GET: Users/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Users/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("UserID,Tipo,Username,Password,Nome,Email,DataNascimento,Biografia")] User user)
        {
            if (ModelState.IsValid)
            {
                _context.Add(user);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(user);
        }

        // GET: Users/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }
            return View(user);
        }

        // POST: Users/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("UserID,Tipo,Username,Password,Nome,Email,DataNascimento,Biografia")] User user)
        {
            if (id != user.UserID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(user);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExists(user.UserID))
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
            return View(user);
        }

        // GET: Users/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserID == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // POST: Users/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var user = await _context.Users.FindAsync(id);
            _context.Users.Remove(user);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool UserExists(int id)
        {
            return _context.Users.Any(e => e.UserID == id);
        }
    }
}
