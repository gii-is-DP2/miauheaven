
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EventServiceTests {

	@Autowired
	protected EventService eventService;


	@Test
	void shouldFindEvents() {
		final Collection<Event> events = this.eventService.findEvents();

		final Event event = EntityUtils.getById(events, Event.class, 1);

		Assertions.assertThat(event.getName()).isEqualTo("AnimalFest");
		Assertions.assertThat(event.getDate()).isEqualTo(LocalDate.of(2050, 03, 04));
		Assertions.assertThat(event.getDescription()).isEqualTo("Event to take a good time with your pet");
	}

	@Test
	@Transactional
	public void shouldInsertEvent() {
		Collection<Event> events = this.eventService.findEvents();
		final int found = events.size();

		final Event event = new Event();
		event.setDate(LocalDate.now());
		;
		event.setDescription("Prueba");
		;
		event.setName("Prueba");

		this.eventService.saveEvent(event);
		Assertions.assertThat(event.getId().longValue()).isNotEqualTo(0);

		events = this.eventService.findEvents();
		Assertions.assertThat(events.size()).isEqualTo(found + 1);
	}

	@Test
	public void shouldNotInsertEvent() {
		final Event event = new Event();
		event.setDate(LocalDate.now());
		event.setName("Prueba");

		assertThrows(ConstraintViolationException.class, () -> {
			this.eventService.saveEvent(event);
		});
	}

	//Prueba HU.04 editar eventos caso positivo
	@Test
	@Transactional
	void shouldUpdateEvent() {
		Event ev = this.eventService.findEventById(1);
		final String oldName = ev.getName();
		final String newName = oldName + "Nuevo";

		ev.setName(newName);
		this.eventService.saveEvent(ev);

		// retrieving new name from database
		ev = this.eventService.findEventById(1);
		Assertions.assertThat(ev.getName()).isEqualTo(newName);
	}

	//Prueba HU.04 editar eventos caso negativo
	@Test
	@Transactional
	void shouldNotUpdateEvent() {
		Event ev = this.eventService.findEventById(1);
		final String oldName = ev.getName();
		final String newName = "";

		ev.setName(newName);
		this.eventService.saveEvent(ev);

		// retrieving new name from database
		ev = this.eventService.findEventById(1);
		Assertions.assertThat(ev.getName()).isEqualTo(newName);
	}

	// ----------------------------------------------------------------- HU.25 ---------------------------------------------------------------------------------------------------

	@Test //+
	void adminSeeEvents() {

		Collection<Event> events = this.eventService.findEvents();
		for (Event i : events) {
			Animalshelter a = i.getAnimalshelter();
			Assertions.assertThat(i.getName()).isNotBlank();
			Assertions.assertThat(i.getDescription()).isNotBlank();
			Assertions.assertThat(i.getDate()).isNotNull();
			Assertions.assertThat(a).isNotNull();
		}

	}

	@Test //-
	void adminDontSeeNotnexistentEvent() {
		Collection<Event> events = this.eventService.findEvents();
		Event evento = this.eventService.findEventById(events.size() + 1);
		Assertions.assertThat(evento).isNull();

	}

}
