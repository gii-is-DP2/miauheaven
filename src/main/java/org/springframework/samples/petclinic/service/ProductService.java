
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;


	public ProductService(final ProductRepository productRepository) {

		this.productRepository = productRepository;
	}

	@Transactional
	public Collection<Product> findAll() {
		return this.productRepository.findAll();
	}

	@Transactional
	public Product findProductById(final Integer id) {
		Product res = this.productRepository.findProductById(id);
		return res;
	}

}
