package com.app.repository;

import com.app.dto.EstudianteCarreraCiudadDTO;
import com.app.dto.EstudianteDTO;
import com.app.factory.JPAUtil;
import com.app.model.Carrera;
import com.app.model.Estudiante;
import com.app.model.Inscripcion;
import com.app.repository.interfaces.EstudianteRepositoryInterface;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepository implements EstudianteRepositoryInterface {

  // Instancia de EntityManager
  private final EntityManager em;

  // Constructor
  public EstudianteRepository() {
    this.em = JPAUtil.getEntityManager();
  }

  // CREATE o UPDATE
  @Override
  public void save(Object estudiante) {
    try {
      em.getTransaction().begin();
      // Verificar si ya existe un registro con el mismo nombre
      Estudiante existente = em
        .createQuery(
          "SELECT e FROM Estudiante e WHERE e.id_estudiante = :dni",
          Estudiante.class
        )
        .setParameter("dni", ((Estudiante) estudiante).getId_estudiante())
        .getResultStream()
        .findFirst()
        .orElse(null);

      if (existente != null) {
        em.merge(existente);
        System.out.println("Estudiante actualizado: " + existente.getNombre());
      } else {
        em.persist(estudiante);
        System.out.println(
          "Estudiante almacenado: " + ((Estudiante) estudiante).getNombre()
        );
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error almacenando el estudiante: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  // Método para insertar desde un archivo CSV
  @Override
  public void insertarDesdeCSV(String rutaArchivo) {
    try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
      String[] linea;
      reader.readNext(); // salta cabecera

      em.getTransaction().begin();

      while ((linea = reader.readNext()) != null) {
        Estudiante estudiante = new Estudiante();
        estudiante.setId_estudiante(Integer.parseInt(linea[0]));
        estudiante.setNombre(linea[1]);
        estudiante.setApellido(linea[2]);
        estudiante.setEdad(Integer.parseInt(linea[3]));
        estudiante.setGenero(linea[4]);
        estudiante.setCiudad(linea[5]);
        estudiante.setLibreta(Integer.parseInt(linea[6]));

        em.merge(estudiante);
      }

      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al insertar desde CSV: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  // Método para eliminar una carrera
  @Override
  public void eliminarEstudiantePorId(int id) {
    try {
      em.getTransaction().begin();
      Estudiante estudiante = em.find(Estudiante.class, id);
      if (estudiante != null) {
        em.remove(estudiante);
        System.out.println("Estudiante eliminado: " + estudiante.getNombre());
      } else {
        System.out.println("Estudiante no encontrado.");
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al eliminar el estudiante: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  // Método para encontrar todos los estudiantes
  @Override
  public ArrayList<EstudianteDTO> findAll() {
    ArrayList<EstudianteDTO> resultado = new ArrayList<>();
    try {
      em.getTransaction().begin();
      List<Estudiante> estudiantes = em
        .createQuery(
          "SELECT e FROM Estudiante e ORDER BY e.nombre ASC",
          Estudiante.class
        )
        .getResultList();
      em.getTransaction().commit();
      for (Estudiante estudiante : estudiantes) {
        resultado.add(new EstudianteDTO(estudiante));
      }
    } catch (Exception e) {
      System.out.println(
        "Error al encontrar todas las carreras: " + e.getMessage()
      );
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return resultado;
  }

  // b) Matricular un estudiante en una carrera
  public void matricularEstudiante(
    Estudiante estudiante,
    Carrera carrera,
    int anioInscripcion,
    int anioGraduacion,
    int aniosAntiguedad
  ) {
    try {
      em.getTransaction().begin();
      // recupero instancias ya existentes
      if (estudiante != null) {
        Inscripcion i = new Inscripcion();
        i.setEstudiante(estudiante);
        i.setCarrera(carrera);
        i.setGraduacion(anioGraduacion);
        i.setInscripcion(anioInscripcion);
        i.setAntiguedad(aniosAntiguedad);
        em.persist(i);
        // Aquí puedes agregar la lógica para matricular al estudiante en el curso
        EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante);

        System.out.println("Estudiante matriculado: " + estudianteDTO);
      } else {
        System.out.println("Estudiante no encontrado.");
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(
        "Error al matricular al estudiante: " + e.getMessage()
      );
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
  }

  @Override
  // recuperar un estudiante, en base a su número de libreta universitaria.
  public EstudianteDTO buscarPorLibretaUnivertitaria(int numeroLibreta) {
    Estudiante estudiante = null;
    try (em) {
      String jpql = "SELECT e from Estudiante e WHERE e.libreta = :libreta";
      em.getTransaction().begin();
      estudiante =
        em
          .createQuery(jpql, Estudiante.class)
          .setParameter("libreta", numeroLibreta)
          .getSingleResult();
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println("Error al recuperar un estudiante: " + e.getMessage());
    }
    if (estudiante == null) {
      return new EstudianteDTO(null);
    }
    EstudianteDTO vista = new EstudianteDTO(estudiante);
    return vista;
  }

  @Override
  // recuperar todos los estudiantes, en base a su género.
  public List<EstudianteDTO> findAllByGender(String genero) {
    List<EstudianteDTO> resultado = new ArrayList<>();
    try {
      List<Estudiante> listaEstudiantes;
      String jpql =
        "SELECT e " +
        "FROM Estudiante e " +
        "WHERE e.genero = :gen " +
        "ORDER BY e.nombre";
      listaEstudiantes =
        em
          .createQuery(jpql, Estudiante.class)
          .setParameter("gen", genero)
          .getResultList();

      if (listaEstudiantes != null) {
        for (Estudiante estudiante : listaEstudiantes) {
          resultado.add(new EstudianteDTO(estudiante));
        }
      }
    } catch (Exception e) {
      System.out.println("Error al recuperar un estudiante: " + e.getMessage());
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return resultado;
  }

  //recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
  @Override
  public ArrayList<EstudianteCarreraCiudadDTO> buscarEstudianteCarreraCiudad(
    String nombreCarrera,
    String ciudadResidencia
  ) {
    ArrayList<EstudianteCarreraCiudadDTO> estudiantes = new ArrayList<>();
    try {
      String query =
        "SELECT new EstudianteCarreraCiudadDTO(e,c) " +
        "FROM Estudiante e " +
        "JOIN e.inscripcion i " + //// Asume que la entidad Estudiante tiene una colección "inscriptos" de tipo Inscriptos
        "JOIN i.carrera c " + //// Asume que la entidad Inscripcion tiene una referencia a Carrera
        "WHERE " +
        "c.nombre = :nombreCarrera " +
        "AND e.ciudad = :ciudadResidencia";

      em.getTransaction().begin();
      List<EstudianteCarreraCiudadDTO> resultados = em
        .createQuery(query, EstudianteCarreraCiudadDTO.class)
        .setParameter("nombreCarrera", nombreCarrera)
        .setParameter("ciudadResidencia", ciudadResidencia)
        .getResultList();

      for (EstudianteCarreraCiudadDTO resultado : resultados) {
        estudiantes.add(resultado);
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(
        "Error al encontrar todos los estudiantes de una determinada carrera: " +
        e.getMessage()
      );
      if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
      }
    }
    return estudiantes;
  }
}
