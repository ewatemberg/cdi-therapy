package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "fraseComplejas", allowSetters = true)
    private SeccionD seccionD;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public FraseCompleja frase(String frase) {
        this.frase = frase;
        return this;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public SeccionD getSeccionD() {
        return seccionD;
    }

    public FraseCompleja seccionD(SeccionD seccionD) {
        this.seccionD = seccionD;
        return this;
    }

    public void setSeccionD(SeccionD seccionD) {
        this.seccionD = seccionD;
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
        return 31;
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
