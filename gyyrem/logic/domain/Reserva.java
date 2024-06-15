package gyyrem.logic.domain;

public class Reserva {
    private int idReserva;
    private int idMiembro;
    private int idClase;
    private String fecha;
    private String estado;

    public Reserva(int idReserva, int idMiembro, int idClase, String fecha, String estado) {
        this.idReserva = idReserva;
        this.idMiembro = idMiembro;
        this.idClase = idClase;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
