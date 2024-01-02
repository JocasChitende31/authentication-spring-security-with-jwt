package com.atendestartup.authentication.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atendestartup.authentication.DTO.ProductRecordDTO;
import com.atendestartup.authentication.Services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/product")
	public ResponseEntity<String> save(@RequestBody @Valid ProductRecordDTO data) {
		productService.save(data);
		return ResponseEntity.ok("'status: 200', Dados salvos com sucesso. ");
	}

	@GetMapping(value = "/products")
	public List<ProductRecordDTO> findAll() {
		List<ProductRecordDTO> data = productService.findAll();
		return data;
	}

	@GetMapping(value = "/product/{id}")
	public ProductRecordDTO findById(@PathVariable String id) {
		ProductRecordDTO data = productService.findById(id);
		return data;
	}

	@PutMapping(value = "/product/{id}/update")
	public ResponseEntity<String> update(@PathVariable String id, @RequestBody @Valid ProductRecordDTO data) {
		productService.update(id, data);
		return ResponseEntity.ok("'status: 200', Producto actualizado com sucesso.");
	}
	@DeleteMapping(value="/product/{id}/delete")
	public ResponseEntity<String> delete(@PathVariable String id){
		productService.delete(id);
		return ResponseEntity.ok("status: 200, Producto excluido com sucesso.");
	}
}
