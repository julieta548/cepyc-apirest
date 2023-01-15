package CepycJunio.cepyc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import CepycJunio.cepyc.Entidades.Publicacion;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

    @Query("SELECT linkInstagram FROM Publicacion")
    public List<String> obtenerLinks();
}
