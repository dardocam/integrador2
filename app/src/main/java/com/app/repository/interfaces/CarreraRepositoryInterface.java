package com.app.repository.interfaces;

import com.app.model.Carrera;

public interface CarreraRepositoryInterface {
  void save(Object carrera);

  void eliminarCarreraPorId(int id);

  void findAll();

  Carrera findByName(String nombre);

  void insertarCarreraDeCSV(String rutaArchivoCSV);
}
