package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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

    @OneToMany(mappedBy = "formaVerbal")
    @JsonIgnoreProperties(value = { "cuestionario", "formaVerbal" }, allowSetters = true)
    private Set<SeccionC> seccionCS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaVerbal id(Long id) {
        this.id = id;
        return this;
    }

    public String getForma() {
        return this.forma;
    }

    public FormaVerbal forma(String forma) {
        this.forma = forma;
        return this;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Set<SeccionC> getSeccionCS() {
        return this.seccionCS;
    }

    public FormaVerbal seccionCS(Set<SeccionC> seccionCS) {
        this.setSeccionCS(seccionCS);
        return this;
    }

    public FormaVerbal addSeccionC(SeccionC seccionC) {
        this.seccionCS.add(seccionC);
        seccionC.setFormaVerbal(this);
        return this;
    }

    public FormaVerbal removeSeccionC(SeccionC seccionC) {
        this.seccionCS.remove(seccionC);
        seccionC.setFormaVerbal(null);
        return this;
    }

    public void setSeccionCS(Set<SeccionC> seccionCS) {
        if (this.seccionCS != null) {
            this.seccionCS.forEach(i -> i.setFormaVerbal(null));
        }
        if (seccionCS != null) {
            seccionCS.forEach(i -> i.setFormaVerbal(this));
        }
        this.seccionCS = seccionCS;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
