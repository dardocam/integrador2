package com.app.repository.interfaces;

public interface InscripcionRepositoryInterface {
  //   void save(Object inscripcion);

  void insertarDesdeCSV(String rutaArchivo);
  //   void eliminarEstudiantePorId(int id);

  //   void findAll();

  void matricularEstudiante(int idEstudiante, int idCarrera);
}
