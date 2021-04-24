package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Cuestionario.
 */
@Entity
@Table(name = "cuestionario")
public class Cuestionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cuestionario")
    @JsonIgnoreProperties(value = { "cuestionario", "vocabulario" }, allowSetters = true)
    private Set<SeccionA> seccionAS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    @JsonIgnoreProperties(value = { "cuestionario", "usoLenguaje" }, allowSetters = true)
    private Set<SeccionB> seccionBS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    @JsonIgnoreProperties(value = { "cuestionario", "formaVerbal" }, allowSetters = true)
    private Set<SeccionC> seccionCS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    @JsonIgnoreProperties(value = { "cuestionario", "fraseCompleja" }, allowSetters = true)
    private Set<SeccionD> seccionDS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "cuestionarios" }, allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuestionario id(Long id) {
        this.id = id;
        return this;
    }

    public Set<SeccionA> getSeccionAS() {
        return this.seccionAS;
    }

    public Cuestionario seccionAS(Set<SeccionA> seccionAS) {
        this.setSeccionAS(seccionAS);
        return this;
    }

    public Cuestionario addSeccionA(SeccionA seccionA) {
        this.seccionAS.add(seccionA);
        seccionA.setCuestionario(this);
        return this;
    }

    public Cuestionario removeSeccionA(SeccionA seccionA) {
        this.seccionAS.remove(seccionA);
        seccionA.setCuestionario(null);
        return this;
    }

    public void setSeccionAS(Set<SeccionA> seccionAS) {
        if (this.seccionAS != null) {
            this.seccionAS.forEach(i -> i.setCuestionario(null));
        }
        if (seccionAS != null) {
            seccionAS.forEach(i -> i.setCuestionario(this));
        }
        this.seccionAS = seccionAS;
    }

    public Set<SeccionB> getSeccionBS() {
        return this.seccionBS;
    }

    public Cuestionario seccionBS(Set<SeccionB> seccionBS) {
        this.setSeccionBS(seccionBS);
        return this;
    }

    public Cuestionario addSeccionB(SeccionB seccionB) {
        this.seccionBS.add(seccionB);
        seccionB.setCuestionario(this);
        return this;
    }

    public Cuestionario removeSeccionB(SeccionB seccionB) {
        this.seccionBS.remove(seccionB);
        seccionB.setCuestionario(null);
        return this;
    }

    public void setSeccionBS(Set<SeccionB> seccionBS) {
        if (this.seccionBS != null) {
            this.seccionBS.forEach(i -> i.setCuestionario(null));
        }
        if (seccionBS != null) {
            seccionBS.forEach(i -> i.setCuestionario(this));
        }
        this.seccionBS = seccionBS;
    }

    public Set<SeccionC> getSeccionCS() {
        return this.seccionCS;
    }

    public Cuestionario seccionCS(Set<SeccionC> seccionCS) {
        this.setSeccionCS(seccionCS);
        return this;
    }

    public Cuestionario addSeccionC(SeccionC seccionC) {
        this.seccionCS.add(seccionC);
        seccionC.setCuestionario(this);
        return this;
    }

    public Cuestionario removeSeccionC(SeccionC seccionC) {
        this.seccionCS.remove(seccionC);
        seccionC.setCuestionario(null);
        return this;
    }

    public void setSeccionCS(Set<SeccionC> seccionCS) {
        if (this.seccionCS != null) {
            this.seccionCS.forEach(i -> i.setCuestionario(null));
        }
        if (seccionCS != null) {
            seccionCS.forEach(i -> i.setCuestionario(this));
        }
        this.seccionCS = seccionCS;
    }

    public Set<SeccionD> getSeccionDS() {
        return this.seccionDS;
    }

    public Cuestionario seccionDS(Set<SeccionD> seccionDS) {
        this.setSeccionDS(seccionDS);
        return this;
    }

    public Cuestionario addSeccionD(SeccionD seccionD) {
        this.seccionDS.add(seccionD);
        seccionD.setCuestionario(this);
        return this;
    }

    public Cuestionario removeSeccionD(SeccionD seccionD) {
        this.seccionDS.remove(seccionD);
        seccionD.setCuestionario(null);
        return this;
    }

    public void setSeccionDS(Set<SeccionD> seccionDS) {
        if (this.seccionDS != null) {
            this.seccionDS.forEach(i -> i.setCuestionario(null));
        }
        if (seccionDS != null) {
            seccionDS.forEach(i -> i.setCuestionario(this));
        }
        this.seccionDS = seccionDS;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public Cuestionario paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuestionario)) {
            return false;
        }
        return id != null && id.equals(((Cuestionario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuestionario{" +
            "id=" + getId() +
            "}";
    }
}
