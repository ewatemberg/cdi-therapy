package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "seccionD")
    private Set<FraseCompleja> fraseComplejas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "seccionDS", allowSetters = true)
    private Cuestionario cuestionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public SeccionD valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Set<FraseCompleja> getFraseComplejas() {
        return fraseComplejas;
    }

    public SeccionD fraseComplejas(Set<FraseCompleja> fraseComplejas) {
        this.fraseComplejas = fraseComplejas;
        return this;
    }

    public SeccionD addFraseCompleja(FraseCompleja fraseCompleja) {
        this.fraseComplejas.add(fraseCompleja);
        fraseCompleja.setSeccionD(this);
        return this;
    }

    public SeccionD removeFraseCompleja(FraseCompleja fraseCompleja) {
        this.fraseComplejas.remove(fraseCompleja);
        fraseCompleja.setSeccionD(null);
        return this;
    }

    public void setFraseComplejas(Set<FraseCompleja> fraseComplejas) {
        this.fraseComplejas = fraseComplejas;
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public SeccionD cuestionario(Cuestionario cuestionario) {
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
        if (!(o instanceof SeccionD)) {
            return false;
        }
        return id != null && id.equals(((SeccionD) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
