
package org.springframework.samples.petclinic.e2e;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class QuestionnaireControllerE2ETest {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_PET_ID		= 1;
	private static final int	TEST_QUEST_ID	= 1;


	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerE2ETest.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("questionnaire")).andExpect(MockMvcResultMatchers.view().name("questionnaire/createOrUpdateQuestionnaire"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerE2ETest.TEST_PET_ID).param("vivienda", "Casa").with(SecurityMockMvcRequestPostProcessors.csrf()).param("ingresos", "Altos")
			.param("convivencia", "No").param("horas_libres", "Entre 3 y 6 horas")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerE2ETest.TEST_PET_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("vivienda", "Casa")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("ingresos", "Altos").param("horas_libres", "Entre 3 y 6 horas"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("questionnaire")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("questionnaire", "convivencia"))
			.andExpect(MockMvcResultMatchers.view().name("questionnaire/createOrUpdateQuestionnaire"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testAnimalshelterList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/{petId}", QuestionnaireControllerE2ETest.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("questionnaire")).andExpect(MockMvcResultMatchers.view().name("questionnaire/questionnaireList"));

	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})

	@Test
	void testShowQuestionnaire() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/show/{questId}", QuestionnaireControllerE2ETest.TEST_QUEST_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("vivienda", Matchers.is("Casa"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("ingresos", Matchers.is("Altos"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("horasLibres", Matchers.is("Entre 3 y 6 horas"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("convivencia", Matchers.is("Sí")))).andExpect(MockMvcResultMatchers.view().name("questionnaire/questonnaireShow"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})

	@Test
	void testShowQuestionnaireFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/show/{questId}", QuestionnaireControllerE2ETest.TEST_QUEST_ID)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testAceptaAdopción() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/accept/{questId}", QuestionnaireControllerE2ETest.TEST_QUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "shelter1", authorities = {
		"animalshelter"
	})
	@Test
	void testAceptAdoptionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/accept/{questId}", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testAceptAdoptionForbidden() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/accept/{questId}", 2)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}

}
