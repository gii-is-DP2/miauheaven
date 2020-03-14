
package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

	Notification findById(int id);

	@Query("select n from Notification n where n.target = 'owner'")
	Iterable<Notification> findAllForOwners();

	@Query("select n from Notification n where n.target = 'veterinarian'")
	Iterable<Notification> findAllForVets();

	@Query("select n from Notification n where n.target = 'animal_shelter'")
	Iterable<Notification> findAllForAnimalShelters();

}
