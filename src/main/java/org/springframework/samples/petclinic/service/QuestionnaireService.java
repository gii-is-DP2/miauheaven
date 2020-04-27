
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.QuestionnaireRepository;
import org.springframework.samples.petclinic.service.exceptions.UnrelatedPetException;
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

	@Transactional(readOnly = true)
	public Questionnaire findOneById(final int questionnaireId) throws DataAccessException {
		return this.questionnaireRepository.findOneById(questionnaireId);
	}

	@Transactional
	public Questionnaire findQuestionnaireById(final int questId) {
		return this.questionnaireRepository.findById(questId);
	}
	@Transactional
	public List<Questionnaire> findAll() {
		return (List<Questionnaire>) this.questionnaireRepository.findAll();
	}

	@Transactional
	public Collection<Questionnaire> findMyQuestionnaireByPetId(final int shelterid, final int petId) throws UnrelatedPetException {
		Pet pet = this.petRepository.findById(petId);
		if (shelterid != pet.getOwner().getId()) {
			throw new UnrelatedPetException();
		} else {
			return this.questionnaireRepository.findAllByPetId(petId);
		}
	}

}
