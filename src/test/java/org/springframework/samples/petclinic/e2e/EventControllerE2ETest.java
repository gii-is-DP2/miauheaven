
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext
@TestPropertySource(locations = "classpath:application-mysql.properties")
public class EventControllerE2ETest {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_EVENT_ID	= 1;


	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").param("name", "Festival Prueba").with(SecurityMockMvcRequestPostProcessors.csrf()).param("date", "2040/02/12").param("description", "Description of festival"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "AnimalFest 2.0").param("date", "2040/02/12")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("event")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("event", "description")).andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testInitUpdateEventForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}/edit", EventControllerE2ETest.TEST_EVENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("name", Matchers.is("AnimalFest"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2050, 03, 04)))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("description", Matchers.is("Event to take a good time with your pet")))).andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessUpdateEventFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/{eventId}/edit", EventControllerE2ETest.TEST_EVENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "AnimalFest 2.1")
			.param("description", "Event to take a good time with your pet").param("date", "2050/03/04")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessUpdateEventFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/events/{eventId}/edit", EventControllerE2ETest.TEST_EVENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "AnimalFest").param("description", "Descripcion nueva"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("event")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("event", "date"))
			.andExpect(MockMvcResultMatchers.view().name("events/createOrUpdateEvent"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testEventList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("events")).andExpect(MockMvcResultMatchers.view().name("events/eventsList"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})

	@Test
	void testEventShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", EventControllerE2ETest.TEST_EVENT_ID)).andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("name", Matchers.is("AnimalFest"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("description", Matchers.is("Event to take a good time with your pet"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2050, 03, 04))))).andExpect(MockMvcResultMatchers.view().name("events/eventShow"));
	}

}

