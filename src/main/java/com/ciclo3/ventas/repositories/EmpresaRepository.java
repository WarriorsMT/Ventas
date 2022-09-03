package com.ciclo3.ventas.repositories;

import com.ciclo3.ventas.entities.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    EmpresaEntity findByNombre(String nombre);
    EmpresaEntity findByNit(String nit);
    @Query(value = "SELECT e.* FROM empresa e WHERE e.id <> ?1 and e.nombre = ?2", nativeQuery = true)
    EmpresaEntity findByNombreEIdNoIgual(Long id, String nombre);
    @Query(value = "SELECT e.* FROM empresa e WHERE e.id <> ?1 and e.nit = ?2", nativeQuery = true)
    EmpresaEntity findByNitEIdNoIgual(Long id, String nit);
    @Query(value = "SELECT ea.* " +
            "FROM empresa ea WHERE ea.id = ?1 and ea.id in " +
            "(SELECT id_empresa FROM empleado)", nativeQuery = true)
    EmpresaEntity findByIdEnEmpleado(Long id);
}
