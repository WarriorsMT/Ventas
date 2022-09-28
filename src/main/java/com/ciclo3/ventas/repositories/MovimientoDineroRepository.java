package com.ciclo3.ventas.repositories;

import com.ciclo3.ventas.entities.EmpresaEntity;
import com.ciclo3.ventas.entities.MovimientoDineroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoDineroRepository extends JpaRepository<MovimientoDineroEntity, Long> {
    @Query(value = "SELECT m.* " +
            "FROM movimiento m " +
            "WHERE m.id_empresa = ?1 " +
            "ORDER BY m.fecha desc, m.id desc", nativeQuery = true)
    List<MovimientoDineroEntity> findByEmpresa(Long id_empresa);

    @Query(value = "SELECT CASE WHEN SUM(monto) IS null THEN 0 ELSE SUM(monto) END " +
            "FROM movimiento " +
            "WHERE id_empresa=?1", nativeQuery = true)
    double totalPorEmpresa(Long id_empresa);
}
