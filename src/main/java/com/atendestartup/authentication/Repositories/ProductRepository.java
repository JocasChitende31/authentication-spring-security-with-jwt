package com.atendestartup.authentication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.atendestartup.authentication.DTO.ProductRecordDTO;
import com.atendestartup.authentication.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Modifying
	@Query(nativeQuery = true, value = """
			UPDATE tb_products SET name=:newName, price=:newPrice, description=:newDescription WHERE id=:id
			""")
	void update(String id, String newName, Double newPrice, String newDescription);
	
	@Query(nativeQuery=true, value="""
			SELECT tb_products.name FROM tb_products WHERE tb_products.name = ?1
			""")
	public ProductRecordDTO findByName(String name); 
}
