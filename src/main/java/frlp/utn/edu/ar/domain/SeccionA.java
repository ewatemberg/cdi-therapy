package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionAS", "seccionBS", "seccionCS", "seccionDS", "paciente" }, allowSetters = true)
    private Cuestionario cuestionario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionAS" }, allowSetters = true)
    private Vocabulario vocabulario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeccionA id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public SeccionA descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getChequeado() {
        return this.chequeado;
    }

    public SeccionA chequeado(Boolean chequeado) {
        this.chequeado = chequeado;
        return this;
    }

    public void setChequeado(Boolean chequeado) {
        this.chequeado = chequeado;
    }

    public Cuestionario getCuestionario() {
        return this.cuestionario;
    }

    public SeccionA cuestionario(Cuestionario cuestionario) {
        this.setCuestionario(cuestionario);
        return this;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    public Vocabulario getVocabulario() {
        return this.vocabulario;
    }

    public SeccionA vocabulario(Vocabulario vocabulario) {
        this.setVocabulario(vocabulario);
        return this;
    }

    public void setVocabulario(Vocabulario vocabulario) {
        this.vocabulario = vocabulario;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeccionA{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", chequeado='" + getChequeado() + "'" +
            "}";
    }
}
