package com.atendestartup.authentication.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atendestartup.authentication.DTO.ProductRecordDTO;
import com.atendestartup.authentication.Entities.Product;
import com.atendestartup.authentication.Repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public void save(ProductRecordDTO body) {
		Product prod = new Product(body.id(), body.name(), body.price(), body.description(), body.imgUrl());
		this.productRepository.save(prod);
	}

	@Transactional(readOnly = true)
	public List<ProductRecordDTO> findAll() {
		List<Product> resul = this.productRepository.findAll();
		List<ProductRecordDTO> data = resul.stream()
				.map(x -> new ProductRecordDTO(x.getId(), x.getName(), x.getPrice(), x.getDescription(), x.getImgUrl())).toList();
		return data;
	}

	@Transactional(readOnly = true)
	public ProductRecordDTO findById(String id) {
		Product resul = this.productRepository.findById(id).get();
		ProductRecordDTO data = new ProductRecordDTO(resul.getId(), resul.getName(), resul.getPrice(),
				resul.getDescription(), resul.getImgUrl());
		return data;
	}
	@Transactional
	public ProductRecordDTO findByName(String name) {
		ProductRecordDTO resul = this.productRepository.findByName(name);
		return resul;
	}
	@Transactional
	public void update(String id, ProductRecordDTO body) {
		String newName = body.name();
		Double newPrice = body.price();
		String newDescription = body.description();
		String newImgUrl=body.imgUrl();
		this.productRepository.update(id, newName, newPrice, newDescription, newImgUrl);

	}
	@Transactional
	public void delete(String id) {
		Product resul = this.productRepository.findById(id).get();
		this.productRepository.delete(resul);
		
	}
	

}
