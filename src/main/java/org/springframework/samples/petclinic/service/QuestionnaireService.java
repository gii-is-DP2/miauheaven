
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

	@Autowired
	private QuestionnaireRepository questionnaireRepository;


	public void saveQuest(final Questionnaire cuestionario) {
		this.questionnaireRepository.save(cuestionario);
	}

}
