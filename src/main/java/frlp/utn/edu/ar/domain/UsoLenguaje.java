package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "usoLenguajes", allowSetters = true)
    private SeccionB seccionB;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public UsoLenguaje pregunta(String pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public SeccionB getSeccionB() {
        return seccionB;
    }

    public UsoLenguaje seccionB(SeccionB seccionB) {
        this.seccionB = seccionB;
        return this;
    }

    public void setSeccionB(SeccionB seccionB) {
        this.seccionB = seccionB;
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
        return 31;
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
