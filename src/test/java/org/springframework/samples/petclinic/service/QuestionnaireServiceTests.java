
package org.springframework.samples.petclinic.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class QuestionnaireServiceTests {

	@Autowired
	protected VetService			vetService;

	@Autowired
	protected QuestionnaireService	questService;
	@Autowired
	protected AnimalshelterService	animalshelterService;


	//HU.5
	//Positive Case
	@Test
	void shouldFindMyApplicationsByPet() {
		//We get an animalshelter from the repository
		List<Animalshelter> animalshelters = (List<Animalshelter>) this.animalshelterService.findAnimalshelters();
		Animalshelter animalshelter = EntityUtils.getById(animalshelters, Animalshelter.class, 1);
		Pet pet = animalshelter.getOwner().getPets().get(0);
		List<Questionnaire> quests = (List<Questionnaire>) this.questService.findQuestionnaireByPetId(pet.getId());
		//We see if the collection of questionnaires is empty or not, in this case should not be empty by the initial data.
		Assertions.assertThat(quests.isEmpty()).isEqualTo(false);
	}

}
