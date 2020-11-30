package frlp.utn.edu.ar.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "obra_social")
    private String obraSocial;

    @Column(name = "dni")
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "lugar_nacimiento")
    private String lugarNacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "nacio_antes_9_meses")
    private Boolean nacioAntes9Meses;

    @Column(name = "semanas_gestacion")
    private Integer semanasGestacion;

    @Column(name = "peso_al_nacer", precision = 21, scale = 2)
    private BigDecimal pesoAlNacer;

    @Column(name = "enfermedad_auditiva_lenguaje")
    private Boolean enfermedadAuditivaLenguaje;

    @Column(name = "descripcion_problema_auditivo_lenguaje")
    private String descripcionProblemaAuditivoLenguaje;

    @Column(name = "infecciones_oido")
    private Boolean infeccionesOido;

    @Column(name = "total_infecciones_anual")
    private Integer totalInfeccionesAnual;

    @Column(name = "problema_salud")
    private Boolean problemaSalud;

    @Column(name = "descripcion_problema_salud")
    private String descripcionProblemaSalud;

    @Column(name = "nombre_madre")
    private String nombreMadre;

    @Column(name = "edad_madre")
    private Integer edadMadre;

    @Column(name = "lugar_origen_madre")
    private String lugarOrigenMadre;

    @Column(name = "nombre_padre")
    private String nombrePadre;

    @Column(name = "edad_padre")
    private Integer edadPadre;

    @Column(name = "lugar_origen_padre")
    private String lugarOrigenPadre;

    @OneToMany(mappedBy = "paciente")
    private Set<Cuestionario> cuestionarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public Paciente nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Paciente apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public Paciente obraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
        return this;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getDni() {
        return dni;
    }

    public Paciente dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Paciente fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public Paciente lugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
        return this;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public Paciente genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean isNacioAntes9Meses() {
        return nacioAntes9Meses;
    }

    public Paciente nacioAntes9Meses(Boolean nacioAntes9Meses) {
        this.nacioAntes9Meses = nacioAntes9Meses;
        return this;
    }

    public void setNacioAntes9Meses(Boolean nacioAntes9Meses) {
        this.nacioAntes9Meses = nacioAntes9Meses;
    }

    public Integer getSemanasGestacion() {
        return semanasGestacion;
    }

    public Paciente semanasGestacion(Integer semanasGestacion) {
        this.semanasGestacion = semanasGestacion;
        return this;
    }

    public void setSemanasGestacion(Integer semanasGestacion) {
        this.semanasGestacion = semanasGestacion;
    }

    public BigDecimal getPesoAlNacer() {
        return pesoAlNacer;
    }

    public Paciente pesoAlNacer(BigDecimal pesoAlNacer) {
        this.pesoAlNacer = pesoAlNacer;
        return this;
    }

    public void setPesoAlNacer(BigDecimal pesoAlNacer) {
        this.pesoAlNacer = pesoAlNacer;
    }

    public Boolean isEnfermedadAuditivaLenguaje() {
        return enfermedadAuditivaLenguaje;
    }

    public Paciente enfermedadAuditivaLenguaje(Boolean enfermedadAuditivaLenguaje) {
        this.enfermedadAuditivaLenguaje = enfermedadAuditivaLenguaje;
        return this;
    }

    public void setEnfermedadAuditivaLenguaje(Boolean enfermedadAuditivaLenguaje) {
        this.enfermedadAuditivaLenguaje = enfermedadAuditivaLenguaje;
    }

    public String getDescripcionProblemaAuditivoLenguaje() {
        return descripcionProblemaAuditivoLenguaje;
    }

    public Paciente descripcionProblemaAuditivoLenguaje(String descripcionProblemaAuditivoLenguaje) {
        this.descripcionProblemaAuditivoLenguaje = descripcionProblemaAuditivoLenguaje;
        return this;
    }

    public void setDescripcionProblemaAuditivoLenguaje(String descripcionProblemaAuditivoLenguaje) {
        this.descripcionProblemaAuditivoLenguaje = descripcionProblemaAuditivoLenguaje;
    }

    public Boolean isInfeccionesOido() {
        return infeccionesOido;
    }

    public Paciente infeccionesOido(Boolean infeccionesOido) {
        this.infeccionesOido = infeccionesOido;
        return this;
    }

    public void setInfeccionesOido(Boolean infeccionesOido) {
        this.infeccionesOido = infeccionesOido;
    }

    public Integer getTotalInfeccionesAnual() {
        return totalInfeccionesAnual;
    }

    public Paciente totalInfeccionesAnual(Integer totalInfeccionesAnual) {
        this.totalInfeccionesAnual = totalInfeccionesAnual;
        return this;
    }

    public void setTotalInfeccionesAnual(Integer totalInfeccionesAnual) {
        this.totalInfeccionesAnual = totalInfeccionesAnual;
    }

    public Boolean isProblemaSalud() {
        return problemaSalud;
    }

    public Paciente problemaSalud(Boolean problemaSalud) {
        this.problemaSalud = problemaSalud;
        return this;
    }

    public void setProblemaSalud(Boolean problemaSalud) {
        this.problemaSalud = problemaSalud;
    }

    public String getDescripcionProblemaSalud() {
        return descripcionProblemaSalud;
    }

    public Paciente descripcionProblemaSalud(String descripcionProblemaSalud) {
        this.descripcionProblemaSalud = descripcionProblemaSalud;
        return this;
    }

    public void setDescripcionProblemaSalud(String descripcionProblemaSalud) {
        this.descripcionProblemaSalud = descripcionProblemaSalud;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public Paciente nombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
        return this;
    }

    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }

    public Integer getEdadMadre() {
        return edadMadre;
    }

    public Paciente edadMadre(Integer edadMadre) {
        this.edadMadre = edadMadre;
        return this;
    }

    public void setEdadMadre(Integer edadMadre) {
        this.edadMadre = edadMadre;
    }

    public String getLugarOrigenMadre() {
        return lugarOrigenMadre;
    }

    public Paciente lugarOrigenMadre(String lugarOrigenMadre) {
        this.lugarOrigenMadre = lugarOrigenMadre;
        return this;
    }

    public void setLugarOrigenMadre(String lugarOrigenMadre) {
        this.lugarOrigenMadre = lugarOrigenMadre;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public Paciente nombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
        return this;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public Integer getEdadPadre() {
        return edadPadre;
    }

    public Paciente edadPadre(Integer edadPadre) {
        this.edadPadre = edadPadre;
        return this;
    }

    public void setEdadPadre(Integer edadPadre) {
        this.edadPadre = edadPadre;
    }

    public String getLugarOrigenPadre() {
        return lugarOrigenPadre;
    }

    public Paciente lugarOrigenPadre(String lugarOrigenPadre) {
        this.lugarOrigenPadre = lugarOrigenPadre;
        return this;
    }

    public void setLugarOrigenPadre(String lugarOrigenPadre) {
        this.lugarOrigenPadre = lugarOrigenPadre;
    }

    public Set<Cuestionario> getCuestionarios() {
        return cuestionarios;
    }

    public Paciente cuestionarios(Set<Cuestionario> cuestionarios) {
        this.cuestionarios = cuestionarios;
        return this;
    }

    public Paciente addCuestionario(Cuestionario cuestionario) {
        this.cuestionarios.add(cuestionario);
        cuestionario.setPaciente(this);
        return this;
    }

    public Paciente removeCuestionario(Cuestionario cuestionario) {
        this.cuestionarios.remove(cuestionario);
        cuestionario.setPaciente(null);
        return this;
    }

    public void setCuestionarios(Set<Cuestionario> cuestionarios) {
        this.cuestionarios = cuestionarios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", obraSocial='" + getObraSocial() + "'" +
            ", dni='" + getDni() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", lugarNacimiento='" + getLugarNacimiento() + "'" +
            ", genero='" + getGenero() + "'" +
            ", nacioAntes9Meses='" + isNacioAntes9Meses() + "'" +
            ", semanasGestacion=" + getSemanasGestacion() +
            ", pesoAlNacer=" + getPesoAlNacer() +
            ", enfermedadAuditivaLenguaje='" + isEnfermedadAuditivaLenguaje() + "'" +
            ", descripcionProblemaAuditivoLenguaje='" + getDescripcionProblemaAuditivoLenguaje() + "'" +
            ", infeccionesOido='" + isInfeccionesOido() + "'" +
            ", totalInfeccionesAnual=" + getTotalInfeccionesAnual() +
            ", problemaSalud='" + isProblemaSalud() + "'" +
            ", descripcionProblemaSalud='" + getDescripcionProblemaSalud() + "'" +
            ", nombreMadre='" + getNombreMadre() + "'" +
            ", edadMadre=" + getEdadMadre() +
            ", lugarOrigenMadre='" + getLugarOrigenMadre() + "'" +
            ", nombrePadre='" + getNombrePadre() + "'" +
            ", edadPadre=" + getEdadPadre() +
            ", lugarOrigenPadre='" + getLugarOrigenPadre() + "'" +
            "}";
    }
}
