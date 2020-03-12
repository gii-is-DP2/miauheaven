
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

	Notification findById(int id);

}
