package com.ciclo3.ventas.repositories;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    EmpleadoEntity findByCorreo(String correo);
    @Query(value = "SELECT e.* FROM empleado e WHERE e.id <> ?1 and e.correo = ?2", nativeQuery = true)
    EmpleadoEntity findByCorreoEIdNoIgual(Long id, String correo);
}
