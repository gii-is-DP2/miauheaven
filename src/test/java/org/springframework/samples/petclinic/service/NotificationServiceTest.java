
package org.springframework.samples.petclinic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.stereotype.Service;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;


	@Test
	public void findOwnersNotification() {
		Iterable<Notification> notifications = this.notificationService.findAllForOwners();
		for (Notification i : notifications) {
			Assertions.assertThat(i.getTitle()).isNotEmpty();
			Assertions.assertThat(i.getMessage()).isNotEmpty();
			Assertions.assertThat(i.getTarget()).matches("owner");
			Assertions.assertThat(i.getDate()).isNotNull();
		}
	}

	@Test
	public void findVetsNotification() {
		Iterable<Notification> notifications = this.notificationService.findAllForVets();
		for (Notification i : notifications) {
			Assertions.assertThat(i.getTitle()).isNotEmpty();
			Assertions.assertThat(i.getMessage()).isNotEmpty();
			Assertions.assertThat(i.getTarget()).matches("veterinarian");
			Assertions.assertThat(i.getDate()).isNotNull();
		}
	}

	@Test
	public void findAnimalShelterNotification() {
		Iterable<Notification> notifications = this.notificationService.findAllForAnimalShelters();
		for (Notification i : notifications) {
			Assertions.assertThat(i.getTitle()).isNotEmpty();
			Assertions.assertThat(i.getMessage()).isNotEmpty();
			Assertions.assertThat(i.getTarget()).matches("animal_shelter");
			Assertions.assertThat(i.getDate()).isNotNull();
		}
	}

}
