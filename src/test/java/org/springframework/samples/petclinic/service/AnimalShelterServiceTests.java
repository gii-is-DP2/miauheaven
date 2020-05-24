
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.UmbralInferiorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnimalShelterServiceTests {

	@Autowired
	private OwnerService			ownerService;

	@Autowired
	private PetService				petService;

	@Autowired
	private QuestionnaireService	questionnaireService;
	
	@Autowired
	private AnimalshelterService animalshelterService;
	

	@Test //-
	@Transactional
    public void testSaveAnimalshelter() {
		
		Collection<Animalshelter> shelters1 = this.animalshelterService.findAnimalshelters();
		Integer cantidadInicial = shelters1.size();
		
		User user = new User();
		user.setUsername("username2");
		user.setPassword("123456789");
		user.setEnabled(true);

		Owner owner = new Owner();
		owner.setFirstName("Name2");
		owner.setLastName("LastName");
		owner.setAddress("Adress");
		owner.setCity("city");
		owner.setTelephone("672554879");
		owner.setUser(user);
		
		Animalshelter shelter = new Animalshelter();
		shelter.setCif("12345679B");
		shelter.setName("shelterName");
		shelter.setPlace("place");
		shelter.setOwner(owner);
		
		this.animalshelterService.save(shelter);
		
		Collection<Animalshelter> shelters2 = this.animalshelterService.findAnimalshelters();
		Integer cantidadFinal = shelters2.size();

		Assertions.assertThat(cantidadInicial == cantidadFinal - 1);
	}


	// ---------------------------------------------------------------- HU.8 ----------------------------------------------------------------------------------------------------

	@Test //+
	@Transactional
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
	@Transactional
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
