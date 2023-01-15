package CepycJunio.cepyc.Servicios;

import org.springframework.web.multipart.MultipartFile;
import CepycJunio.cepyc.Entidades.Foto;
import CepycJunio.cepyc.Entidades.Producto;
import CepycJunio.cepyc.Repositorios.ProductoRepositorio;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    // TRANSACCIONES A LA BASE DE DATOS

    @Transactional
    public Producto crear(MultipartFile archivo, String nombre, String descripcion, String contacto,
            String unidadProductiva) throws Exception {

        try {
            Foto foto = fotoServicio.guardar(archivo);
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setContacto(contacto);
            
            producto.setUnidadProductiva(unidadProductiva);
            asociarFoto(foto, producto);

            productoRepositorio.save(producto);
            return producto;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public List<Producto> obtenerTodos() {
        try {
            return productoRepositorio.findAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Producto modificar(MultipartFile archivo, Long id,
            String nombre, String descripcion, String contacto,
            String unidadProductiva) throws Exception {

        try {
            Optional<Producto> resultado = productoRepositorio.findById(id);

            if (resultado.isPresent()) {
                Producto producto = resultado.get();
                if (archivo != null) {
                    producto.removeFoto();
                    fotoServicio.borrar(producto.getFoto().getId());
                    Foto foto = fotoServicio.guardar(archivo);
                    producto.addFoto(foto);
                }
                if (nombre != null) {
                    producto.setNombre(nombre);
                }
                if (descripcion != null) {
                    producto.setDescripcion(descripcion);
                }
                if (contacto != null) {
                    producto.setContacto(contacto);
                }
                if (unidadProductiva != null) {
                    producto.setUnidadProductiva(unidadProductiva);
                }
                return productoRepositorio.save(producto);
            } else {
                throw new Exception("No se encontró el archivo con el id especiicado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Producto obtenerPorId(Long id) throws Exception {
        try {
            Optional<Producto> respuesta = productoRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Producto producto = respuesta.get();
                return producto;
            } else {
                throw new Exception("No se encontró el producto con el id solicitado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void eliminar(Long id) throws Exception {
        try {
            Optional<Producto> respuesta = productoRepositorio.findById(id);

            if (respuesta.isPresent()) {
                Foto fotoDelProducto = obtenerFoto(id);

                Producto producto = respuesta.get();

                producto.removeFoto();

                fotoServicio.borrar(fotoDelProducto.getId());

                productoRepositorio.delete(producto);
            } else {
                throw new Exception("No se encontró el producto con el id solicitado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Transactional
    private Foto obtenerFoto(Long id) throws Exception {
        try {
            Optional<Producto> respuesta = productoRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Producto producto = respuesta.get();
                Foto foto = producto.getFoto();
                return foto;
            } else {
                throw new Exception("No se encontró el producto con el id solicitado.");
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
            throw e;
        }
    }

    // LOGICAS DE NEGOCIO

    @Transactional
    public void asociarFoto(Foto foto, Producto producto) {
        producto.addFoto(foto);
        foto.addProducto(producto);
    }

    @Transactional
    public byte[] obtenerBytesDeFoto(Long id) throws Exception {

        Foto foto = obtenerPorId(id).getFoto();

        byte[] contenidoFoto = foto.getContenido();

        return contenidoFoto;
    }

    public String obtenerBase64String(byte[] contenidoFoto) {

        String contenidoBase64;

        Encoder encoder = Base64.getEncoder();

        contenidoBase64 = encoder.encodeToString(contenidoFoto);

        return contenidoBase64;
    }


    
}
