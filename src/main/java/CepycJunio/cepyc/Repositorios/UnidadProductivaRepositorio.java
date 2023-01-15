

package CepycJunio.cepyc.Repositorios;

import CepycJunio.cepyc.Entidades.UnidadProductiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadProductivaRepositorio extends JpaRepository<UnidadProductiva, Long>{

}
