
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Questionnaire;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Integer> {

	Collection<Questionnaire> findAllByPetId(int petId);

	
	Questionnaire findOneById(int questionnaireId);


	Questionnaire findById(int questId);


}
