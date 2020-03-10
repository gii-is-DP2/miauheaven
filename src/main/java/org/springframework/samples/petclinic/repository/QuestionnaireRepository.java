
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Questionnaire;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Integer> {

}
