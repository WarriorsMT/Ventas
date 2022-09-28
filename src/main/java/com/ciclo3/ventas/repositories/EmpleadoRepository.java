package com.ciclo3.ventas.repositories;

import com.ciclo3.ventas.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    List<EmpleadoEntity> findAllByOrderByNombreAsc();
    EmpleadoEntity findByCorreo(String correo);

    @Query(value = "SELECT e.* FROM empleado e WHERE e.id <> ?1 and e.correo = ?2", nativeQuery = true)
    EmpleadoEntity findByCorreoEIdNoIgual(Long id, String correo);

    @Query(value = "SELECT e.* " +
            "FROM empleado e " +
            "WHERE e.id_empresa = ?1 and rol<> 'SUPERADMINISTRADOR' " +
            "ORDER BY e.nombre", nativeQuery = true)
    List<EmpleadoEntity> findByEmpresa(Long id_empresa);

    @Query(value = "SELECT eo.* " +
            "FROM empleado eo WHERE eo.id = ?1 and eo.id in " +
            "(SELECT id_empleado FROM movimiento)", nativeQuery = true)
    EmpleadoEntity findByIdEnMovimiento(Long id);

    @Modifying
    @Query(value = "update empleado " +
            "set password = crypt(?2, gen_salt('bf')) " +
            "where id=?1", nativeQuery = true)
    @Transactional
    int savePassword(Long id, String password);

    @Query(value = "SELECT crypt(?1, gen_salt('bf')) ", nativeQuery = true)
    String generatePassword(String password);

    @Query(value = "select * " +
            "from empleado " +
            "where correo=?1 and password = crypt(?2, password) ", nativeQuery = true)
    EmpleadoEntity Login(String correo, String clave);

}
