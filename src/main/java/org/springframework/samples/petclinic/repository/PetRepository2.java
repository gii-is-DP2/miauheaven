
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pet;

public interface PetRepository2 extends CrudRepository<Pet, Integer> {

}
