package CepycJunio.cepyc.Controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogo")
public class CatalogoControlador {

  
    @GetMapping("/catalogo")
    public String portadaCatalogo(){
        return "catalogo.html";
    }
}
