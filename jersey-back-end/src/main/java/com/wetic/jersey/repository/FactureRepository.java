package com.wetic.jersey.repository;

import com.wetic.jersey.domain.DetailsFacture;
import com.wetic.jersey.domain.Facture;
import com.wetic.jersey.service.dto.RecetteChartSerieDTO;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Facture entity.
 *
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
	@Override
	@Query(value = "select facture.id as id, date_facturation, facture.client_id, first_name  from facture join client on facture.client_id = client.id join jhi_user on client.user_id = jhi_user.id", nativeQuery = true)
	public Page<Facture> findAll(Pageable pageable);

	public Page<Facture> findByClientId(Pageable pageable, Long id);

	@Query(value = "select count(*) as countFacture from facture", nativeQuery = true)
	public Long CountAll();

	// @Query(value="select sum(p.prix * df.qte_produit) as valeurMois,
	// MONTH(f.date_facturation) as mois from facture f join details_facture df on
	// df.facture_id = f.id join produit p on df.produit_id = p.id where
	// MONTH(f.date_facturation) IS NOT NULL group by
	// MONTH(f.date_facturation)",nativeQuery=true)
	@Query(value = "SELECT new com.wetic.jersey.service.dto.RecetteChartSerieDTO(SUM(p.prix * df.qteProduit), MONTH(f.dateFacturation)) from Facture f join DetailsFacture df on df.facture.id = f.id join Produit p on df.produit.id = p.id where MONTH(f.dateFacturation) IS NOT NULL group by MONTH(f.dateFacturation)")
	public ArrayList<RecetteChartSerieDTO> SumFacturesByMonth();

}
