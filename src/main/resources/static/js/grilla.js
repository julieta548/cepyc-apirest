let registros = require('./productos');

// const productos = [
//   {nombre: registros.nombre,
//   descripcion: registros.descripcion,
//   contacto: registros.contacto,
//   unidadProductiva: registros.unidadProductiva,
//   contenido: registros.contenido}

// ]


   
registros.forEach(element => {
  console.log(element.nombre + ', descripcion: ' + element.descripcion)
 });




var swiper = new Swiper(".mySwiper", {
    navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
       
    },
     
});

