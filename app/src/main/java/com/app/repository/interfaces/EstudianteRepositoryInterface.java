package com.app.repository.interfaces;

public interface EstudianteRepositoryInterface {
  void save(Object estudiante);

  void insertarDesdeCSV(String rutaArchivo);

  void eliminarEstudiantePorId(int id);

  void findAll();
}
