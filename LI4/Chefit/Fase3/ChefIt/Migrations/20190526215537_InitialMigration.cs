using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ChefIt.Migrations
{
    public partial class InitialMigration : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Ingredientes",
                columns: table => new
                {
                    IngredienteID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Nome = table.Column<string>(maxLength: 80, nullable: false),
                    Calorias = table.Column<float>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Ingredientes", x => x.IngredienteID);
                });

            migrationBuilder.CreateTable(
                name: "Passos",
                columns: table => new
                {
                    PassoID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Descricao = table.Column<string>(maxLength: 500, nullable: false),
                    Acao = table.Column<string>(maxLength: 50, nullable: true),
                    NrPasso = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Passos", x => x.PassoID);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    UserID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Tipo = table.Column<string>(nullable: false),
                    Username = table.Column<string>(maxLength: 30, nullable: false),
                    Password = table.Column<string>(maxLength: 50, nullable: false),
                    Nome = table.Column<string>(maxLength: 80, nullable: false),
                    Email = table.Column<string>(nullable: false),
                    DataNascimento = table.Column<DateTime>(nullable: false),
                    Biografia = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.UserID);
                });

            migrationBuilder.CreateTable(
                name: "Receitas",
                columns: table => new
                {
                    ReceitaID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Nome = table.Column<string>(maxLength: 100, nullable: false),
                    Descricao = table.Column<string>(maxLength: 500, nullable: true),
                    Preparacao = table.Column<int>(nullable: false),
                    Duracao = table.Column<int>(nullable: false),
                    Dose = table.Column<float>(nullable: false),
                    Dificuldade = table.Column<int>(nullable: false),
                    Classificacao = table.Column<float>(nullable: false),
                    Estado = table.Column<string>(nullable: false),
                    UserID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Receitas", x => x.ReceitaID);
                    table.ForeignKey(
                        name: "FK_Receitas_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "UserID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ReceitaIngredientePassos",
                columns: table => new
                {
                    ReceitaID = table.Column<int>(nullable: false),
                    IngredienteID = table.Column<int>(nullable: false),
                    PassoID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReceitaIngredientePassos", x => new { x.ReceitaID, x.IngredienteID, x.PassoID });
                    table.ForeignKey(
                        name: "FK_ReceitaIngredientePassos_Ingredientes_IngredienteID",
                        column: x => x.IngredienteID,
                        principalTable: "Ingredientes",
                        principalColumn: "IngredienteID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ReceitaIngredientePassos_Passos_PassoID",
                        column: x => x.PassoID,
                        principalTable: "Passos",
                        principalColumn: "PassoID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ReceitaIngredientePassos_Receitas_ReceitaID",
                        column: x => x.ReceitaID,
                        principalTable: "Receitas",
                        principalColumn: "ReceitaID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ReceitaIngredientes",
                columns: table => new
                {
                    ReceitaID = table.Column<int>(nullable: false),
                    IngredienteID = table.Column<int>(nullable: false),
                    Quantidade = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReceitaIngredientes", x => new { x.ReceitaID, x.IngredienteID });
                    table.ForeignKey(
                        name: "FK_ReceitaIngredientes_Ingredientes_IngredienteID",
                        column: x => x.IngredienteID,
                        principalTable: "Ingredientes",
                        principalColumn: "IngredienteID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ReceitaIngredientes_Receitas_ReceitaID",
                        column: x => x.ReceitaID,
                        principalTable: "Receitas",
                        principalColumn: "ReceitaID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ReceitaIngredientePassos_IngredienteID",
                table: "ReceitaIngredientePassos",
                column: "IngredienteID");

            migrationBuilder.CreateIndex(
                name: "IX_ReceitaIngredientePassos_PassoID",
                table: "ReceitaIngredientePassos",
                column: "PassoID");

            migrationBuilder.CreateIndex(
                name: "IX_ReceitaIngredientes_IngredienteID",
                table: "ReceitaIngredientes",
                column: "IngredienteID");

            migrationBuilder.CreateIndex(
                name: "IX_Receitas_UserID",
                table: "Receitas",
                column: "UserID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ReceitaIngredientePassos");

            migrationBuilder.DropTable(
                name: "ReceitaIngredientes");

            migrationBuilder.DropTable(
                name: "Passos");

            migrationBuilder.DropTable(
                name: "Ingredientes");

            migrationBuilder.DropTable(
                name: "Receitas");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
