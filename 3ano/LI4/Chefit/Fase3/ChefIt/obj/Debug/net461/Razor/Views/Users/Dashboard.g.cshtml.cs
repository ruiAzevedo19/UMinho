#pragma checksum "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "fd84d09243d32d78108a2e80cd71530f51848597"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Users_Dashboard), @"mvc.1.0.view", @"/Views/Users/Dashboard.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Users/Dashboard.cshtml", typeof(AspNetCore.Views_Users_Dashboard))]
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
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"fd84d09243d32d78108a2e80cd71530f51848597", @"/Views/Users/Dashboard.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"2ebcf41b0b1bc2bb1ae405e19d5462873069dcce", @"/Views/_ViewImports.cshtml")]
    public class Views_Users_Dashboard : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<dynamic>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(0, 2, true);
            WriteLiteral("\r\n");
            EndContext();
#line 2 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml"
  
    ViewData["Title"] = "Dashboard";

#line default
#line hidden
            BeginContext(47, 17, true);
            WriteLiteral("\r\n<h2>Bem-vindo, ");
            EndContext();
            BeginContext(65, 16, false);
#line 6 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml"
          Write(ViewBag.Username);

#line default
#line hidden
            EndContext();
            BeginContext(81, 9, true);
            WriteLiteral("</h2>\r\n\r\n");
            EndContext();
#line 8 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml"
 if(ViewBag.Tipo == "a")
{

#line default
#line hidden
            BeginContext(119, 34, true);
            WriteLiteral("    <h1>Sou o administrador</h1>\r\n");
            EndContext();
#line 11 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml"
}
else
{

#line default
#line hidden
            BeginContext(165, 29, true);
            WriteLiteral("    <h1>Sou um cliente</h1>\r\n");
            EndContext();
#line 15 "C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Views\Users\Dashboard.cshtml"
}

#line default
#line hidden
            BeginContext(197, 2, true);
            WriteLiteral("\r\n");
            EndContext();
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
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<dynamic> Html { get; private set; }
    }
}
#pragma warning restore 1591