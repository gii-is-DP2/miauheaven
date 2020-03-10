
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

	@Autowired
	private QuestionnaireRepository	questionnaireRepository;

	@Autowired
	private PetRepository			petRepository;

	@Autowired
	private OwnerRepository			owRepository;


	public void saveQuest(final Questionnaire cuestionario) {
		this.questionnaireRepository.save(cuestionario);
	}

	public Pet findPetById(final int petId) {
		return this.petRepository.findById(petId);
	}

	public Owner findOwnerByUsername(final String username) {
		return this.owRepository.findByUsername(username);
	}

}
