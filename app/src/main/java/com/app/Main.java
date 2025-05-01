package com.app;

import com.app.dto.CarreraConInscriptosDTO;
import com.app.dto.CarreraReporteDTO;
import com.app.dto.EstudianteCarreraCiudadDTO;
import com.app.dto.EstudianteDTO;
import com.app.factory.JPAUtil;
import com.app.model.Estudiante;
import com.app.repository.CarreraRepository;
import com.app.repository.EstudianteRepository;
import com.app.repository.InscripcionRepository;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    System.out.println("*******************************************");

    // INICIO DE LA APLICACION
    System.out.println("*******************************************");
    System.out.println("START APPLICATION");
    System.out.println("*******************************************");
    // POBLANDO LA BASE DE DATOS tp2-integrador
    System.out.println("Poblando la base de datos");
    System.out.println("*******************************************");

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
    System.out.println("*******************************************");

    System.out.println("DB poblada con éxito");

    System.out.println("*******************************************");
    System.out.println("*******************************************");

    System.out.println("OPERACIONES DEL USUARIO");

    System.out.println("*******************************************");
    System.out.println("*******************************************");

    System.out.println("DAR DE ALTA DOS ESTUDIANTES");

    System.out.println("*******************************************");
    // OPERACIONES DEL USUARIO
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

    System.out.println("*******************************************");

    System.out.println("MATRICULARLOS EN LA CARRERA TUDAI");

    System.out.println("*******************************************");
    // b) Matricular un estudiante en una carrera
    inscripcionRepository.matricularEstudiante(e1.getId_estudiante(), 1);
    inscripcionRepository.matricularEstudiante(e.getId_estudiante(), 1);

    System.out.println("*******************************************");

    System.out.println("CONSULTAS");

    System.out.println("*******************************************");

    // c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.

    ArrayList<EstudianteDTO> todosLosEstudiantes = estudianteRepository.findAll();
    System.out.println("*******************************************");
    System.out.println("LISTA TOTAL DE ESTUDIANTES");
    System.out.println("*******************************************");

    System.out.println(todosLosEstudiantes);

    // d) recuperar un estudiante, en base a su número de libreta universitaria.

    System.out.println("*******************************************");
    System.out.println("ESTUDIANTE POR LIBRETA");
    System.out.println("*******************************************");

    System.out.println(estudianteRepository.buscarPorLibretaUnivertitaria(LU));

    // e) recuperar todos los estudiantes, en base a su género.

    System.out.println("*******************************************");
    System.out.println("LISTA DE ESTUDIANTES POR GENERO");
    System.out.println("*******************************************");

    ArrayList<EstudianteDTO> estudiantesPorGenero = estudianteRepository.findAllByGender(
      "Masculino"
    );
    System.out.println(estudiantesPorGenero);

    // f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

    ArrayList<CarreraConInscriptosDTO> listaCarreras = carreraRepository.buscarTodasLasCarrerasConEstudiantesInscriptos();
    System.out.println("*******************************************");
    System.out.println("LISTA DE CARRERAS CON INSCRIPTOS");
    System.out.println("*******************************************");

    System.out.println(listaCarreras);

    // g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.

    ArrayList<EstudianteCarreraCiudadDTO> listaEstudiantes = estudianteRepository.buscarEstudianteCarreraCiudad(
      "TUDAI",
      "Necochea"
    );
    System.out.println("*******************************************");
    System.out.println("LISTA DE ESTUDIANTES POR CARRERA Y CIUDAD");
    System.out.println("*******************************************");

    System.out.println(listaEstudiantes);

    // Punto 3)
    // Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
    // los años de manera cronológica.
    ArrayList<CarreraReporteDTO> listaCarreraReporte = carreraRepository.reporteCarreras();
    System.out.println("*******************************************");
    System.out.println("REPORTE DE CARRERAS");
    System.out.println("*******************************************");

    System.out.println(listaCarreraReporte);

    System.out.println("*******************************************");
    System.out.println("END APPLICATION - CIERRE DE CONEXION");
    System.out.println("*******************************************");
    JPAUtil.close();
  }
}
