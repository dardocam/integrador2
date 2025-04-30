package com.app;

import com.app.dto.CarreraConInscriptosDTO;
import com.app.dto.EstudianteCarreraCiudadDTO;
import com.app.dto.EstudianteDTO;
import com.app.model.Estudiante;
import com.app.repository.CarreraRepository;
import com.app.repository.EstudianteRepository;
import com.app.repository.InscripcionRepository;
import java.util.ArrayList;

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

    // a) Dar de alta un estudiante
    Estudiante e1 = new Estudiante();
    e1.setId_estudiante(123654789);
    e1.setNombre("Leonela Luz");
    e1.setApellido("Baños");
    e1.setEdad(46);
    e1.setGenero("Femenino");
    e1.setCiudad("Necochea");
    e1.setLibreta(222222);
    estudianteRepository.save(e1);

    // b) Matricular un estudiante en una carrera
    inscripcionRepository.matricularEstudiante(e1.getId_estudiante(), 1);
    inscripcionRepository.matricularEstudiante(e.getId_estudiante(), 1);

    // c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    System.out.println("LISTA TOTAL DE ESTUDIANTES");

    ArrayList<EstudianteDTO> ejercicioC = estudianteRepository.findAll();
    System.out.println(ejercicioC);

    // d) recuperar un estudiante, en base a su número de libreta universitaria.
    System.out.println("ESTUDIANTE POR LIBRETA");

    // EstudianteDTO recuperado = estudianteRepository.buscarPorLibretaUnivertitaria(
    //   LU
    // );
    // System.out.println("Estudiante recuperado por su libreta: " + recuperado);
    // e) recuperar todos los estudiantes, en base a su género.

    System.out.println("LISTA DE ESTUDIANTES POR GENERO");

    // for (EstudianteDTO dto : estudianteRepository.findAllByGender(
    //   "Masculino"
    // )) {
    //   System.out.println(dto);
    // }
    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    System.out.println("LISTA DE CARRERAS CON INSCRIPTOS");

    ArrayList<CarreraConInscriptosDTO> listaCarreras = carreraRepository.buscarTodasLasCarrerasConEstudiantesInscriptos();
    System.out.println(listaCarreras);
    // for (CarreraConInscriptosDTO inscriptos : carreraRepository.buscarTodasLasCarrerasConEstudiantesInscriptos()) {
    //   System.out.println(inscriptos);
    // }

    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.

    ArrayList<EstudianteCarreraCiudadDTO> listaEstudiantes = estudianteRepository.buscarEstudianteCarreraCiudad(
      "TUDAI",
      "Necochea"
    );
    System.out.println("LISTA DE ESTUDIANTES POR CARRERA Y CIUDAD");
    System.out.println(listaEstudiantes);
  }
}
