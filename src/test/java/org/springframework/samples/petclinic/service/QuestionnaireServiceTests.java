
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class QuestionnaireServiceTests {

	@Autowired
	protected QuestionnaireService questionnaireService;


	@Test
	@Transactional
	public void shouldInsertQuestionnaire() {

		Questionnaire q1 = new Questionnaire();
		q1.setIngresos("Altos");
		q1.setHorasLibres("Entre 3 y 6 horas");
		q1.setVivienda("Casa");
		q1.setConvivencia("Si");
		q1.setName("Quest-George Franklin");
		q1.setPuntuacion(5);
		q1.setUmbral(4);

		Questionnaire q2 = new Questionnaire();
		q2.setIngresos("Bajo");
		q2.setHorasLibres("Entre 3 y 6 horas");
		q2.setVivienda("Casa");
		q2.setConvivencia("No");
		q2.setName("Quest-Mario Suarez");
		q2.setPuntuacion(7);
		q2.setUmbral(3);

		Owner o = new Owner();
		o.setFirstName("George");
		o.setLastName("Franklin");
		o.setCity("Madison");
		o.setAddress("110 W. Liberty St.");
		o.setTelephone("6085551023");

		User user = new User();
		user.setUsername("owner1");
		user.setPassword("0wn3r");
		user.setEnabled(true);
		o.setUser(user);

		LocalDate d = LocalDate.of(2016, 6, 18);
		PetType pt = new PetType();
		pt.setName("lizard");

		Pet p = new Pet();
		p.setName("Desto");
		p.setGenre("male");
		p.setType(pt);
		p.setBirthDate(d);

		this.questionnaireService.saveQuest(q1);
		Boolean b1 = this.questionnaireService.findAll().contains(q1);
		Assertions.assertThat(b1).isNotEqualTo(false);
	}

	@Test
	void shouldFindQuestionnaireByPetId() {
		Collection<Questionnaire> Questionnaire = this.questionnaireService.findQuestionnaireByPetId(14);
		Assertions.assertThat(Questionnaire.size()).isEqualTo(1);

		Questionnaire = this.questionnaireService.findQuestionnaireByPetId(1);
		Assertions.assertThat(Questionnaire.isEmpty()).isTrue();
	}
}
