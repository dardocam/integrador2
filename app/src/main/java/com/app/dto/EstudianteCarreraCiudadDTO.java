package com.app.dto;

import com.app.model.Carrera;
import com.app.model.Estudiante;

public class EstudianteCarreraCiudadDTO {

  private final String nombre;
  private final String carrera;
  private final String ciudad;

  public EstudianteCarreraCiudadDTO(Estudiante e, Carrera c) {
    this.nombre = e.getNombre();
    this.carrera = c.getNombre();
    this.ciudad = e.getCiudad();
  }

  public String getNombre() {
    return nombre;
  }

  public String getCarrera() {
    return carrera;
  }

  public String getCiudad() {
    return ciudad;
  }

  @Override
  public String toString() {
    return (
      "EstudianteCarreraCiudadDTO{" +
      "nombre='" +
      nombre +
      '\'' +
      "carrera=" +
      carrera +
      "ciudad=" +
      ciudad +
      '}'
    );
  }
}
