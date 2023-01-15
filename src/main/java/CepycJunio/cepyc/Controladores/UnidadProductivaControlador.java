package CepycJunio.cepyc.Controladores;

import CepycJunio.cepyc.Entidades.UnidadProductiva;
import CepycJunio.cepyc.Servicios.UnidadProductivaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/unidad-productiva")
public class UnidadProductivaControlador {

    @Autowired
    UnidadProductivaServicio unidadProductivaServicio;
    
    @PostMapping("/guardar-unidad")
    public String unidadesBorrar(@RequestParam("nombre") String nombre, @RequestParam("produccion") String produccion, @RequestParam("departamento") String departamento, @RequestParam("direccion") String direccion) throws Exception{
        
        unidadProductivaServicio.crearUnidad(nombre, produccion, departamento, direccion);
        return "redirect:/editable-portada";
    }

    
    @RequestMapping(value ="/editar-unidad", method = {RequestMethod.GET,RequestMethod.PUT})
    public String editarUnidad(@RequestParam("idPuEdit") Long id,@RequestParam("nombrePuEdit") String nombre,@RequestParam("produccionPuEdit")String produccion,@RequestParam("departamentoPuEdit")String departamento,@RequestParam("direccionPuEdit")String direccion) throws Exception{
        
        unidadProductivaServicio.editarUnidad(id, nombre, produccion, departamento, direccion);
        return "redirect:/editable-portada";
    }
    
    @RequestMapping(value = "/obtener-unidad/{id}")
    @ResponseBody
    public UnidadProductiva obtenerUnidad(@PathVariable("id")Long id) throws Exception{
        return unidadProductivaServicio.obtenerPorId(id);
    }

    @RequestMapping("/obtener-todas")
    public List<UnidadProductiva> obtenerTodas() throws Exception{
        return unidadProductivaServicio.obtenerTodas();
    }

    @RequestMapping(value ="/eliminar-unidad", method = {RequestMethod.DELETE,RequestMethod.GET})
    public String eliminarUnidad(@RequestParam("id") Long id) throws Exception{
    
        unidadProductivaServicio.borrarUnidad(id);
        return "redirect:/editable-portada";
    }
}
