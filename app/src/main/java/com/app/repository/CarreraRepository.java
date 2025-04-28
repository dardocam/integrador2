package com.app.repository;

import com.app.dto.CarreraDTO;
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
  public void findAll() {
    try {
      em.getTransaction().begin();
      List<Carrera> carreras = em
        .createQuery("SELECT c FROM Carrera c", Carrera.class)
        .getResultList();
      for (Carrera carrera : carreras) {
        CarreraDTO carreraDTO = new CarreraDTO(carrera);
        System.out.println(carreraDTO);
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
    } finally {
      em.close();
    }
  }

  @Override
  // recuperar todos los estudiantes, en base a su género.
  public List<CarreraDTO> buscarTodasLasCarrerasConEstudiantesInscriptos() {
    List<CarreraDTO> resultado = new ArrayList<>();
    try {
      em.getTransaction().begin();
      List<Carrera> listaCarreras;
      String jpql = "SELECT c" + "FROM Carrera c";
      // "ORDER BY COUNT(i) DESC";
      listaCarreras = em.createQuery(jpql, Carrera.class).getResultList();
      em.getTransaction().commit();

      if (listaCarreras != null) {
        for (Carrera carrera : listaCarreras) {
          resultado.add(new CarreraDTO(carrera));
        }
      }
    } catch (Exception e) {
      System.out.println("Error al recuperar un estudiante: " + e.getMessage());
    }
    return resultado;
  }
}
