#pragma checksum "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "533b2a47ed15d5219b148088fc488307f31b75ab"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Receitas_Index), @"mvc.1.0.view", @"/Views/Receitas/Index.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Receitas/Index.cshtml", typeof(AspNetCore.Views_Receitas_Index))]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#line 1 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\_ViewImports.cshtml"
using ChefIt;

#line default
#line hidden
#line 2 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\_ViewImports.cshtml"
using ChefIt.Models;

#line default
#line hidden
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"533b2a47ed15d5219b148088fc488307f31b75ab", @"/Views/Receitas/Index.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"2ebcf41b0b1bc2bb1ae405e19d5462873069dcce", @"/Views/_ViewImports.cshtml")]
    public class Views_Receitas_Index : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<IEnumerable<ChefIt.Models.Receita>>
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("src", new global::Microsoft.AspNetCore.Html.HtmlString("~/images/sopamilho.jpg"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_1 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("style", new global::Microsoft.AspNetCore.Html.HtmlString("width:100%;height:100%"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        #line hidden
        #pragma warning disable 0169
        private string __tagHelperStringValueBuffer;
        #pragma warning restore 0169
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperExecutionContext __tagHelperExecutionContext;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner __tagHelperRunner = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner();
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __backed__tagHelperScopeManager = null;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __tagHelperScopeManager
        {
            get
            {
                if (__backed__tagHelperScopeManager == null)
                {
                    __backed__tagHelperScopeManager = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager(StartTagHelperWritingScope, EndTagHelperWritingScope);
                }
                return __backed__tagHelperScopeManager;
            }
        }
        private global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(43, 2, true);
            WriteLiteral("\r\n");
            EndContext();
#line 3 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
  
    ViewData["Title"] = "Index";

#line default
#line hidden
            BeginContext(86, 43, true);
            WriteLiteral("<hr />\r\n<h2>Receitas</h2>\r\n<hr />\r\n<br />\r\n");
            EndContext();
#line 10 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
 for (var i = 0; i < 3; i++)
{
    

#line default
#line hidden
#line 12 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
     foreach (var receita in Model)
    {

#line default
#line hidden
            BeginContext(206, 12, true);
            WriteLiteral("        <div");
            EndContext();
            BeginWriteAttribute("id", " id=\"", 218, "\"", 249, 2);
            WriteAttributeValue("", 223, "receita-", 223, 8, true);
#line 14 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
WriteAttributeValue("", 231, receita.ReceitaID, 231, 18, false);

#line default
#line hidden
            EndWriteAttribute();
            BeginContext(250, 121, true);
            WriteLiteral(" style=\"overflow:-webkit-paged-x; border-style: solid;border-width: 1px;border-color: #eeeeee\" class=\"=row linha-receita\"");
            EndContext();
            BeginWriteAttribute("onclick", " onclick=\"", 371, "\"", 472, 3);
            WriteAttributeValue("", 381, "location.href=\'", 381, 15, true);
#line 14 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
WriteAttributeValue("", 396, Url.Action("ApresentaReceita", "Receitas", new { id = receita.ReceitaID }), 396, 75, false);

#line default
#line hidden
            WriteAttributeValue("", 471, "\'", 471, 1, true);
            EndWriteAttribute();
            BeginContext(473, 115, true);
            WriteLiteral(">\r\n            <div class=\"col-md-3 imagem-receita\" style=\"margin-top: 35px; margin-left: 20px;\">\r\n                ");
            EndContext();
            BeginContext(588, 67, false);
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("img", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.SelfClosing, "4e806312a375486bb706d0898f4189cb", async() => {
            }
            );
            __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_0);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_1);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            EndContext();
            BeginContext(655, 123, true);
            WriteLiteral("\r\n            </div>\r\n\r\n            <div class=\"col-md-8 detalhes-receita\">\r\n\r\n                <p class=\"titulo-receita\"><p");
            EndContext();
            BeginWriteAttribute("id", " id=", 778, "", 800, 1);
#line 21 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
WriteAttributeValue("", 782, receita.ReceitaID, 782, 18, false);

#line default
#line hidden
            EndWriteAttribute();
            BeginContext(800, 22, true);
            WriteLiteral(" class=\"nome-receita\">");
            EndContext();
            BeginContext(823, 12, false);
#line 21 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                                                                                   Write(receita.Nome);

#line default
#line hidden
            EndContext();
            BeginContext(835, 557, true);
            WriteLiteral(@"</p></p>
                <div class=""row"">
                    <div class=""col-md-3"">
                        <ul class=""lista-receita-nomes"">

                            <li>Tempo de preparação:</li>
                            <li>Duração:</li>
                            <li>Classificação:</li>
                            <li>Dificuldade: </li>
                        </ul>
                    </div>
                    <div class=""col-md-9"">
                        <ul class=""lista-receita-detalhes"">
                            <li> ");
            EndContext();
            BeginContext(1393, 18, false);
#line 34 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                            Write(receita.Preparacao);

#line default
#line hidden
            EndContext();
            BeginContext(1411, 101, true);
            WriteLiteral(" <i class=\"fas fa-hourglass-end\" style=\"margin-left:5px\"></i></li>\r\n                            <li> ");
            EndContext();
            BeginContext(1513, 15, false);
#line 35 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                            Write(receita.Duracao);

#line default
#line hidden
            EndContext();
            BeginContext(1528, 126, true);
            WriteLiteral(" <i class=\"far fa-clock\" style=\"margin-left:5px\"></i></li>\r\n                            <li>\r\n                                ");
            EndContext();
            BeginContext(1655, 21, false);
#line 37 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                           Write(receita.Classificacao);

#line default
#line hidden
            EndContext();
            BeginContext(1676, 4, true);
            WriteLiteral("\r\n\r\n");
            EndContext();
#line 39 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                                 for (int j = 0; j < 5; j++)
                                {
                                    if (j < (int)receita.Classificacao)
                                    {

#line default
#line hidden
            BeginContext(1889, 93, true);
            WriteLiteral("                                        <i class=\"fas fa-star\" style=\"margin-left:5px\"></i>\r\n");
            EndContext();
#line 44 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                                    }
                                    else
                                    {

#line default
#line hidden
            BeginContext(2102, 93, true);
            WriteLiteral("                                        <i class=\"far fa-star\" style=\"margin-left:5px\"></i>\r\n");
            EndContext();
#line 48 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                                    }
                                }

#line default
#line hidden
            BeginContext(2269, 68, true);
            WriteLiteral("                            </li>\r\n                            <li> ");
            EndContext();
            BeginContext(2338, 19, false);
#line 51 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
                            Write(receita.Dificuldade);

#line default
#line hidden
            EndContext();
            BeginContext(2357, 263, true);
            WriteLiteral(@" <i class=""fas fa-praying-hands"" style=""margin-left: 15px;""></i> </li>
                        </ul>
                    </div>
                </div>
            </div>


        </div>
        <p></p>
        <p></p>
        <p></p>
        <p></p>
");
            EndContext();
#line 63 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
    }

#line default
#line hidden
#line 63 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Receitas\Index.cshtml"
     

}

#line default
#line hidden
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<IEnumerable<ChefIt.Models.Receita>> Html { get; private set; }
    }
}
#pragma warning restore 1591