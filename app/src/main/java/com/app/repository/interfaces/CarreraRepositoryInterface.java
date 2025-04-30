package com.app.repository.interfaces;

import com.app.dto.CarreraConInscriptosDTO;
import com.app.dto.CarreraDTO;
import com.app.model.Carrera;
import java.util.ArrayList;

public interface CarreraRepositoryInterface {
  void save(Object carrera);

  void eliminarCarreraPorId(int id);

  ArrayList<CarreraDTO> findAll();

  Carrera findByName(String nombre);

  void insertarCarreraDeCSV(String rutaArchivoCSV);

  ArrayList<CarreraConInscriptosDTO> buscarTodasLasCarrerasConEstudiantesInscriptos();
}
