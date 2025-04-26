package com.app.repository;

import com.app.factory.JPAUtil;
import com.app.model.Carrera;
import com.app.model.Estudiante;
import com.app.model.Inscripcion;
import com.app.repository.interfaces.InscripcionRepositoryInterface;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Date;

public class InscripcionRepository implements InscripcionRepositoryInterface {

  // Instancia de EntityManager
  private final EntityManager em;

  // Constructor
  public InscripcionRepository() {
    this.em = JPAUtil.getEntityManager();
  }

  // Método para insertar desde un archivo CSV
  @Override
  public void insertarDesdeCSV(String rutaArchivo) {
    try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
      String[] linea;
      reader.readNext(); // salta cabecera

      em.getTransaction().begin();

      while ((linea = reader.readNext()) != null) {
        Estudiante estudiante = em.find(Estudiante.class, (linea[1]));
        if (estudiante == null) {
          break;
        }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId_inscripcion(Integer.parseInt(linea[0]));

        inscripcion.setEstudiante(estudiante);
        Carrera carrera = em.find(Carrera.class, (linea[2]));
        if (carrera == null) {
          break;
        }
        inscripcion.setCarrera(carrera);
        inscripcion.setInscripcion(Integer.parseInt(linea[3]));
        inscripcion.setGraduacion(Integer.parseInt(linea[4]));
        inscripcion.setAntiguedad(Integer.parseInt(linea[5]));
        em.merge(inscripcion);
      }

      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al insertar desde CSV: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  @Override
  public void matricularEstudiante(int idEstudiante, int idCarrera) {
    try {
      Estudiante estudiante = em.find(Estudiante.class, idEstudiante);
      Carrera carrera = em.find(Carrera.class, idCarrera);
      if (estudiante == null || carrera == null) {
        System.out.println("Estudiante o carrera no encontrados");
        return;
      }
      Inscripcion inscripcion = new Inscripcion();
      inscripcion.setEstudiante(estudiante);
      inscripcion.setCarrera(carrera);
      LocalDate date = new Date()
        .toInstant()
        .atZone(java.time.ZoneId.systemDefault())
        .toLocalDate();
      int year = date.getYear();
      inscripcion.setInscripcion(year);
      em.getTransaction().begin();
      em.persist(inscripcion);
      em.getTransaction().commit();
      System.out.println(
        "Estudiante matriculado: " +
        estudiante.getNombre() +
        " " +
        "Carrera: " +
        carrera.getNombre()
      );
    } catch (Exception e) {
      System.out.println("Error al matricular estudiante: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }
}
