package CepycJunio.cepyc.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CepycJunio.cepyc.Entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    
}
