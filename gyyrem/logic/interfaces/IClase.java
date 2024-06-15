package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Clase;
import java.util.List;

public interface IClase {
    public boolean existeClase(String dias, String horario, int idProfesor);
    public int registrarClase(Clase clase);
    public int modificarClase(int idClase, Clase claseNueva);
    public int cambiarEstadoALlena(int idClase);
    public int cambiarEstadoADisponible(int idClase);
    public List<Clase> obtenerListaClasesDisponibles();
    public Clase obtenerInformacionClase(int idClase);
}
