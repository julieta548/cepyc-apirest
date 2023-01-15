package CepycJunio.cepyc.Servicios;

import CepycJunio.cepyc.Entidades.UnidadProductiva;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import CepycJunio.cepyc.Repositorios.UnidadProductivaRepositorio;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadProductivaServicio {

    @Autowired
    UnidadProductivaRepositorio repositorio;

    // TRANSACCIONES A LA BASE DE DATOS

    @Transactional
    public void crearUnidad(String nombre, String produccion, String departamento, String direccion) throws Exception {
        try {
            UnidadProductiva unidadProductiva = new UnidadProductiva();
            unidadProductiva.setNombre(nombre);
            unidadProductiva.setProduccion(produccion);
            unidadProductiva.setDepartamento(departamento);
            unidadProductiva.setDireccion(direccion);
            repositorio.save(unidadProductiva);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public List<UnidadProductiva> obtenerTodas() throws Exception {
        try {
            List<UnidadProductiva> unidades = repositorio.findAll();
            return unidades;
        } catch (Exception e) {
            System.err.print(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public UnidadProductiva editarUnidad(Long id, String nombre, String produccion, String departamento,
            String direccion) throws Exception {
        try {
            Optional<UnidadProductiva> respuesta = repositorio.findById(id);
            if (respuesta.isPresent()) {
                UnidadProductiva unidad = respuesta.get();
                unidad.setDepartamento(departamento);
                unidad.setDireccion(direccion);
                unidad.setNombre(nombre);
                unidad.setProduccion(produccion);
                return repositorio.save(unidad);
            } else {
                throw new Exception("No se logró modificar la unidad productiva solicitada.");
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void borrarUnidad(Long id) throws Exception {
        try {
            repositorio.deleteById(id);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public UnidadProductiva obtenerPorId(Long id)throws Exception{
        try {
            Optional<UnidadProductiva> respuesta = repositorio.findById(id);
            if (respuesta.isPresent()) {
                UnidadProductiva unidad = respuesta.get();
                return unidad;
            } else {
                throw new Exception("No se logró modificar la unidad productiva solicitada.");
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
            throw e;
        }
    }
}
