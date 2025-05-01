package com.app.dto;

public class CarreraReporteDTO {

  private final String nombreCarrera;
  private final int anioInscripcion;
  private final Long totalInscriptos;
  private final Long totalEgresados;

  public CarreraReporteDTO(
    String nombreCarrera,
    int anioInscripcion,
    Long totalInscriptos,
    Long totalEgresados
  ) {
    this.nombreCarrera = nombreCarrera;
    this.anioInscripcion = anioInscripcion;
    this.totalInscriptos = totalInscriptos;
    this.totalEgresados = totalEgresados;
  }

  @Override
  public String toString() {
    return (
      "CarreraReporteDTO{" +
      "nombre='" +
      nombreCarrera +
      '\'' +
      "Año de Inscripcion=" +
      anioInscripcion +
      "Total de Inscriptos=" +
      totalInscriptos +
      "Total de Egresados=" +
      totalEgresados +
      '}'
    );
  }
}
