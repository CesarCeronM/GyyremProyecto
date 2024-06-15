package gyyrem.logic.interfaces;

import gyyrem.logic.domain.Profesor;
import java.util.List;

public interface IProfesor {
    public List<Profesor> obtenerListaProfesores();
    public Profesor obtenerInformacionProfesores(int idProfesor);
}
