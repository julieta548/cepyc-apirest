package CepycJunio.cepyc.Entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    @JsonIgnore
    @OneToOne(mappedBy = "producto", targetEntity = Foto.class, fetch = FetchType.LAZY)
    private Foto foto;
    
    private String descripcion;

    private String contacto;

    private String unidadProductiva;

    private String miniDescripcion;


    
    public String getMiniDescripcion() {
        return miniDescripcion;
    }

    public void setMiniDescripcion(String miniDescripcion) {
        this.miniDescripcion = miniDescripcion;
    }

    public void addFoto(Foto foto){
        if (this.foto == null){
            this.foto = foto;
        }
    }

    public void removeFoto(){
        if (this.foto != null){
            this.foto = null;
        }
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getUnidadProductiva() {
        return unidadProductiva;
    }

    public void setUnidadProductiva(String unidadProductiva) {
        this.unidadProductiva = unidadProductiva;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.getId() != null) || (this.id != null && !this.id.equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CepycJunio.cepyc.Entidades.Producto[ id=" + id + " ]";
    }

}
