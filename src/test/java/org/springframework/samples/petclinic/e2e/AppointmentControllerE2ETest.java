
package org.springframework.samples.petclinic.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AppointmentControllerE2ETest {

	private static final int	TEST_OWNER_ID	= 6;

	private static final int	TEST_PET_ID		= 7;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testInitNewAppointmentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/{petId}/appointment/new", AppointmentControllerE2ETest.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("appointment")).andExpect(MockMvcResultMatchers.view().name("appointment/createOrUpdateAppointmentForm"));
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/pets/{petId}/appointment/new", AppointmentControllerE2ETest.TEST_OWNER_ID, AppointmentControllerE2ETest.TEST_PET_ID).param("cause", "Tiene rota la patita")
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("urgent", "true")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/owners/{ownerId}/pets/{petId}/appointment/new", AppointmentControllerE2ETest.TEST_OWNER_ID, AppointmentControllerE2ETest.TEST_PET_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("urgent", "null"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "cause"))
			.andExpect(MockMvcResultMatchers.view().name("appointment/createOrUpdateAppointmentForm"));
	}
}
