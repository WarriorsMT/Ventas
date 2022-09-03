package com.ciclo3.ventas.repositories;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    EmpleadoEntity findByCorreo(String correo);
    @Query(value = "SELECT e.* FROM empleado e WHERE e.id <> ?1 and e.correo = ?2", nativeQuery = true)
    EmpleadoEntity findByCorreoEIdNoIgual(Long id, String correo);
    @Query(value = "SELECT e.* FROM empleado e WHERE e.id_empresa = ?1", nativeQuery = true)
    List<EmpleadoEntity> findByEmpresa(Long id_empresa);

    @Query(value = "SELECT eo.* " +
            "FROM empleado eo WHERE eo.id = ?1 and eo.id in " +
            "(SELECT id_empleado FROM movimiento)", nativeQuery = true)
    EmpleadoEntity findByIdEnMovimiento(Long id);
}
