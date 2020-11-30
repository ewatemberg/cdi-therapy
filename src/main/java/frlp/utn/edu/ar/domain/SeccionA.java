package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SeccionA.
 */
@Entity
@Table(name = "seccion_a")
public class SeccionA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "chequeado")
    private Boolean chequeado;

    @OneToMany(mappedBy = "seccionA")
    private Set<Vocabulario> vocabularios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "seccionAS", allowSetters = true)
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

    public SeccionA descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isChequeado() {
        return chequeado;
    }

    public SeccionA chequeado(Boolean chequeado) {
        this.chequeado = chequeado;
        return this;
    }

    public void setChequeado(Boolean chequeado) {
        this.chequeado = chequeado;
    }

    public Set<Vocabulario> getVocabularios() {
        return vocabularios;
    }

    public SeccionA vocabularios(Set<Vocabulario> vocabularios) {
        this.vocabularios = vocabularios;
        return this;
    }

    public SeccionA addVocabulario(Vocabulario vocabulario) {
        this.vocabularios.add(vocabulario);
        vocabulario.setSeccionA(this);
        return this;
    }

    public SeccionA removeVocabulario(Vocabulario vocabulario) {
        this.vocabularios.remove(vocabulario);
        vocabulario.setSeccionA(null);
        return this;
    }

    public void setVocabularios(Set<Vocabulario> vocabularios) {
        this.vocabularios = vocabularios;
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public SeccionA cuestionario(Cuestionario cuestionario) {
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
        if (!(o instanceof SeccionA)) {
            return false;
        }
        return id != null && id.equals(((SeccionA) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeccionA{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", chequeado='" + isChequeado() + "'" +
            "}";
    }
}
