package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Miembro;
import java.util.List;

public interface IMiembro {
    public int existeMiembro(String nombre, String apellidoPaterno);
    public int registrarMiembro(Miembro miembro);
    public int modificarMiembro(int idMiembro, Miembro miembroNuevo);
    public int suspenderMiembro(int idMiembro);
    public int activarMiembro(int idMiembro);
    public List<Miembro> obtenerListaMiembro();
    public List<Miembro> obtenerListaMiembrosSuspendidos();
    public Miembro obtenerInformacionMiembro(int idMiembro);
}
