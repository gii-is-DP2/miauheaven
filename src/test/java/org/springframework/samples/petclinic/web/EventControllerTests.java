
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = EventController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class EventControllerTests {

	private static final int	TEST_EVENT_ID	= 1;

	@MockBean
	private EventService		eventService;

	@Autowired
	private MockMvc				mockMvc;

	private Event				event;


	@BeforeEach
	void setup() {
		this.event = new Event();
		this.event.setId(EventControllerTests.TEST_EVENT_ID);
		this.event.setName("Prueba");
		this.event.setDescription("Prueba");
		this.event.setDate(LocalDate.now());

		BDDMockito.given(this.eventService.findEventById(EventControllerTests.TEST_EVENT_ID)).willReturn(this.event);

	}

	@WithMockUser(value = "spring")
	@Test
	void testEventList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("events")).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEventShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", EventControllerTests.TEST_EVENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("id", Matchers.is(EventControllerTests.TEST_EVENT_ID))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("name", Matchers.is("Prueba")))).andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("description", Matchers.is("Prueba"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("date", Matchers.is(LocalDate.now())))).andExpect(MockMvcResultMatchers.view().name("events/eventShow"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("event"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").param("name", "Prueba").with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Descripción de prueba").param("date", "2020/08/08"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").param("name", "Prueba").with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Descripción de prueba").param("date", ""))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("event")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"));
	}
}
