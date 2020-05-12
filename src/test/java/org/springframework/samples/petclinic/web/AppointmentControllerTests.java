
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AppointmentControllerTests {

	private static final int		TEST_APPOINTMENT_ID	= 1;

	private static final int		TEST_OWNER_ID		= 6;

	private static final int		TEST_PET_ID			= 7;

	private static final int		TEST_VET_ID			= 1;

	@Autowired
	private AppointmentController	appointmentController;

	@MockBean
	private AppointmentService		appointmentService;

	@MockBean
	private OwnerService			ownerService;

	@MockBean
	private PetService				petService;

	@MockBean
	private VetService				vetService;

	@Autowired
	private MockMvc					mockMvc;

	private Appointment				ap;

	private Owner					ow;

	private Pet						pet;

	private Vet						vet;


	@BeforeEach
	void setup() {

		this.ap = new Appointment();
		this.ap.setId(AppointmentControllerTests.TEST_APPOINTMENT_ID);
		this.ap.setCause("No come nada");
		this.ap.setDate(LocalDate.of(2029, 4, 1));
		this.ap.setUrgent(true);
		this.ap.setOwner(this.ownerService.findOwnerById(AppointmentControllerTests.TEST_OWNER_ID));
		this.ap.setPet(this.petService.findPetById(AppointmentControllerTests.TEST_PET_ID));
		this.ap.setVet_id(AppointmentControllerTests.TEST_VET_ID);
		BDDMockito.given(this.appointmentService.findOneById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).willReturn(new Appointment());

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitNewAppointmentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/{petId}/appointment/new", AppointmentControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("appointment")).andExpect(MockMvcResultMatchers.view().name("appointment/createOrUpdateAppointmentForm"));
	}
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/pets/{petId}/appointment/new", AppointmentControllerTests.TEST_OWNER_ID, AppointmentControllerTests.TEST_PET_ID).param("cause", "No come nada")
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("urgent", "true")
			.param("date", "2020/12/12")
		.param("vet_id", "1"))
.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/pets/{petId}/appointment/new", AppointmentControllerTests.TEST_OWNER_ID, AppointmentControllerTests.TEST_PET_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("urgent", "null"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "cause"))
			.andExpect(MockMvcResultMatchers.view().name("appointment/createOrUpdateAppointmentForm"));
	}

}
