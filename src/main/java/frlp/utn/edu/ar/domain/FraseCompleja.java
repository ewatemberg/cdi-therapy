package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A FraseCompleja.
 */
@Entity
@Table(name = "frase_compleja")
public class FraseCompleja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frase")
    private String frase;

    @OneToMany(mappedBy = "fraseCompleja")
    @JsonIgnoreProperties(value = { "cuestionario", "fraseCompleja" }, allowSetters = true)
    private Set<SeccionD> seccionDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FraseCompleja id(Long id) {
        this.id = id;
        return this;
    }

    public String getFrase() {
        return this.frase;
    }

    public FraseCompleja frase(String frase) {
        this.frase = frase;
        return this;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public Set<SeccionD> getSeccionDS() {
        return this.seccionDS;
    }

    public FraseCompleja seccionDS(Set<SeccionD> seccionDS) {
        this.setSeccionDS(seccionDS);
        return this;
    }

    public FraseCompleja addSeccionD(SeccionD seccionD) {
        this.seccionDS.add(seccionD);
        seccionD.setFraseCompleja(this);
        return this;
    }

    public FraseCompleja removeSeccionD(SeccionD seccionD) {
        this.seccionDS.remove(seccionD);
        seccionD.setFraseCompleja(null);
        return this;
    }

    public void setSeccionDS(Set<SeccionD> seccionDS) {
        if (this.seccionDS != null) {
            this.seccionDS.forEach(i -> i.setFraseCompleja(null));
        }
        if (seccionDS != null) {
            seccionDS.forEach(i -> i.setFraseCompleja(this));
        }
        this.seccionDS = seccionDS;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FraseCompleja)) {
            return false;
        }
        return id != null && id.equals(((FraseCompleja) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FraseCompleja{" +
            "id=" + getId() +
            ", frase='" + getFrase() + "'" +
            "}";
    }
}
