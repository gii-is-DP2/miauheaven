
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	@Override
	Collection<Product> findAll();

	Product findProductById(int id);
}
