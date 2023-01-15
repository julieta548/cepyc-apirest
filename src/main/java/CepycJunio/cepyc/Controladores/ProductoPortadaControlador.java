
package CepycJunio.cepyc.Controladores;

import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import CepycJunio.cepyc.Entidades.Producto;
import CepycJunio.cepyc.Servicios.ProductoServicio;

@Controller
@RequestMapping("/producto")
public class ProductoPortadaControlador {

    @Autowired
    private ProductoServicio productoServicio;

    Encoder encoder = Base64.getEncoder();

    @GetMapping("/single/{id}")
    public String mostrarProducto(Model model, @PathVariable("id") Long id) throws Exception {
        Producto productoX = productoServicio.obtenerPorId(id);

        model.addAttribute("productoX", productoX);


        model.addAttribute("encoder", encoder);

        return "producto.html";
    }

    @GetMapping("/grilla")
    public String grilla(Model model) {

        List<Producto> productos = productoServicio.obtenerTodos();
        model.addAttribute("productos", productos);

        model.addAttribute("encoder", encoder);
        return "grilla.html";
    }
}
