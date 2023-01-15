package CepycJunio.cepyc.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CepycJunio.cepyc.Entidades.Admin;

public interface AdminRepositorio extends JpaRepository<Admin,Long> {
    @Query(value = "SELECT a FROM Admin a WHERE a.usuario = :usuario AND a.contrasenia = :contrasenia",nativeQuery = true)
    public List<Admin> validarLogin(@Param("usuario")String usuario, @Param("contrasenia")String contrasenia);

    @Query("SELECT a FROM Admin a WHERE a.usuario = :usuario")
    public List<Admin> buscarAdminPorUsuario(@Param("usuario")String usuario);
}
