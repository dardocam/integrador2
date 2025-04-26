package com.app.dto;

public class CarreraDTO {

  private final String nombre;

  public CarreraDTO(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "CarreraDTO{" + "nombre='" + nombre + '\'' + '}';
  }
}
