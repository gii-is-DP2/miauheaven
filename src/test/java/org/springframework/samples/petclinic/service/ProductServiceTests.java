
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProductServiceTests {

	@Autowired
	private ProductService productService;


	// ---------------------------------------------------------------- HU.28 ----------------------------------------------------------------------------------------------------

	@Test //+
	public void ShouldEditProduct() {
		Product product = this.productService.findProductById(1);
		String newName = "Esto es un producto de prueba";
		Double newPrice = 7.98;
		Boolean newStock = false;
		product.setName(newName);
		product.setPrice(newPrice);
		product.setStock(newStock);
		this.productService.save(product);
		product = this.productService.findProductById(1);
		Assertions.assertThat(product.getName()).isEqualTo(newName);
		Assertions.assertThat(product.getPrice()).isEqualTo(newPrice);
		Assertions.assertThat(product.getStock()).isEqualTo(newStock);

	}

	@Test //-
	public void ShoulNotdEditProduct() {
		String newName = "";
		Double newPrice = null;
		Boolean newStock = null;
		
		assertThrows(ConstraintViolationException.class, () -> {
			Product product = this.productService.findProductById(1);
			product.setName(newName);
			product.setPrice(newPrice);
			product.setStock(newStock);
			this.productService.save(product);
			product = this.productService.findProductById(1);

		});

	}

}
