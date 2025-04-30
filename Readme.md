# TPN2 INTEGRADOR JPA y CONSULTAS JPQL 

SELECT 
    c.id_carrera, 
    c.nombre AS carrera,
    COUNT(*) AS cantidad_inscriptos
FROM 
    Carrera c
INNER JOIN 
    Inscriptos i ON c.id_carrera = i.id_carrera
GROUP BY 
    c.id_carrera, 
    c.nombre
ORDER BY 
    cantidad_inscriptos DESC;

    