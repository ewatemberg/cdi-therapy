package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import frlp.utn.edu.ar.domain.enumeration.CategoriaSemantica;

/**
 * A Vocabulario.
 */
@Entity
@Table(name = "vocabulario")
public class Vocabulario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "palabra")
    private String palabra;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaSemantica categoria;

    @ManyToOne
    @JsonIgnoreProperties(value = "vocabularios", allowSetters = true)
    private SeccionA seccionA;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public Vocabulario palabra(String palabra) {
        this.palabra = palabra;
        return this;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public CategoriaSemantica getCategoria() {
        return categoria;
    }

    public Vocabulario categoria(CategoriaSemantica categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(CategoriaSemantica categoria) {
        this.categoria = categoria;
    }

    public SeccionA getSeccionA() {
        return seccionA;
    }

    public Vocabulario seccionA(SeccionA seccionA) {
        this.seccionA = seccionA;
        return this;
    }

    public void setSeccionA(SeccionA seccionA) {
        this.seccionA = seccionA;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vocabulario)) {
            return false;
        }
        return id != null && id.equals(((Vocabulario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vocabulario{" +
            "id=" + getId() +
            ", palabra='" + getPalabra() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
