package CepycJunio.cepyc.Servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import CepycJunio.cepyc.Entidades.Admin;
import CepycJunio.cepyc.Repositorios.AdminRepositorio;

@Service
public class AdminServicio implements UserDetailsService {

    private Logger logger = Logger.getLogger(AdminServicio.class);

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Transactional
    public Admin guardarAdmin(String usuario, String contrasenia) {
        try {
            Admin admin = new Admin();
            admin.setUsuario(usuario);
            String encriptada = new BCryptPasswordEncoder().encode(contrasenia);
            admin.setContrasenia(encriptada);
            return adminRepositorio.save(admin);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    @Transactional
    public List<Admin> buscarAdmin() {
        try {
            return adminRepositorio.findAll();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    @Transactional
    public boolean borrarAdmin() {
        try {
            adminRepositorio.deleteAll();
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    @Transactional
    public Admin modificarAdmin(String usuario, String contrasenia, Long id) {
        try {
            Optional<Admin> resultado = adminRepositorio.findById(id);
            if (resultado.isPresent()) {
                Admin admin = resultado.get();
                admin.setUsuario(usuario);
                admin.setContrasenia(contrasenia);
                return adminRepositorio.save(admin);
            }else{
                logger.info("No se encontró el admin con el id solicitado.");
                return null;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public boolean validarLogin(String usuario, String contrasenia){
        try {

            List<Admin> admin = adminRepositorio.validarLogin(usuario,contrasenia);
            return (admin.size() == 1);

        } catch (Exception e) {

            logger.info("Ocurrio un error validando el login");
            return false;
        }
    }

    public List<Admin> buscarAdminPorUsuario(String usuario){
        List<Admin> admin = adminRepositorio.buscarAdminPorUsuario(usuario);

        if (admin.size() == 1){
            return admin;
        }else{
            logger.info("Ocurrió un error al realizar el pedido buscarAdmin");
            return Collections.emptyList();
        }
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        
        Admin admin;

        if (buscarAdminPorUsuario(usuario).isEmpty()){
            return null;
        }else{
            admin = buscarAdminPorUsuario(usuario).get(0);
        }

        if (admin != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_ADMIN");
            permisos.add(permiso1);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession();
            session.setAttribute("adminsession", admin);

            return new User(admin.getUsuario(), admin.getContrasenia(), permisos);
            
        } else {
            return null;
        }
    }

}
