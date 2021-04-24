package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SeccionD.
 */
@Entity
@Table(name = "seccion_d")
public class SeccionD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Integer valor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionAS", "seccionBS", "seccionCS", "seccionDS", "paciente" }, allowSetters = true)
    private Cuestionario cuestionario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccionDS" }, allowSetters = true)
    private FraseCompleja fraseCompleja;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeccionD id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getValor() {
        return this.valor;
    }

    public SeccionD valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Cuestionario getCuestionario() {
        return this.cuestionario;
    }

    public SeccionD cuestionario(Cuestionario cuestionario) {
        this.setCuestionario(cuestionario);
        return this;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    public FraseCompleja getFraseCompleja() {
        return this.fraseCompleja;
    }

    public SeccionD fraseCompleja(FraseCompleja fraseCompleja) {
        this.setFraseCompleja(fraseCompleja);
        return this;
    }

    public void setFraseCompleja(FraseCompleja fraseCompleja) {
        this.fraseCompleja = fraseCompleja;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeccionD)) {
            return false;
        }
        return id != null && id.equals(((SeccionD) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeccionD{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
