
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EventServiceTests {

	@Autowired
	protected EventService eventService;


	@Test
	void shouldFindEvents() {
		Collection<Event> events = this.eventService.findEvents();

		Event event = EntityUtils.getById(events, Event.class, 1);

		Assertions.assertThat(event.getName()).isEqualTo("AnimalFest");
		Assertions.assertThat(event.getDate()).isEqualTo(LocalDate.of(2010, 03, 04));
		Assertions.assertThat(event.getDescription()).isEqualTo("Event to take a good time with your pet");
	}

}
