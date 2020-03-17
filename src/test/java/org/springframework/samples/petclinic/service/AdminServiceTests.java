
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdminServiceTests {

	@Autowired
	private OwnerService			ownerService;

	@Autowired
	private PetService				petService;

	@Autowired
	private QuestionnaireService	questionnaireService;


	// ---------------------------------------------------------------- HU.10 ----------------------------------------------------------------------------------------------------

	@Test //+
	public void adminShouldSeeQuestionnaire() {
		Questionnaire res = null;
		Pet pet = this.questionnaireService.findPetById(14);
		Owner owner = this.ownerService.findOwnerById(1);
		Collection<Questionnaire> quests = this.questionnaireService.findQuestionnaireByPetId(pet.getId());
		for (Questionnaire q : quests) {
			if (q.getOwner().equals(owner)) {
				res = q;
				break;
			}
		}
		Assertions.assertThat(res.getIngresos()).isNotBlank();
		Assertions.assertThat(res.getConvivencia()).isNotBlank();
		Assertions.assertThat(res.getHorasLibres()).isNotBlank();
		Assertions.assertThat(res.getName()).isNotBlank();
		Assertions.assertThat(res.getPuntuacion()).isNotNull();
		Assertions.assertThat(res.getPuntuacion()).isNotNegative();
		Assertions.assertThat(res.getVivienda()).isNotBlank();

	}

	@Test //-
	public void adminShouldNotSeeQuestionnaire() {
		Questionnaire res = null;
		Pet pet = this.questionnaireService.findPetById(14);
		Owner owner = new Owner();
		Collection<Questionnaire> quests = this.questionnaireService.findQuestionnaireByPetId(pet.getId());
		for (Questionnaire q : quests) {
			if (q.getOwner().equals(owner)) {
				res = q;
				break;
			}
		}
		Assertions.assertThat(res).isNull();
	}

	// ----------------------------------------------------------------- HU.19 ---------------------------------------------------------------------------------------------------

	@Test //+
	public void adminShouldSeePet() {
		Pet pet = this.petService.findPetById(1);
		Assertions.assertThat(pet.getName()).isNotBlank();
		Assertions.assertThat(pet.getGenre()).matches("female|male");
		Assertions.assertThat(pet.getBirthDate()).isNotNull();
		Assertions.assertThat(pet.getOwner()).isNotNull();
		Assertions.assertThat(pet.getType()).isNotNull();

	}

	@Test //-
	public void adminShouldNotSeePet() {
		Collection<Pet> pets = (Collection<Pet>) this.petService.findAllPets();
		Assertions.assertThat(this.petService.findPetById(pets.size() + 1)).isNull();
	}

}
