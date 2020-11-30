package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FormaVerbal.
 */
@Entity
@Table(name = "forma_verbal")
public class FormaVerbal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "forma")
    private String forma;

    @ManyToOne
    @JsonIgnoreProperties(value = "formaVerbals", allowSetters = true)
    private SeccionC seccionC;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForma() {
        return forma;
    }

    public FormaVerbal forma(String forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public SeccionC getSeccionC() {
        return seccionC;
    }

    public FormaVerbal seccionC(SeccionC seccionC) {
        this.seccionC = seccionC;
        return this;
    }

    public void setSeccionC(SeccionC seccionC) {
        this.seccionC = seccionC;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaVerbal)) {
            return false;
        }
        return id != null && id.equals(((FormaVerbal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormaVerbal{" +
            "id=" + getId() +
            ", forma='" + getForma() + "'" +
            "}";
    }
}
