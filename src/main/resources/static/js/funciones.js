const $foto = document.querySelector("#productPhoto");
const $fotoContainer = document.querySelector("#photoContainer");

$('document').ready(function() {
    $('.postTable #editPostButton').on('click', function(event){

        event.preventDefault();

        var href= $(this).attr('href');

        $.get(href,function(publicacion, status) {
            $('#idPostEdit').val(publicacion.id);
            $('#tituloPostEdit').val(publicacion.titulo);
        });

        $('#editPostModal').modal('show');
    });
});

$('document').ready(function(){
    $('.productTable #editProductButton').on('click', function(event){

        event.preventDefault();

        var href=$(this).attr('href');

        $.get(href,function(producto, status) {
            $('#idProductEdit').val(producto.id);
            $('#fileProductEdit').val(producto.foto);
            $('#nameProductEdit').val(producto.nombre);
            $('#contactProductEdit').val(producto.contacto);
            $('#descriptionProductEdit').val(producto.descripcion);
            $('#productiveUnitProductEdit').val(producto.unidadProductiva);
        });
        $('#editProductModal').modal('show');
    })
});

$('document').ready(function(){
    $('.productTable #showPhotosButton').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');

        $.get(href,function(foto,status){
            $foto.setAttribute("src","data:image/png;base64," + foto)
        });

        $('#showPhotosModal').modal('show');
    })
});

function aplicaFiltroCards() {
    var input, filter, cards, cardContainer, h5, title, i;
    input = document.getElementById("filtroCards");
    filter = input.value.toUpperCase();
    cardContainer = document.getElementById("containerCards");
    cards = cardContainer.getElementsByClassName("card");
    for (i = 0; i < cards.length; i++) {
        title = cards[i].querySelector(".card-body");
        if (title.innerText.toUpperCase().indexOf(filter) > -1) {
            cards[i].style.display = "";
        } else {
            cards[i].style.display = "none";
        }
    }
}

let offset = 1;
let limit = 12;

function paginacion(offset, limit){
    for (let i = offset; i < offset + limit; i++) {
        const element = array[i];
        
    }
}
