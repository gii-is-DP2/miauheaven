
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.hamcrest.xml.HasXPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(controllers = VetController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class VetControllerTests {

	private static final int	TEST_PET_ID				= 1;
	private static final int	TEST_NOTIFICATION_ID	= 3;
	private static final int	TEST_APPOINTMENT_ID		= 2;
	@Autowired
	private VetController		vetController;

	@MockBean
	private VetService			clinicService;
	@MockBean
	private NotificationService	notificationService;
	@MockBean
	private AppointmentService	appointmentService;
	@MockBean
	private PetService			petService;

	@Autowired
	private MockMvc				mockMvc;

	private Pet					leo;
	private Notification		notification;
	private Vet					vet1;
	private Appointment			appointment;


	@BeforeEach
	void setup() {

		Vet james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		Vet helen = new Vet();
		helen.setFirstName("Helen");
		helen.setLastName("Leary");
		helen.setId(2);
		Specialty radiology = new Specialty();
		radiology.setId(1);
		radiology.setName("radiology");
		helen.addSpecialty(radiology);
		BDDMockito.given(this.clinicService.findVets()).willReturn(Lists.newArrayList(james, helen));
		this.leo = new Pet();
		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		Owner george = new Owner();
		george.setId(1);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");

		this.leo.setId(VetControllerTests.TEST_PET_ID);
		this.leo.setBirthDate(LocalDate.of(2010, 9, 7));
		this.leo.setGenre("female");
		this.leo.setName("Leo");
		this.leo.setType(cat);
		this.leo.setVisits(new HashSet<Visit>());
		this.leo.changeOwner(george);
		BDDMockito.given(this.petService.findPetById(VetControllerTests.TEST_PET_ID)).willReturn(this.leo);

		this.notification = new Notification();
		this.notification.setId(VetControllerTests.TEST_NOTIFICATION_ID);
		this.notification.setDate(LocalDateTime.of(2013, 01, 04, 12, 32));
		this.notification.setMessage("Quiero daros la bienvenida a todos los veterinarios");
		this.notification.setTarget("veterinarian");
		this.notification.setTitle("¡Bienvenidos veterinarios!");
		BDDMockito.given(this.notificationService.findNotificationById(VetControllerTests.TEST_NOTIFICATION_ID)).willReturn(this.notification);

		this.vet1 = new Vet();
		this.vet1.setId(1);
		this.vet1.setFirstName("James");
		this.vet1.setLastName("Carter");
		User user = new User();
		user.setEnabled(true);
		user.setUsername("vet1");
		user.setPassword("v3t");
		this.vet1.setUser(user);
		BDDMockito.given(this.clinicService.findVetByUsername("vet1")).willReturn(this.vet1);

		this.appointment = new Appointment();
		this.appointment.setCause("Operacion");
		this.appointment.setDate(LocalDate.of(2020, 5, 1));
		this.appointment.setId(VetControllerTests.TEST_APPOINTMENT_ID);
		this.appointment.setUrgent(true);
		this.appointment.setVet(this.vet1);
		this.appointment.setPet(this.leo);
		this.appointment.setOwner(george);
		BDDMockito.given(this.appointmentService.findOneById(VetControllerTests.TEST_APPOINTMENT_ID)).willReturn(this.appointment);

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVetListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vets")).andExpect(MockMvcResultMatchers.view().name("vets/vetList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVetListXml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets.xml").accept(MediaType.APPLICATION_XML)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_XML_VALUE))
			.andExpect(MockMvcResultMatchers.content().node(HasXPath.hasXPath("/vets/vetList[id=1]/id")));
	}
	
	 // ---------------------------------------------------------------- HU.18 ----------------------------------------------------------------------------------------------------
	
	
	@Test //+
	@WithMockUser(value = "spring")
	void testShowPetList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/pets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("pets")).andExpect(MockMvcResultMatchers.view().name("vets/petList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowPet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/pets/{petId}", VetControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("name", Matchers.is("Leo")))).andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("birthDate", Matchers.is(LocalDate.of(2010, 9, 7)))))
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("genre", Matchers.is("female")))).andExpect(MockMvcResultMatchers.view().name("vets/petShow"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/notification")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
			.andExpect(MockMvcResultMatchers.view().name("vets/notification/notificationList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowNotification() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/notification/{notificationId}", VetControllerTests.TEST_NOTIFICATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title", Matchers.is("¡Bienvenidos veterinarios!"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("date", Matchers.is(LocalDateTime.of(2013, 01, 04, 12, 32)))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message", Matchers.is("Quiero daros la bienvenida a todos los veterinarios"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target", Matchers.is("veterinarian")))).andExpect(MockMvcResultMatchers.view().name("vets/notification/notificationShow"));
	}

	@WithMockUser(value = "vet1")
	@Test
	void testShowAppointmentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/appointment")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
			.andExpect(MockMvcResultMatchers.view().name("vets/appointment/appointmentsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAppointment() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/appointment/{appointmentId}", VetControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("cause", Matchers.is("Operacion"))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 5, 1)))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("urgent", Matchers.is(true)))).andExpect(MockMvcResultMatchers.view().name("vets/appointment/appointmentsShow"));
	}


}
