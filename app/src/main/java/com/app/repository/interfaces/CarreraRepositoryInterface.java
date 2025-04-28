package com.app.repository.interfaces;

import com.app.dto.CarreraDTO;
import com.app.model.Carrera;
import java.util.List;

public interface CarreraRepositoryInterface {
  void save(Object carrera);

  void eliminarCarreraPorId(int id);

  void findAll();

  Carrera findByName(String nombre);

  void insertarCarreraDeCSV(String rutaArchivoCSV);

  List<CarreraDTO> buscarTodasLasCarrerasConEstudiantesInscriptos();
}
