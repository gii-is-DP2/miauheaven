
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.service.exceptions.UmbralInferiorException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AnimalShelterServiceTests {

	@Autowired
	private OwnerService			ownerService;

	@Autowired
	private PetService				petService;

	@Autowired
	private QuestionnaireService	questionnaireService;


	// ---------------------------------------------------------------- HU.8 ----------------------------------------------------------------------------------------------------

	@Test //+
	public void animalShelterShouldAceptApplication() {
		Questionnaire res = null;
		Pet pet = this.questionnaireService.findPetById(14);
		Owner owner = this.ownerService.findOwnerById(1);
		Integer oldtam=owner.getPets().size();
		Collection<Questionnaire> quests = this.questionnaireService.findQuestionnaireByPetId(pet.getId());
		for (Questionnaire q : quests) {
			if (q.getOwner().equals(owner)) {
				res = q;
				break;
			}
		}
		if (res.getPuntuacion()>=res.getUmbral()) {
			owner.addPet(pet);
			this.ownerService.saveOwner(owner);
		}
		Owner newowner = this.ownerService.findOwnerById(1);
		Integer newtam=newowner.getPets().size()+1;
		
		Assertions.assertThat(oldtam).isLessThan(newtam);
		

	}

	@Test //-
    public void animalShelterShouldNotAceptApplication() {
        Questionnaire res = null;
        Pet pet = this.questionnaireService.findPetById(15);
        Owner owner = this.ownerService.findOwnerById(2);
        Collection<Questionnaire> quests = this.questionnaireService.findQuestionnaireByPetId(pet.getId());
        for (Questionnaire q : quests) {
            if (q.getOwner().equals(owner)) {
                res = q;
                break;
            }
        }

        Integer umbral =res.getUmbral();
        Integer puntuacion =res.getPuntuacion();
        try {
            this.ownerService.saveOwnerQuest(owner, res.getUmbral(), res.getPuntuacion());
        } catch (UmbralInferiorException e) {
            e.printStackTrace();
        }

        assertThrows(UmbralInferiorException.class, () -> {
            ownerService.saveOwnerQuest(owner,umbral, puntuacion);
        });
    }


}
