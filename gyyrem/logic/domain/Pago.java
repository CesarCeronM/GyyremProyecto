package gyyrem.logic.domain;

public class Pago {
    private int idPago;
    private int monto;
    private String fecha;
    private String descripcion;
    private int idMiembro;
    private String nombreMiembro;

    public Pago(int idPago, int monto, String fecha, String descripcion, int idMiembro) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha=fecha;
        this.descripcion = descripcion;
        this.idMiembro = idMiembro;
    }
    
    public Pago(int idPago, int monto, String fecha, String descripcion, int idMiembro, String nombreMiembro) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha=fecha;
        this.descripcion = descripcion;
        this.idMiembro = idMiembro;
        this.nombreMiembro=nombreMiembro;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String nombreMiembro) {
        this.nombreMiembro = nombreMiembro;
    }
}