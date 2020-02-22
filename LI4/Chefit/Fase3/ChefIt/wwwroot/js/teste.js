//$("#teste").on("keyup", function () {
   
//    if ($("#teste").val().length > 5) {
//        //$("#teste").css("background-color", "black");
//        alert("");
//    }
//})

$('.botao').css('cursor', 'pointer');


$('.botao').on("click", function ()
{
    var numero = this.textContent.trim();

    $(".descricao").css("display", "none");
    var id = "#passo-" + numero;
    $(id).css("display", "block");

    $('.botao').css("background-color", "white");
    $(this).css("background-color", "lawngreen");
})

$(".linha-receita").css('cursor', 'pointer');

$('.linha-receita').on("mouseover", function ()
{
    $(this).css("box-shadow", "9px 9px 28px 4px rgba(0, 0, 0, 0.75)");
    $(this).css("-webkit-box-shadow", "9px 9px 28px 4px rgba(0, 0, 0, 0.75)");
    $(this).css("-moz-box-shadow", "9px 9px 28px 4px rgba(0, 0, 0, 0.75)");
})


$('.linha-receita').on("mouseout", function () {
    $(this).css("box-shadow", "");
})
