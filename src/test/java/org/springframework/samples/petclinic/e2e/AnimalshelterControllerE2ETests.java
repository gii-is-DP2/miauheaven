
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDateTime;

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
//@TestPropertySource(locations = "classpath:application-mysql.properties")
class AnimalshelterControllerE2ETests {

	private static final int	TEST_OWNER_ID			= 11;

	private static final int	TEST_NOTIFICATION_ID	= 5;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testShowAnimalshelterList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("animalshelters"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelterList"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("animalshelter"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/createOrUpdateAnimalshelterForm"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/animalshelter/new").param("name", "Pichú Animales").param("cif", "12345678A").with(SecurityMockMvcRequestPostProcessors.csrf()).param("place", "41410 La Celada").param("owner_id", "11"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/animalshelter/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Arca Sevilla").param("place", "41500 Alcalá de Guadaíra").param("owner_id", "12"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("animalshelter")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("animalshelter", "cif"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/createOrUpdateAnimalshelterForm"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testFindMyAnimalShelter() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter", AnimalshelterControllerE2ETests.TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Shelter")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("Pichú Animales"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("41410 La Celada")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Seville"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("610839583")))).andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelter/animalshelterShow"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testFindOwnersWithQuest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owners"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelterList"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testShowRecordList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter/records")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("records"))
			.andExpect(MockMvcResultMatchers.view().name("records/recordList"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testInitRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter/records/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("record"))
			.andExpect(MockMvcResultMatchers.view().name("records/createOrUpdateRecordForm"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessRecordFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/myAnimalShelter/records/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("owner_id", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessRecordFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/myAnimalShelter/records/new").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testShowNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/notification/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/notification/notificationList"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testShowNotification() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/notification/{notificationId}", AnimalshelterControllerE2ETests.TEST_NOTIFICATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title", Matchers.is("¡Bienvenidas protectoras!"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("date", Matchers.is(LocalDateTime.of(2013, 01, 04, 12, 32)))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message", Matchers.is("Quiero daros la bienvenida a todas las protectoras"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target", Matchers.is("animal_shelter")))).andExpect(MockMvcResultMatchers.view().name("animalshelter/notification/notificationShow"));
	}
}

