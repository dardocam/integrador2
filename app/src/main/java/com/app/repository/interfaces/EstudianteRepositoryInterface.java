package com.app.repository.interfaces;

import com.app.dto.EstudianteDTO;
import java.util.ArrayList;
import java.util.List;

public interface EstudianteRepositoryInterface {
  void save(Object estudiante);

  void insertarDesdeCSV(String rutaArchivo);

  void eliminarEstudiantePorId(int id);

  ArrayList<EstudianteDTO> findAll();
  List<EstudianteDTO> findAllByGender(String genero);

  EstudianteDTO buscarPorLibretaUnivertitaria(int numeroLibreta);
}
