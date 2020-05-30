
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductServiceTests {

	@Autowired
	private ProductService		productService;

	@Autowired
	private ProductRepository	productRepository;


	// ---------------------------------------------------------------- HU.27 ----------------------------------------------------------------------------------------------------

	@Test
	@Transactional
	public void shouldInsertProduct() {
		Collection<Product> products = this.productService.findAll();
		Product p = this.productService.findProductById(1);

		final int found = products.size();

		final Product product = new Product();
		product.setDescription("Prueba");
		product.setName("Producto1");
		product.setImage("https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg");
		product.setPrice(20.0);
		product.setStock(true);
		this.productService.save(product);
		Assertions.assertThat(product.getId().longValue()).isNotEqualTo(0);

		products = this.productService.findAll();
		Assertions.assertThat(products.size()).isEqualTo(found + 1);
		this.productRepository.deleteById(product.getId());
	}

	@Test
	@Transactional
	public void shouldNotInsertProduct() {
		final Product product = new Product();
		product.setDescription("Prueba");
		product.setName("Prueba");

		assertThrows(ConstraintViolationException.class, () -> {
			this.productService.save(product);
		});
		//	this.eventRepository.deleteById(event.getId());
	}

	// ---------------------------------------------------------------- HU.28 ----------------------------------------------------------------------------------------------------

	@Test //+
	@Transactional
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
	@Transactional
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
