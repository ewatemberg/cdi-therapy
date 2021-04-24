package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionAS", "seccionBS", "seccionCS", "seccionDS", "paciente" }, allowSetters = true)
    private Cuestionario cuestionario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionCS" }, allowSetters = true)
    private FormaVerbal formaVerbal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeccionC id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public SeccionC descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValor() {
        return this.valor;
    }

    public SeccionC valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Cuestionario getCuestionario() {
        return this.cuestionario;
    }

    public SeccionC cuestionario(Cuestionario cuestionario) {
        this.setCuestionario(cuestionario);
        return this;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    public FormaVerbal getFormaVerbal() {
        return this.formaVerbal;
    }

    public SeccionC formaVerbal(FormaVerbal formaVerbal) {
        this.setFormaVerbal(formaVerbal);
        return this;
    }

    public void setFormaVerbal(FormaVerbal formaVerbal) {
        this.formaVerbal = formaVerbal;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
