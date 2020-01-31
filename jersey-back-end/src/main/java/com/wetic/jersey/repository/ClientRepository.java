package com.wetic.jersey.repository;

import java.util.ArrayList;
import com.wetic.jersey.domain.Client;
import com.wetic.jersey.service.dto.RecetteChartSerieDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Override
	@Query(value="select c.*, u.first_name from client c join jhi_user u on c.user_id = u.id",nativeQuery=true)
	public Page<Client>findAll(Pageable pageable);
	
	@Query(value="select count(*) as countClient from client",nativeQuery=true)
	public Long CountAll();
	
	//@Query(value="select count(id) as valeurMois, MONTH(created_date) as mois from jhi_user where MONTH(created_date) IS NOT NULL group by MONTH(created_date)",nativeQuery=true)
	@Query("SELECT new com.wetic.jersey.service.dto.RecetteChartSerieDTO(COUNT(u.id), MONTH(u.createdDate)) from User u where MONTH(u.createdDate) IS NOT NULL group by MONTH(u.createdDate)")
	public ArrayList<RecetteChartSerieDTO> CLientAddedByMonth();
}
