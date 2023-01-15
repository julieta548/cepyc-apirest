package CepycJunio.cepyc.Controladores;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import CepycJunio.cepyc.Entidades.Producto;
import CepycJunio.cepyc.Servicios.ProductoServicio;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    
    @PostMapping("/guardar-producto")
    public String guardarProducto(@RequestParam(name = "archivo1") MultipartFile archivo,
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "descripcion") String descripcion,
            @RequestParam(name = "contacto") String contacto,
            @RequestParam(name = "unidadProductiva") String unidadProductiva) {

        try {

            productoServicio.crear(archivo, nombre, descripcion, contacto, unidadProductiva);
            return "redirect:/editable-portada";

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "redirect:/editable-portada";
        }
    }

    

    @GetMapping("/todos")
    public String todosLosProductos(Model model, @RequestParam Map<String, Object> params) throws Exception{
        Encoder encoder = Base64.getEncoder();

      

        List<Producto> productos = productoServicio.obtenerTodos();
        model.addAttribute("productos", productos);
        model.addAttribute("encoder", encoder); 



        return "grilla.html";
    }

    @RequestMapping("/obtener-producto/{id}")
    @ResponseBody
    public Producto obtenerPorId(@PathVariable("id") Long id) throws Exception{
        return productoServicio.obtenerPorId(id);
    }

    @RequestMapping("/obtener-productos")
    public List<Producto> obtenerProductos(){
        return productoServicio.obtenerTodos();
    }

    @RequestMapping(value = "/editar-producto", method =  {RequestMethod.PUT,RequestMethod.GET})
    public String editarProducto(@RequestParam("idProductEdit") Long id, @RequestParam("descripcionEdit") String descripcion, @RequestParam("contactoEdit") String contacto, @RequestParam("nombreEdit") String nombre, @RequestParam("unidadEdit")String unidadProductiva,
    @RequestParam(required = false, value = "fileProductEdit0")MultipartFile archivo)throws Exception{

        productoServicio.modificar(archivo, id, nombre, descripcion, contacto, unidadProductiva);

        return "redirect:/editable-portada";
    }

    @RequestMapping("/obtener-foto/{id}")
    @ResponseBody
    public String obtenerFoto(@PathVariable("id") Long id)throws Exception{
      return productoServicio.obtenerBase64String(productoServicio.obtenerBytesDeFoto(id));
    }

    @RequestMapping(value = "/eliminar-producto",method = {RequestMethod.DELETE, RequestMethod.GET})
    public String eliminarProducto(@RequestParam("id")Long id) throws Exception{

        productoServicio.eliminar(id);
        return "redirect:/editable-portada";
    }


    
}
