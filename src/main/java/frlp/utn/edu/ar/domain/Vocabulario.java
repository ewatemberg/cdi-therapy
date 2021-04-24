package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import frlp.utn.edu.ar.domain.enumeration.CategoriaSemantica;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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

    @OneToMany(mappedBy = "vocabulario")
    @JsonIgnoreProperties(value = { "cuestionario", "vocabulario" }, allowSetters = true)
    private Set<SeccionA> seccionAS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vocabulario id(Long id) {
        this.id = id;
        return this;
    }

    public String getPalabra() {
        return this.palabra;
    }

    public Vocabulario palabra(String palabra) {
        this.palabra = palabra;
        return this;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public CategoriaSemantica getCategoria() {
        return this.categoria;
    }

    public Vocabulario categoria(CategoriaSemantica categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(CategoriaSemantica categoria) {
        this.categoria = categoria;
    }

    public Set<SeccionA> getSeccionAS() {
        return this.seccionAS;
    }

    public Vocabulario seccionAS(Set<SeccionA> seccionAS) {
        this.setSeccionAS(seccionAS);
        return this;
    }

    public Vocabulario addSeccionA(SeccionA seccionA) {
        this.seccionAS.add(seccionA);
        seccionA.setVocabulario(this);
        return this;
    }

    public Vocabulario removeSeccionA(SeccionA seccionA) {
        this.seccionAS.remove(seccionA);
        seccionA.setVocabulario(null);
        return this;
    }

    public void setSeccionAS(Set<SeccionA> seccionAS) {
        if (this.seccionAS != null) {
            this.seccionAS.forEach(i -> i.setVocabulario(null));
        }
        if (seccionAS != null) {
            seccionAS.forEach(i -> i.setVocabulario(this));
        }
        this.seccionAS = seccionAS;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
