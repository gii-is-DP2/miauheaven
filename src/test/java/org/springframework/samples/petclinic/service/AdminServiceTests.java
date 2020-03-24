
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
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
	@Autowired
	private ProductService			productService;

	 Pattern regex = Pattern.compile("(?i)^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?$");
	
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
	
	// ----------------------------------------------------------------- HU.29 ---------------------------------------------------------------------------------------------------

		@Test //+
		public void adminShouldSeeProduct() {
			Product product = this.productService.findProductById(1);
			Assertions.assertThat(product.getPrice()).isNotNull();
			Assertions.assertThat(product.getPrice()).isNotNegative();
			Assertions.assertThat(product.getStock()).isNotNull();
			Assertions.assertThat(product.getImage()).containsPattern(regex);
		}

		@Test //-
		public void adminShouldNotSeeProduct() {
			Collection<Product> products = (Collection<Product>) this.productService.findAll();
			Assertions.assertThat(this.productService.findProductById(products.size() + 1)).isNull();
		}


}
