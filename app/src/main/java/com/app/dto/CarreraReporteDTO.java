package com.app.dto;

public class CarreraReporteDTO {

  private final String nombre;
  private final int anioInscripcion;
  private final int totalInscriptos;
  private final int totalEgresados;

  public CarreraReporteDTO(
    String nombre,
    int anioInscripcion,
    int totalInscriptos,
    int totalEgresados
  ) {
    this.nombre = nombre;
    this.anioInscripcion = anioInscripcion;
    this.totalInscriptos = totalInscriptos;
    this.totalEgresados = totalEgresados;
  }

  public String getNombre() {
    return nombre;
  }

  public int getAnioInscripcion() {
    return anioInscripcion;
  }

  public int getTotalInscriptos() {
    return totalInscriptos;
  }

  public int getTotalEgresados() {
    return totalEgresados;
  }

  @Override
  public String toString() {
    return (
      "CarreraReporteDTO{" +
      "nombre='" +
      nombre +
      '\'' +
      ", anioInscripcion=" +
      anioInscripcion +
      ", totalInscriptos=" +
      totalInscriptos +
      ", totalEgresados=" +
      totalEgresados +
      '}'
    );
  }
}
// SELECT
//     c.nombre AS carrera,
//     i.anio AS año,
//     COUNT(i) AS totalInscriptos,
//     SUM(CASE WHEN i.egresado = true THEN 1 ELSE 0 END) AS totalEgresados
// FROM
//     Carrera c
// JOIN
//     c.inscriptos i  // Asume que Carrera tiene una colección "inscriptos" vinculada a la entidad Inscriptos
// GROUP BY
//     c.nombre, i.anio
// ORDER BY
//     c.nombre ASC, i.anio ASC
