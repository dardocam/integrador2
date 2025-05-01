package com.app.dto;

public class EstudianteCarreraCiudadDTO {

  private final String nombre;
  private final String carrera;
  private final String ciudad;

  public EstudianteCarreraCiudadDTO(
    String nombreEstudiante,
    String nombreCarrera,
    String ciudadResidencia
  ) {
    this.nombre = nombreEstudiante;
    this.carrera = nombreCarrera;
    this.ciudad = ciudadResidencia;
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
