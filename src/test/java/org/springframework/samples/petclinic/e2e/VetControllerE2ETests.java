
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.hamcrest.xml.HasXPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.web.VetController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for the {@link VetController}
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext
//@TestPropertySource(locations = "classpath:application-mysql.properties")
class VetControllerE2ETests {

	private static final int	TEST_PET_ID				= 1;
	private static final int	TEST_NOTIFICATION_ID	= 3;
	private static final int	TEST_APPOINTMENT_ID		= 2;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowVetListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vets")).andExpect(MockMvcResultMatchers.view().name("vets/vetList"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowVetListXml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets.xml").accept(MediaType.APPLICATION_XML)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_XML_VALUE))
			.andExpect(MockMvcResultMatchers.content().node(HasXPath.hasXPath("/vets/vetList[id=1]/id")));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowPetList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/pets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("pets")).andExpect(MockMvcResultMatchers.view().name("vets/petList"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowPet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/pets/{petId}", VetControllerE2ETests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("name", Matchers.is("Leo")))).andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("birthDate", Matchers.is(LocalDate.of(2010, 9, 7)))))
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("genre", Matchers.is("female")))).andExpect(MockMvcResultMatchers.view().name("vets/petShow"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/notification")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
			.andExpect(MockMvcResultMatchers.view().name("vets/notification/notificationList"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowNotification() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/notification/{notificationId}", VetControllerE2ETests.TEST_NOTIFICATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title", Matchers.is("¡Bienvenidos veterinarios!"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("date", Matchers.is(LocalDateTime.of(2013, 01, 04, 13, 32)))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message", Matchers.is("Quiero daros la bienvenida a todos los veterinarios"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target", Matchers.is("veterinarian")))).andExpect(MockMvcResultMatchers.view().name("vets/notification/notificationShow"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowAppointmentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/appointment")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
			.andExpect(MockMvcResultMatchers.view().name("vets/appointment/appointmentsList"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowAppointment() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets/appointment/{appointmentId}", VetControllerE2ETests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("cause", Matchers.is("Operacion"))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 12, 1)))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("urgent", Matchers.is(true)))).andExpect(MockMvcResultMatchers.view().name("vets/appointment/appointmentsShow"));
	}

}
