
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionnaireService {

	private QuestionnaireRepository	questionnaireRepository;

	private PetRepository			petRepository;


	@Autowired
	public QuestionnaireService(final PetRepository petRepository, final QuestionnaireRepository questionnaireRepository) {
		this.petRepository = petRepository;
		this.questionnaireRepository = questionnaireRepository;

	}
	@Transactional
	public void saveQuest(final Questionnaire cuestionario) {
		this.questionnaireRepository.save(cuestionario);
	}

	@Transactional
	public Pet findPetById(final int petId) {
		return this.petRepository.findById(petId);
	}

	@Transactional
	public Collection<Questionnaire> findQuestionnaireByPetId(final int petId) {
		return this.questionnaireRepository.findAllByPetId(petId);
	}

	@Transactional
	public Questionnaire findQuestionnaireById(final int questId) {
		return this.questionnaireRepository.findById(questId);
	}

}
