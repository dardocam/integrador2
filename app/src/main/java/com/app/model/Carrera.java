package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Carrera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carrera {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id_carrera;

  @Column(name = "nombre", nullable = true)
  private String nombre;

  @Column(name = "duracion", nullable = true)
  private int duracion;

  @OneToMany(mappedBy = "carrera")
  private Set<Inscripcion> inscripcion;
}
