package com.app.repository;

import com.app.dto.CarreraConInscriptosDTO;
import com.app.dto.CarreraDTO;
import com.app.dto.CarreraReporteDTO;
import com.app.factory.JPAUtil;
import com.app.model.Carrera;
import com.app.repository.interfaces.CarreraRepositoryInterface;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepository implements CarreraRepositoryInterface {

  // Instancia de EntityManager
  private final EntityManager em;

  // Constructor
  public CarreraRepository() {
    this.em = JPAUtil.getEntityManager();
  }

  // CREATE o UPDATE
  @Override
  public void save(Object carrera) {
    try {
      em.getTransaction().begin();
      // Verificar si ya existe un registro con el mismo nombre
      Carrera existente = em
        .createQuery(
          "SELECT c FROM Carrera c WHERE c.nombre = :nombre",
          Carrera.class
        )
        .setParameter("nombre", ((Carrera) carrera).getNombre())
        .getResultStream()
        .findFirst()
        .orElse(null);

      if (existente != null) {
        // Actualizar el registro existente
        existente.setNombre(((Carrera) carrera).getNombre());
        // Aquí puedes actualizar otros campos si es necesario
        em.merge(existente);
        System.out.println("Carrera actualizada: " + existente.getNombre());
      } else {
        // Insertar un nuevo registro
        em.persist(carrera);
        System.out.println(
          "Carrera guardada exitosamente: " + ((Carrera) carrera).getNombre()
        );
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al guardar la carrera: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  // Método para eliminar una carrera
  @Override
  public void eliminarCarreraPorId(int id) {
    try {
      em.getTransaction().begin();
      Carrera carreraAEliminar = em.find(Carrera.class, id);
      if (carreraAEliminar != null) {
        em.remove(carreraAEliminar);
        System.out.println(
          "Carrera eliminada: " + carreraAEliminar.getNombre()
        );
      } else {
        System.out.println("Carrera no encontrada.");
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al eliminar la carrera: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  // Método para encontrar todas las carreras
  @Override
  public ArrayList<CarreraDTO> findAll() {
    ArrayList<CarreraDTO> resultado = new ArrayList<>();
    try {
      List<Carrera> carreras = em
        .createQuery("SELECT c FROM Carrera c", Carrera.class)
        .getResultList();

      for (Carrera carrera : carreras) {
        System.out.println(carrera);
        resultado.add(new CarreraDTO(carrera));
      }
    } catch (Exception e) {
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return resultado;
  }

  @Override
  public Carrera findByName(String nombre) {
    try {
      em.getTransaction().begin();
      Carrera carrera = em
        .createQuery(
          "SELECT c FROM Carrera c WHERE c.nombre = :nombre",
          Carrera.class
        )
        .setParameter("nombre", nombre)
        .getSingleResult();
      em.getTransaction().commit();
      return carrera;
    } catch (Exception e) {
      System.out.println("Error al encontrar la carrera: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return null;
  }

  @Override
  public void insertarCarreraDeCSV(String rutaArchivoCSV) {
    try (CSVReader reader = new CSVReader(new FileReader(rutaArchivoCSV))) {
      String[] linea;
      reader.readNext(); // salta cabecera

      em.getTransaction().begin();

      while ((linea = reader.readNext()) != null) {
        Carrera carrera = new Carrera();
        carrera.setId_carrera(Integer.parseInt(linea[0]));
        carrera.setNombre(linea[1]);
        carrera.setDuracion(Integer.parseInt(linea[2]));

        em.merge(carrera);
        // El metodo persist() devuelve el siguiente error :(
        // Error al insertar desde CSV: detached entity passed to persist: com.app.model.Carrera
      }

      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al insertar desde CSV: " + e.getMessage());
    }
  }

  // Método para encontrar todas las carreras con estudiantes inscriptos
  @Override
  public ArrayList<CarreraConInscriptosDTO> buscarTodasLasCarrerasConEstudiantesInscriptos() {
    ArrayList<CarreraConInscriptosDTO> carrerasConEstudiantes = new ArrayList<>();
    try {
      String query =
        "SELECT new CarreraConInscriptosDTO(c.nombre, COUNT(i)) " +
        "FROM Carrera c " +
        "JOIN c.inscripcion i " +
        "GROUP BY c.id_carrera, c.nombre " +
        "ORDER BY COUNT(i) DESC";

      em.getTransaction().begin();
      List<CarreraConInscriptosDTO> resultados = em
        .createQuery(query, CarreraConInscriptosDTO.class)
        .getResultList();

      for (CarreraConInscriptosDTO resultado : resultados) {
        carrerasConEstudiantes.add(resultado);
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(
        "Error al encontrar todas las carreras: " + e.getMessage()
      );
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return carrerasConEstudiantes;
  }

  //Generar un reporte de las carreras, que para cada carrera incluya información de los
  //inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
  //los años de manera cronológica.
  @Override
  public ArrayList<CarreraReporteDTO> reporteCarreras() {
    ArrayList<CarreraReporteDTO> estudiantes = new ArrayList<>();
    try {
      String query =
        "SELECT new CarreraReporteDTO(c.nombre, i.inscripcion, COUNT(i),SUM(CASE WHEN i.graduacion > 0 THEN 1 ELSE 0 END) ) " +
        "FROM Carrera c " +
        "JOIN c.inscripcion i " + //// Asume que la entidad Carrera tiene una colección "inscriptos" de tipo Inscriptos
        "GROUP BY " +
        "c.nombre, i.inscripcion " +
        "ORDER BY c.nombre ASC, i.inscripcion ASC";

      em.getTransaction().begin();
      List<CarreraReporteDTO> resultados = em
        .createQuery(query, CarreraReporteDTO.class)
        .getResultList();

      for (CarreraReporteDTO resultado : resultados) {
        estudiantes.add(resultado);
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error general: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return estudiantes;
  }
}
