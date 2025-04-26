package com.app;

import com.app.model.Estudiante;
import com.app.repository.CarreraRepository;
import com.app.repository.EstudianteRepository;
import com.app.repository.InscripcionRepository;

// import com.app.model.Carrera;
// import com.app.repository.EstudianteRepository;
//import com.app.utils.HelperMySql;

public class Main {

  public static void main(String[] args) {
    System.out.println("Iniciando la aplicacion");
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

    // Punto a) Dar de alta un estudiante
    Estudiante e = new Estudiante();
    e.setId_estudiante(26796903);
    e.setNombre("Dardo Diego");
    e.setApellido("Camaño");
    e.setEdad(46);
    e.setGenero("Masculino");
    e.setCiudad("Necochea");
    e.setLibreta(1111111);
    estudianteRepository.save(e);

    // Punto b) Matricular un estudiante en una carrera
    inscripcionRepository.matricularEstudiante(e.getId_estudiante(), 5);
    // c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    estudianteRepository.findAll();
    // d) recuperar un estudiante, en base a su número de libreta universitaria.
    // e) recuperar todos los estudiantes, en base a su género.
    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
  }
}
