package com.app.dto;

import com.app.model.Estudiante;

public class EstudianteDTO {

  private final String nombre;
  private final int libreta;
  private final String genero;

  public EstudianteDTO(Estudiante e) {
    this.nombre = e.getNombre();
    this.libreta = e.getLibreta();
    this.genero = e.getGenero();
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return (
      "EstudianteDTO{" +
      "nombre='" +
      nombre +
      "' " +
      "libreta=" +
      libreta +
      ' ' +
      "genero=" +
      genero +
      ' ' +
      '}'
    );
  }
}
