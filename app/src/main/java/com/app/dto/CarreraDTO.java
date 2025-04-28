package com.app.dto;

import com.app.model.Carrera;

public class CarreraDTO {

  private final String nombre;

  public CarreraDTO(Carrera c) {
    this.nombre = c.getNombre();
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "CarreraDTO{" + "nombre='" + nombre + '\'' + '}';
  }
}
