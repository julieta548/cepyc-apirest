package CepycJunio.cepyc.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import CepycJunio.cepyc.Entidades.Publicacion;
import CepycJunio.cepyc.Servicios.PublicacionServicio;

@Controller
@RequestMapping("/publicacion")
public class PublicacionControlador {

    @Autowired
    private PublicacionServicio publicacionServicio;

    @PostMapping("/guardar-publicacion")
    public String guardarPublicacion(@RequestParam(name = "titulo") String titulo,@RequestParam(name = "linkInstagram") String linkInstagram) throws Exception {

        String linkCorregido = publicacionServicio.corregirLink(linkInstagram);

        publicacionServicio.guardar(titulo, linkCorregido);

        return "redirect:/editable-portada";
    }

    @RequestMapping("/obtener-publicaciones")
    public List<Publicacion> obtenerPublicaciones() {
        return publicacionServicio.buscarTodos();
    }

    @RequestMapping("/obtener-publicacion/{id}")
    @ResponseBody
    public Publicacion obtenerPublicacionPorId(@PathVariable("id") Long id) throws Exception {
        return publicacionServicio.buscarPorId(id);
    }

    @RequestMapping(value = "/editar-publicacion", method = {RequestMethod.PUT,RequestMethod.GET})
    public String editarPublicacion(@RequestParam("idEdit") Long id, @RequestParam("tituloEdit") String titulo)throws Exception {

        publicacionServicio.modificar(id, titulo);

        return "redirect:/editable-portada";
    }

    @RequestMapping(value = "/eliminar-publicacion", method = {RequestMethod.DELETE,RequestMethod.GET})
    public String eliminarPublicacion(@RequestParam Long id)throws Exception {

        publicacionServicio.borrar(id);

        return "redirect:/editable-portada";
    }
}
