
/*
@if(User.Identity.IsAuthenticated)
{
< li class="nav-item">
    <span class="navbar-text">
        <p>Bem-vindo, <b><u>@User.Identity.Name</u></b></p>
    </span>
</li>
<li class="nav-item">
    <a class="nav-link text-dark" asp-controller="Users" asp-action="Logout">
        Sign Out
    </a>
</li>
                    }
                    else
                    {
<li class="nav-item">
    <a class="nav-link text-dark" asp-controller="Users" asp-action="UserLogin">
        Login
    </a>
</li>
<li class="nav-item">
    <a class="nav-link text-dark" asp-controller="Users" asp-action="RegisterUser">
        Registo
    </a>
</li>
                    }


                    <h2>Index</h2>

<p>
    <a asp-action="Create">Create New</a>
</p>
<table class="table">
    <thead>
        <tr>
            <th>
                @Html.DisplayNameFor(model => model.Nome)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Descricao)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Preparacao)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Duracao)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Dose)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Dificuldade)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Classificacao)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.Estado)
            </th>
            <th>
                @Html.DisplayNameFor(model => model.User)
            </th>
            <th></th>
        </tr>
    </thead>
    <tbody>
@foreach (var item in Model) {
        <tr>
            <td>
                @Html.DisplayFor(modelItem => item.Nome)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Descricao)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Preparacao)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Duracao)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Dose)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Dificuldade)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Classificacao)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.Estado)
            </td>
            <td>
                @Html.DisplayFor(modelItem => item.User.Email)
            </td>
            <td>
                <a asp-action="Edit" asp-route-id="@item.ReceitaID">Edit</a> |
                <a asp-action="Details" asp-route-id="@item.ReceitaID">Details</a> |
                <a asp-action="Delete" asp-route-id="@item.ReceitaID">Delete</a>
            </td>
        </tr>
}
    </tbody>
</table>
*/