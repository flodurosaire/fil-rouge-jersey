package com.wetic.jersey.repository;

import com.wetic.jersey.domain.DetailsFacture;
import com.wetic.jersey.domain.Facture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailsFacture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsFactureRepository extends JpaRepository<DetailsFacture, Long> {
	Page<DetailsFacture> findDetailsFactureByFactureId(Pageable pageable, Long Id);

	@Override
	@Query(value="select df.id, df.qte_produit, df.facture_id, df.produit_id, p.libelle, p.prix from details_facture df join produit p  on df.produit_id = p.id",nativeQuery=true)
	public Page<DetailsFacture>findAll(Pageable pageable);

	public void deleteByFactureId(Long id);
}
