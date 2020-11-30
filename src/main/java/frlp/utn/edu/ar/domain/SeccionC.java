package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SeccionC.
 */
@Entity
@Table(name = "seccion_c")
public class SeccionC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor")
    private Integer valor;

    @OneToMany(mappedBy = "seccionC")
    private Set<FormaVerbal> formaVerbals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "seccionCS", allowSetters = true)
    private Cuestionario cuestionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public SeccionC descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValor() {
        return valor;
    }

    public SeccionC valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Set<FormaVerbal> getFormaVerbals() {
        return formaVerbals;
    }

    public SeccionC formaVerbals(Set<FormaVerbal> formaVerbals) {
        this.formaVerbals = formaVerbals;
        return this;
    }

    public SeccionC addFormaVerbal(FormaVerbal formaVerbal) {
        this.formaVerbals.add(formaVerbal);
        formaVerbal.setSeccionC(this);
        return this;
    }

    public SeccionC removeFormaVerbal(FormaVerbal formaVerbal) {
        this.formaVerbals.remove(formaVerbal);
        formaVerbal.setSeccionC(null);
        return this;
    }

    public void setFormaVerbals(Set<FormaVerbal> formaVerbals) {
        this.formaVerbals = formaVerbals;
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public SeccionC cuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
        return this;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeccionC)) {
            return false;
        }
        return id != null && id.equals(((SeccionC) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeccionC{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
