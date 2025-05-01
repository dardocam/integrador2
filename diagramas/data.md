**1. Quién gestiona las conexiones en JPA?**  
En JPA, la gestión de las conexiones a la base de datos es responsabilidad del **proveedor de JPA** (como Hibernate, EclipseLink, etc.). El `EntityManager` utiliza un `DataSource` configurado (definido en `persistence.xml` o programáticamente) para obtener y liberar conexiones. La fábrica de `EntityManager` (`EntityManagerFactory`) se encarga de crear instancias de `EntityManager` que gestionan las conexiones subyacentes según sea necesario.

---

**2. Quién gestiona las transacciones en JPA?**  
- En entornos **JTA (Java EE/Jakarta EE)**, las transacciones son gestionadas por el **contenedor** (como un servidor de aplicaciones), usando `UserTransaction` o transacciones declarativas (`@Transactional`).  
- En entornos **resource-local** (aplicaciones standalone), las transacciones son gestionadas explícitamente por el desarrollador mediante `EntityTransaction` (obtenido con `entityManager.getTransaction()`).  

---

**3. Es EntityManagerFactory thread-safe?**  
**Sí**, `EntityManagerFactory` es **thread-safe**. Está diseñada para ser creada una vez y compartida entre múltiples hilos, según la especificación de JPA.

---

**4. Es EntityManager thread-safe?**  
**No**, `EntityManager` **no es thread-safe**. Cada instancia debe ser utilizada por un solo hilo a la vez. Se recomienda crear un `EntityManager` por hilo o por operación transaccional.

---

**5. Al utilizar entityManager.getTransaction(), ¿cuál es el scope de la transacción?**  
El scope de la transacción es **local al `EntityManager` actual**. La transacción comienza con `begin()`, incluye todas las operaciones realizadas con ese `EntityManager` hasta que se llama a `commit()` (para confirmar) o `rollback()` (para deshacer). Es una transacción de **resource-local**, no distribuida.

---

**6. Al utilizar entityManager.persist(), ¿cómo se manejan las excepciones?**  
- `persist()` puede lanzar excepciones no verificadas (**unchecked**):  
  - `EntityExistsException`: Si la entidad ya existe (no siempre aplicable, depende del proveedor).  
  - `IllegalArgumentException`: Si el objeto no es una entidad válida.  
  - `TransactionRequiredException`: Si no hay una transacción activa (en entornos JTA con transacciones requeridas).  
  - `PersistenceException`: Error genérico de persistencia.  
- Estas excepciones deben ser capturadas y manejadas explícitamente por el código.

---

**7. Al utilizar entityManager.getTransaction().commit(), ¿cómo se manejan las excepciones? ¿Qué sucede en un caso de rollback()?**  
- **Al hacer commit()**:  
  - Si hay un error, se lanza `RollbackException` (o `PersistenceException`).  
  - La transacción se marca como **rollback-only** si ocurre un error durante la fase de flush (ej: violación de restricciones).  
- **En caso de rollback()**:  
  - Se revierten todos los cambios realizados en la transacción actual.  
  - Las entidades gestionadas por el `EntityManager` quedan en estado desvinculado (**detached**) si el `EntityManager` está cerrado, o pueden seguir gestionadas si no.  
  - El `EntityManager` puede seguir siendo usado, pero se recomienda manejarlo con cuidado después de un rollback.  

**Ejemplo de manejo de excepciones:**  
```java
EntityTransaction tx = entityManager.getTransaction();
try {
    tx.begin();
    // Operaciones JPA...
    tx.commit();
} catch (PersistenceException e) {
    if (tx.isActive()) {
        tx.rollback(); // Obligatorio si hay una excepción y la transacción está activa.
    }
    // Manejar la excepción...
}
```