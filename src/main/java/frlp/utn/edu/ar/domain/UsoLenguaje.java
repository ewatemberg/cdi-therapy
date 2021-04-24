package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A UsoLenguaje.
 */
@Entity
@Table(name = "uso_lenguaje")
public class UsoLenguaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pregunta")
    private String pregunta;

    @OneToMany(mappedBy = "usoLenguaje")
    @JsonIgnoreProperties(value = { "cuestionario", "usoLenguaje" }, allowSetters = true)
    private Set<SeccionB> seccionBS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsoLenguaje id(Long id) {
        this.id = id;
        return this;
    }

    public String getPregunta() {
        return this.pregunta;
    }

    public UsoLenguaje pregunta(String pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Set<SeccionB> getSeccionBS() {
        return this.seccionBS;
    }

    public UsoLenguaje seccionBS(Set<SeccionB> seccionBS) {
        this.setSeccionBS(seccionBS);
        return this;
    }

    public UsoLenguaje addSeccionB(SeccionB seccionB) {
        this.seccionBS.add(seccionB);
        seccionB.setUsoLenguaje(this);
        return this;
    }

    public UsoLenguaje removeSeccionB(SeccionB seccionB) {
        this.seccionBS.remove(seccionB);
        seccionB.setUsoLenguaje(null);
        return this;
    }

    public void setSeccionBS(Set<SeccionB> seccionBS) {
        if (this.seccionBS != null) {
            this.seccionBS.forEach(i -> i.setUsoLenguaje(null));
        }
        if (seccionBS != null) {
            seccionBS.forEach(i -> i.setUsoLenguaje(this));
        }
        this.seccionBS = seccionBS;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsoLenguaje)) {
            return false;
        }
        return id != null && id.equals(((UsoLenguaje) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsoLenguaje{" +
            "id=" + getId() +
            ", pregunta='" + getPregunta() + "'" +
            "}";
    }
}
