package com.wetic.jersey.repository;

import com.wetic.jersey.domain.Produit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	@Query(value="select count(*) as countProduct from produit",nativeQuery=true)
	public Long CountAll();
}
