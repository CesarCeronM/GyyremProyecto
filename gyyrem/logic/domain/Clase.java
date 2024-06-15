package gyyrem.logic.domain;

public class Clase {
    private int idClase;
    private int cupo;
    private String nombre;
    private String horario;
    private String estado;
    private int costo;
    private int idProfesor;

    public Clase(int idClase, int cupo, String nombre, String horario, String estado, int costo, int idProfesor) {
        this.idClase = idClase;
        this.cupo = cupo;
        this.nombre = nombre;
        this.horario = horario;
        this.estado = estado;
        this.costo = costo;
        this.idProfesor = idProfesor;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
}
