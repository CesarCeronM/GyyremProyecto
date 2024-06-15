package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Pago;
import java.util.List;

public interface IPago {
    public int registrarPago(Pago pago);
    public List<Pago> obtenerListaPagos(int idMiembro);
    public Pago obtenerInformacionPago(int idPago);
}
