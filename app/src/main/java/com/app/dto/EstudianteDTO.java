package com.app.dto;

public class EstudianteDTO {

  private final String nombre;

  public EstudianteDTO(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "EstudianteDTO{" + "nombre='" + nombre + '\'' + '}';
  }
}
