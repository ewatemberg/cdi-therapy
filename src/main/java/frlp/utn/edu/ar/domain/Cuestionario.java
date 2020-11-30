package frlp.utn.edu.ar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<SeccionA> seccionAS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    private Set<SeccionB> seccionBS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    private Set<SeccionC> seccionCS = new HashSet<>();

    @OneToMany(mappedBy = "cuestionario")
    private Set<SeccionD> seccionDS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cuestionarios", allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SeccionA> getSeccionAS() {
        return seccionAS;
    }

    public Cuestionario seccionAS(Set<SeccionA> seccionAS) {
        this.seccionAS = seccionAS;
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
        this.seccionAS = seccionAS;
    }

    public Set<SeccionB> getSeccionBS() {
        return seccionBS;
    }

    public Cuestionario seccionBS(Set<SeccionB> seccionBS) {
        this.seccionBS = seccionBS;
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
        this.seccionBS = seccionBS;
    }

    public Set<SeccionC> getSeccionCS() {
        return seccionCS;
    }

    public Cuestionario seccionCS(Set<SeccionC> seccionCS) {
        this.seccionCS = seccionCS;
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
        this.seccionCS = seccionCS;
    }

    public Set<SeccionD> getSeccionDS() {
        return seccionDS;
    }

    public Cuestionario seccionDS(Set<SeccionD> seccionDS) {
        this.seccionDS = seccionDS;
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
        this.seccionDS = seccionDS;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Cuestionario paciente(Paciente paciente) {
        this.paciente = paciente;
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
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuestionario{" +
            "id=" + getId() +
            "}";
    }
}
