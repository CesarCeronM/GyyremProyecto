package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Membresia;

public interface IMembresia {
    public int registrarMembresia(Membresia membresia);
    public int expirarMembresia(int idMembresia);
    public int pagarMembresia(int idMiembro);
    public Membresia membresiaEstaPagada(int idMiembro);
}
