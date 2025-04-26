package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Estudiante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Estudiante {

  @Id
  @Column(name = "DNI", nullable = false)
  private int id_estudiante;

  @Column(name = "nombre", nullable = true)
  private String nombre;

  @Column(name = "apellido", nullable = true)
  private String apellido;

  @Column(name = "edad", nullable = true)
  private int edad;

  @Column(name = "genero", nullable = true)
  private String genero;

  @Column(name = "ciudad", nullable = true)
  private String ciudad;

  @Column(name = "LU", nullable = true)
  private int libreta;

  @OneToMany(mappedBy = "estudiante")
  private Set<Inscripcion> inscripcion;
}
