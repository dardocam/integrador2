package com.app;

import com.app.dto.CarreraDTO;
import com.app.dto.EstudianteDTO;
import com.app.model.Estudiante;
import com.app.repository.CarreraRepository;
import com.app.repository.EstudianteRepository;
import com.app.repository.InscripcionRepository;

// import com.app.model.Carrera;
// import com.app.repository.EstudianteRepository;
//import com.app.utils.HelperMySql;

public class Main {

  public static void main(String[] args) {
    // INICIO
    System.out.println("Iniciando la aplicacion");
    // POBLANDO LA DB
    System.out.println("Poblando la base de datos");
    CarreraRepository carreraRepository = new CarreraRepository();
    carreraRepository.insertarCarreraDeCSV(
      "src/main/resources/populateDB/carreras.csv"
    );
    EstudianteRepository estudianteRepository = new EstudianteRepository();
    estudianteRepository.insertarDesdeCSV(
      "src/main/resources/populateDB/estudiantes.csv"
    );
    InscripcionRepository inscripcionRepository = new InscripcionRepository();
    inscripcionRepository.insertarDesdeCSV(
      "src/main/resources/populateDB/estudianteCarrera.csv"
    );
    System.out.println("DB poblada con éxito");
    // OPERACIONES
    int LU = 111111; //libreta universitaria
    // a) Dar de alta un estudiante
    Estudiante e = new Estudiante();
    e.setId_estudiante(26796903);
    e.setNombre("Dardo Diego");
    e.setApellido("Camaño");
    e.setEdad(46);
    e.setGenero("Masculino");
    e.setCiudad("Necochea");
    e.setLibreta(LU);
    estudianteRepository.save(e);

    // b) Matricular un estudiante en una carrera
    inscripcionRepository.matricularEstudiante(e.getId_estudiante(), 5);
    // c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    System.out.println("LISTA TOTAL DE ESTUDIANTES");

    for (EstudianteDTO e1 : estudianteRepository.findAll()) {
      System.out.println(e1);
    }

    // d) recuperar un estudiante, en base a su número de libreta universitaria.
    System.out.println("ESTUDIANTE POR LIBRETA");

    EstudianteDTO recuperado = estudianteRepository.buscarPorLibretaUnivertitaria(
      LU
    );
    System.out.println("Estudiante recuperado por su libreta: " + recuperado);
    // e) recuperar todos los estudiantes, en base a su género.

    System.out.println("LISTA DE ESTUDIANTES POR GENERO");

    for (EstudianteDTO dto : estudianteRepository.findAllByGender(
      "Masculino"
    )) {
      System.out.println(dto);
    }
    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    System.out.println("LISTA DE CARRERAS CON INSCRIPTOS");

    for (CarreraDTO inscriptos : carreraRepository.buscarTodasLasCarrerasConEstudiantesInscriptos()) {
      System.out.println(inscriptos);
    }
    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
  }
}
