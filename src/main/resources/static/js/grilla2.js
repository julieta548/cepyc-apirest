let registros = require('./productos');

const nombres = registros.sort((a, b)=>a.nombre.localeCompare(b.nombre));

nombres.forEach(element => {
 console.log(element.nombre)
});