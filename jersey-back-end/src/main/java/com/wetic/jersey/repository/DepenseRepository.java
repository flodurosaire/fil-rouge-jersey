package com.wetic.jersey.repository;

import java.util.ArrayList;

import com.wetic.jersey.domain.Depense;
import com.wetic.jersey.service.dto.RecetteChartSerieDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Depense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {
	@Override
	@Query(value = "select d.*, td.libelle from depense d join type_depense td on d.type_depense_id = td.id", nativeQuery = true)
	public Page<Depense> findAll(Pageable pageable);

	@Query(value = "select sum(montant) as totalDepense from depense", nativeQuery = true)
	public Long SumAll();

	// @Query(value="select sum(montant) as valeurMois, MONTH(date_facturation) as
	// mois from depense where MONTH(date_facturation) IS NOT NULL group by
	// MONTH(date_facturation)",nativeQuery=true)
	@Query(value = "SELECT new com.wetic.jersey.service.dto.RecetteChartSerieDTO(SUM(d.montant),MONTH(d.dateFacturation)) from Depense d where MONTH(d.dateFacturation) IS NOT NULL group by MONTH(d.dateFacturation)")
	public ArrayList<RecetteChartSerieDTO> SumDepensesByMonth();

}
