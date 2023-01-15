package CepycJunio.cepyc.Controladores;

import java.util.Base64;

import java.util.Base64.Encoder;

import javax.servlet.http.HttpSession;

import CepycJunio.cepyc.Entidades.Admin;
import CepycJunio.cepyc.Entidades.Producto;
import CepycJunio.cepyc.Entidades.Publicacion;
import CepycJunio.cepyc.Entidades.UnidadProductiva;
import CepycJunio.cepyc.Servicios.AdminServicio;
import CepycJunio.cepyc.Servicios.ProductoServicio;
import CepycJunio.cepyc.Servicios.PublicacionServicio;
import CepycJunio.cepyc.Servicios.UnidadProductivaServicio;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class PortadaControlador {

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UnidadProductivaServicio unidadProductivaServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;



    @RequestMapping(value = "/", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_PNG_VALUE })
    public String index(Model model, HttpSession session) throws Exception {

        boolean sesionAbierta = false;

        Admin admin = (Admin) session.getAttribute("adminsession");

        Encoder encoder = Base64.getEncoder();

        List<Producto> productos = productoServicio.obtenerTodos();

        List<String> linksPublicaciones = publicacionServicio.obtenerLinks();

        if (admin != null) {
            sesionAbierta = true;
        }

        model.addAttribute("linksPublicaciones", linksPublicaciones);
        model.addAttribute("encoder", encoder);
        model.addAttribute("sesionAbierta", sesionAbierta);
        model.addAttribute("productos", productos);

        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model modelo) {
        List<Admin> admins = adminServicio.buscarAdmin();

        modelo.addAttribute("admins", admins);

        if (error != null) {
            modelo.addAttribute("error", error);
        }

        return "login.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registro")
    public String formularioRegistro(Model model){

       List<Admin> admins = adminServicio.buscarAdmin();

        model.addAttribute("admins", admins);
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("usuario") String usuario,
    @RequestParam("contrasenia") String contrasenia){

        adminServicio.guardarAdmin(usuario, contrasenia);

        return "redirect:/login";
    }

    @PostMapping("/chequear-login")
    public String chequearLogin(@RequestParam("usuario") String usuario,
            @RequestParam("contrasenia") String contrasenia) throws Exception {

        try {
            if (adminServicio.validarLogin(usuario, contrasenia)) {
                return "redirect:/editable-portada";
            } else {
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/editable-portada", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_PNG_VALUE })
    public String portadaEditable(Model model) throws Exception {

        List<Producto> productos = productoServicio.obtenerTodos();

        List<UnidadProductiva> unidades = unidadProductivaServicio.obtenerTodas();
        
        List<Publicacion> publicaciones = publicacionServicio.buscarTodos();



        // OPERADOR TERNARIO List<Foto> carrusel = (contenidoCarruselServicio.obtener()
        // == null) ? null : contenidoCarruselServicio.obtener().getFotos();

        Encoder encoder = Base64.getEncoder();


        model.addAttribute("unidades", unidades);
        model.addAttribute("publicaciones", publicaciones);
        model.addAttribute("encoder", encoder);
        model.addAttribute("productos", productos);

        return "tabla-mensajes.html";
    }

    @PostMapping(value = "/subir-producto", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String subirProducto(@RequestParam(name = "archivo1") MultipartFile archivo1,
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "descripcion") String descripcion,
            @RequestParam(name = "contacto") String contacto,
            @RequestParam(name = "unidadProductiva") String unidadProductiva) {

        try {

            productoServicio.crear(archivo1, nombre, descripcion, contacto, unidadProductiva);

            return "redirect:/editable-portada";

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "redirect:/editable-portada";
        }
    }

}
