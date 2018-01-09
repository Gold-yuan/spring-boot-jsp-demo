package com.ytf.apple.respositroy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ytf.apple.entity.City;


@Repository
public interface CityRepository extends CrudRepository<City, Long>{
	City findCityById(Long id);
	List<City> findAll();
	
	@Query(" select count(*) from City t where name = :name")
    Integer getCountForName(@Param("name") String name);
}