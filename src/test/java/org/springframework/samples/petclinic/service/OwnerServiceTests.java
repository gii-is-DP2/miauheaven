/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * OwnerServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OwnerServiceTests {

	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	private ProductService		productService;

	@Autowired
	private ProductRepository	productRepository;


	@Test
	public void findByUsername() {
		Owner owner = this.ownerService.findOwnerByUsername("owner1");
		Assertions.assertThat(owner.getAddress()).isNotEmpty();
		Assertions.assertThat(owner.getCity()).isNotEmpty();
		Assertions.assertThat(owner.getTelephone()).isNotEmpty();
		Assertions.assertThat(owner.getFirstName()).isNotNull();
		Assertions.assertThat(owner.getLastName()).isNotNull();

	}

	@Test
	@Transactional
	void shouldFindOwnersByLastName() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Davis");
		Assertions.assertThat(owners.size()).isEqualTo(2);

		owners = this.ownerService.findOwnerByLastName("Daviss");
		Assertions.assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	@Transactional
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.ownerService.findOwnerById(1);
		Assertions.assertThat(owner.getLastName()).startsWith("Franklin");
		Assertions.assertThat(owner.getPets().size()).isEqualTo(1);
		Assertions.assertThat(owner.getPets().get(0).getType()).isNotNull();
		Assertions.assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
	}

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		owner.setAddress("4, Evans Street");
		owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
		User user = new User();
		user.setUsername("Sam");
		user.setPassword("supersecretpassword");
		user.setEnabled(true);
		owner.setUser(user);

		this.ownerService.saveOwner(owner);
		Assertions.assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.ownerService.findOwnerByLastName("Schultz");
		Assertions.assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Owner owner = this.ownerService.findOwnerById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.ownerService.saveOwner(owner);

		// retrieving new name from database
		owner = this.ownerService.findOwnerById(1);
		Assertions.assertThat(owner.getLastName()).isEqualTo(newLastName);
	}

	@Test
	@Transactional
	void shoulSaveOwner() {
		Collection<Owner> owners = (Collection<Owner>) this.ownerService.findAllOwner();
		Integer cantidadInicial = owners.size();

		User user = new User();
		user.setUsername("username");
		user.setPassword("123456789");
		user.setEnabled(true);

		Owner owner = new Owner();
		owner.setFirstName("Name");
		owner.setLastName("LastName");
		owner.setAddress("Adress");
		owner.setCity("city");
		owner.setTelephone("672554879");
		owner.setUser(user);

		this.ownerService.saveOwner(owner);

		Collection<Owner> owners2 = (Collection<Owner>) this.ownerService.findAllOwner();
		Integer cantidadFinal = owners2.size();

		Assertions.assertThat(cantidadInicial == cantidadFinal - 1);

	}
	
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
