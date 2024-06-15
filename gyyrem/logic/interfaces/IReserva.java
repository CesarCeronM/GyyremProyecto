package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Reserva;

public interface IReserva {
    public int reservarClase(Reserva reserva);
    public int cancelarReserva(int idReserva);
    public int pagarReserva(int idClase, int idMiembro);
}
