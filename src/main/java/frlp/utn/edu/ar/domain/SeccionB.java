package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SeccionB.
 */
@Entity
@Table(name = "seccion_b")
public class SeccionB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Integer valor;

    @OneToMany(mappedBy = "seccionB")
    private Set<UsoLenguaje> usoLenguajes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "seccionBS", allowSetters = true)
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

    public SeccionB valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Set<UsoLenguaje> getUsoLenguajes() {
        return usoLenguajes;
    }

    public SeccionB usoLenguajes(Set<UsoLenguaje> usoLenguajes) {
        this.usoLenguajes = usoLenguajes;
        return this;
    }

    public SeccionB addUsoLenguaje(UsoLenguaje usoLenguaje) {
        this.usoLenguajes.add(usoLenguaje);
        usoLenguaje.setSeccionB(this);
        return this;
    }

    public SeccionB removeUsoLenguaje(UsoLenguaje usoLenguaje) {
        this.usoLenguajes.remove(usoLenguaje);
        usoLenguaje.setSeccionB(null);
        return this;
    }

    public void setUsoLenguajes(Set<UsoLenguaje> usoLenguajes) {
        this.usoLenguajes = usoLenguajes;
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public SeccionB cuestionario(Cuestionario cuestionario) {
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
        if (!(o instanceof SeccionB)) {
            return false;
        }
        return id != null && id.equals(((SeccionB) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeccionB{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
