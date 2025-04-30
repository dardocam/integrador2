package com.app.dto;

public class CarreraConInscriptosDTO {

  private String nombre;
  private Long cantidadInscriptos;

  public CarreraConInscriptosDTO(String nombre, Long cantidadInscriptos) {
    this.nombre = nombre;
    this.cantidadInscriptos = cantidadInscriptos;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getCantidadInscriptos() {
    return cantidadInscriptos;
  }

  public void setCantidadInscriptos(Long cantidadInscriptos) {
    this.cantidadInscriptos = cantidadInscriptos;
  }

  @Override
  public String toString() {
    return (
      "CarreraConInscriptosDTO{" +
      "nombre='" +
      nombre +
      '\'' +
      ", cantidadInscriptos=" +
      cantidadInscriptos +
      '}'
    );
  }
}
