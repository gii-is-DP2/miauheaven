
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AnimalshelterService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.samples.petclinic.service.RecordService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AnimalshelterController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AnimalshelterControllerTests {

	private static final int		TEST_OWNER_ID			= 11;

	private static final int		TEST_NOTIFICATION_ID	= 5;

	@Autowired
	private AnimalshelterController	animalshelterController;

	@MockBean
	private AnimalshelterService	clinicService;
	@MockBean
	private OwnerService			ownerService;
	@MockBean
	private QuestionnaireService	questionnaireService;
	@MockBean
	private NotificationService		notificationService;
	@MockBean
	private RecordService			recordService;

	@Autowired
	private MockMvc					mockMvc;

	private Owner					shelter;

	private Notification			notification;

	private Owner					george;
	private Record					record;


	@BeforeEach
	void setup() {

		this.shelter = new Owner();
		this.shelter.setId(AnimalshelterControllerTests.TEST_OWNER_ID);
		this.shelter.setFirstName("Pichú Animales");
		this.shelter.setLastName("Shelter");
		this.shelter.setAddress("41410 La Celada");
		this.shelter.setCity("Seville");
		this.shelter.setTelephone("610839583");
		BDDMockito.given(this.ownerService.findOwnerById(AnimalshelterControllerTests.TEST_OWNER_ID)).willReturn(this.shelter);

		User user = new User();
		user.setEnabled(true);
		user.setUsername("shelter1");
		user.setPassword("shelter1");
		this.shelter.setUser(user);
		BDDMockito.given(this.clinicService.findOwnerByUsername("shelter1")).willReturn(this.shelter);

		this.notification = new Notification();
		this.notification.setId(AnimalshelterControllerTests.TEST_NOTIFICATION_ID);
		this.notification.setTitle("¡Bienvenidas protectoras!");
		this.notification.setMessage("Quiero daros la bienvenida a todas las protectoras");
		this.notification.setDate(LocalDateTime.of(2013, 01, 04, 12, 32));
		this.notification.setTarget("animal_shelter");

		BDDMockito.given(this.notificationService.findNotificationById(AnimalshelterControllerTests.TEST_NOTIFICATION_ID)).willReturn(this.notification);

		this.george = new Owner();
		this.george.setId(1);
		this.george.setFirstName("George");
		this.george.setLastName("Franklin");
		this.george.setAddress("110 W. Liberty St.");
		this.george.setCity("Madison");
		this.george.setTelephone("6085551023");
		BDDMockito.given(this.ownerService.findOwnerById(1)).willReturn(this.george);

		this.record = new Record();
		this.record.setAnimalshelter(this.shelter);
		this.record.setOwner(this.george);
		List<Record> records = new ArrayList<>();
		records.add(this.record);
		BDDMockito.given(this.recordService.findAllByUsename("owner1")).willReturn(records);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAnimalshelterList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("animalshelters"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelterList"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("animalshelter"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/createOrUpdateAnimalshelterForm"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/animalshelter/new").param("name", "Pichú Animales").param("cif", "12345678A").with(SecurityMockMvcRequestPostProcessors.csrf()).param("place", "41410 La Celada").param("owner_id", "11"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/animalshelter/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Arca Sevilla").param("place", "41500 Alcalá de Guadaíra").param("owner_id", "12"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("animalshelter")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("animalshelter", "cif"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/createOrUpdateAnimalshelterForm"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testFindMyAnimalShelter() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter", AnimalshelterControllerTests.TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Shelter")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("Pichú Animales"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("41410 La Celada")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Seville"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("610839583")))).andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelter/animalshelterShow"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testFindOwnersWithQuest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owners"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/animalshelterList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowRecordList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter/records")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("records"))
			.andExpect(MockMvcResultMatchers.view().name("records/recordList"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testInitRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/myAnimalShelter/records/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("record"))
			.andExpect(MockMvcResultMatchers.view().name("records/createOrUpdateRecordForm"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testProcessRecordFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/myAnimalShelter/records/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("owner_id", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testProcessRecordFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/myAnimalShelter/records/new").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/notification/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
			.andExpect(MockMvcResultMatchers.view().name("animalshelter/notification/notificationList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowNotification() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/animalshelter/notification/{notificationId}", AnimalshelterControllerTests.TEST_NOTIFICATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title", Matchers.is("¡Bienvenidas protectoras!"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("date", Matchers.is(LocalDateTime.of(2013, 01, 04, 12, 32)))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message", Matchers.is("Quiero daros la bienvenida a todas las protectoras"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target", Matchers.is("animal_shelter")))).andExpect(MockMvcResultMatchers.view().name("animalshelter/notification/notificationShow"));
	}
}
