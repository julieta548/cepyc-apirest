package CepycJunio.cepyc.Servicios;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import CepycJunio.cepyc.Entidades.Publicacion;
import CepycJunio.cepyc.Repositorios.PublicacionRepositorio;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio repositorio;

    private static Logger logger = Logger.getLogger(PublicacionServicio.class);

    //TRANSACCIONES A LA BASE DE DATOS

    @Transactional
    public Publicacion guardar(String titulo,String linkInstagram)
            throws Exception {
        try {
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(titulo);
            publicacion.setLinkInstagram(linkInstagram);
            return repositorio.save(publicacion);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Publicacion modificar(Long id, String titulo) throws Exception {
        try {
            Optional<Publicacion> respuesta = repositorio.findById(id);

            if (respuesta.isPresent()) {

                Publicacion publicacionAntigua = respuesta.get();

                publicacionAntigua.setTitulo(titulo);

                return repositorio.save(publicacionAntigua);
            } else {
                throw new Exception("No se encontró la publicacion con el id especificado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public void borrar(Long idPublicacion) {
        try {
            repositorio.deleteById(idPublicacion);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public List<Publicacion> buscarTodos() {
        try {
            return repositorio.findAll();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    @Transactional
    public Publicacion buscarPorId(Long idPublicacion) throws Exception {
        try {
            Optional<Publicacion> resultado = repositorio.findById(idPublicacion);
            if (resultado.isPresent()) {
                Publicacion publicacion = resultado.get();
                return publicacion;
            } else {
                throw new Exception("No se encontró la publicación solicitada");
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    //LOGICAS DE NEGOCIO

    public String corregirLink(String linkInstagram){

        if (linkInstagram.endsWith("/")){
            return linkInstagram.concat("embed");
        }else{
            return linkInstagram.concat("/embed");
        }
        
    }

    public List<String> obtenerLinks(){
        return repositorio.obtenerLinks();
    }
}
