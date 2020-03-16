
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdminServiceTests {

	@Autowired
	private PetService PetService;


	// ----------------------------------------------------------------- HU.19 ---------------------------------------------------------------------------------------------------

	@Test //+
	public void adminShouldSeePet() {
		Pet pet = this.PetService.findPetById(1);
		Assertions.assertThat(pet.getName()).isNotBlank();
		Assertions.assertThat(pet.getGenre()).matches("female|male");
		Assertions.assertThat(pet.getBirthDate()).isNotNull();
		Assertions.assertThat(pet.getOwner()).isNotNull();
		Assertions.assertThat(pet.getType()).isNotNull();

	}

	@Test //-
	public void adminShouldNotSeePet() {
		Collection<Pet> pets = (Collection<Pet>) this.PetService.findAllPets();
		Assertions.assertThat(this.PetService.findPetById(pets.size() + 1)).isNull();
	}

}
