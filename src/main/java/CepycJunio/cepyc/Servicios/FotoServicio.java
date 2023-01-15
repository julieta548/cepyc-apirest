package CepycJunio.cepyc.Servicios;

import CepycJunio.cepyc.Entidades.Foto;
import CepycJunio.cepyc.Entidades.Producto;
import CepycJunio.cepyc.Repositorios.FotoRepositorio;
import CepycJunio.cepyc.Repositorios.ProductoRepositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio repositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Transactional
    public Foto guardar(MultipartFile archivo) throws Exception {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setContenido(archivo.getBytes());
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                return repositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public List<Foto> obtenerTodas() {
        try {
            return repositorio.findAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Foto obtenerPorId(Long id) throws Exception {
        try {
            Optional<Foto> resultado = repositorio.findById(id);
            if (resultado.isPresent()) {
                Foto foto = resultado.get();
                return foto;
            } else {
                throw new Exception("No se encontró foto con el id especificado");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public void borrar(Long id) {
        try {
            repositorio.deleteById(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Transactional
    public void borrarTodas() {
        try {
            repositorio.deleteAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void desvincular(Long idProducto){

        Optional<Producto> respuesta = productoRepositorio.findById(idProducto);

        this.borrarTodas();

        
        
    }

}
