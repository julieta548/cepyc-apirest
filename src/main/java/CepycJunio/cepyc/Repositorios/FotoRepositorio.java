

package CepycJunio.cepyc.Repositorios;

import CepycJunio.cepyc.Entidades.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Long>{

    public List<Foto> findByNombre(String nombre);
}
