
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
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = QuestionnaireController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class QuestionnaireControllerTests {

	private static final int		TEST_QUEST_ID		= 1;
	private static final int		TEST_QUEST_ID_FAIL	= 2;
	private static final int		TEST_PET_ID			= 15;
	private static final int		TEST_OWNER_ID		= 11;

	@Autowired
	private QuestionnaireController	questController;
	@MockBean
	private QuestionnaireService	questService;
	@MockBean
	private OwnerService			ownerService;
	@MockBean
	private PetService				petService;

	@Autowired
	private MockMvc					mockMvc;

	private Pet						desto;
	private Questionnaire			quest;
	private Owner					george;
	private Owner					pichu;
	private Animalshelter			as;

	private Owner					shelter;


	@BeforeEach
	void setup() {
		this.pichu = new Owner();
		this.pichu.setId(11);
		this.pichu.setFirstName("Pichú Animales");
		this.pichu.setLastName("Shelter");
		this.pichu.setAddress("41410 La Celada");
		this.pichu.setCity("Seville");
		this.pichu.setTelephone("610839583");
		User user = new User();
		user.setEnabled(true);
		user.setUsername("shelter1");
		user.setPassword("shelter1");
		this.pichu.setUser(user);

		this.as = new Animalshelter();
		this.as.setId(1);
		this.as.setName("Pichú Animales");
		this.as.setCif("12345678A");
		this.as.setPlace("41410 La Celada");
		this.as.setOwner(this.pichu);

		this.desto = new Pet();
		this.desto.setId(QuestionnaireControllerTests.TEST_PET_ID);
		this.desto.setBirthDate(LocalDate.of(2016, 6, 18));
		this.desto.setGenre("male");
		PetType dog = new PetType();
		dog.setId(3);
		dog.setName("dog");
		this.desto.setType(dog);
		this.pichu.addPet(this.desto);
		BDDMockito.given(this.petService.findPetById(QuestionnaireControllerTests.TEST_PET_ID)).willReturn(this.desto);

		this.george = new Owner();
		this.george.setId(1);
		this.george.setFirstName("George");
		this.george.setLastName("Franklin");
		this.george.setAddress("110 W. Liberty St.");
		this.george.setCity("Madison");
		this.george.setTelephone("6085551023");
		User user2 = new User();
		user.setEnabled(true);
		user.setUsername("owner1");
		user.setPassword("0wn3r");
		this.george.setUser(user2);

		this.quest = new Questionnaire();
		this.quest.setId(QuestionnaireControllerTests.TEST_QUEST_ID);
		this.quest.setConvivencia("Sí");
		this.quest.setHorasLibres("Entre 3 y 6 horas");
		this.quest.setIngresos("Altos");
		this.quest.setVivienda("Casa");
		this.quest.setName("Quest-George Franklin");
		this.quest.setOwner(this.george);
		this.quest.setPet(this.desto);
		this.quest.setUmbral(4);
		this.quest.setPuntuacion(5);
		BDDMockito.given(this.questService.findQuestionnaireById(QuestionnaireControllerTests.TEST_QUEST_ID)).willReturn(this.quest);

		this.shelter = new Owner();
		this.shelter.setId(QuestionnaireControllerTests.TEST_OWNER_ID);
		this.shelter.setFirstName("Pichú Animales");
		this.shelter.setLastName("Shelter");
		this.shelter.setAddress("41410 La Celada");
		this.shelter.setCity("Seville");
		this.shelter.setTelephone("610839583");
		BDDMockito.given(this.ownerService.findOwnerById(QuestionnaireControllerTests.TEST_OWNER_ID)).willReturn(this.shelter);

		User user1 = new User();
		user1.setEnabled(true);
		user1.setUsername("shelter1");
		user1.setPassword("shelter1");
		this.shelter.setUser(user1);
		BDDMockito.given(this.ownerService.findOwnerByUsername("shelter1")).willReturn(this.shelter);
	}

	@WithMockUser(value = "spring")
	@Test
	void testCrearCuestionario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("questionnaire")).andExpect(MockMvcResultMatchers.view().name("questionnaire/createOrUpdateQuestionnaire"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSalvaCuestionario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerTests.TEST_PET_ID).param("horasLibres", "Entre 3 y 6 horas").with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("ingresos", "Altos").param("vivienda", "Casa")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSalvaCuestionarioFail() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/owners/adoptList/questionnaire/new/{petId}", QuestionnaireControllerTests.TEST_PET_ID).param("horasLibres", "Entre 3 y 6 horas").with(SecurityMockMvcRequestPostProcessors.csrf()).param("ingresos", "")
				.param("vivienda", "Casa"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("questionnaire")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("questionnaire/createOrUpdateQuestionnaire"));
	}

	@WithMockUser(value = "shelter1")
	@Test
	void testShowAnimalShelterList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/{petId}", QuestionnaireControllerTests.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("questionnaire")).andExpect(MockMvcResultMatchers.view().name("questionnaire/questionnaireList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowQuestionnaires() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/show/{questId}", QuestionnaireControllerTests.TEST_QUEST_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("ingresos", Matchers.is("Altos"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("vivienda", Matchers.is("Casa"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("horasLibres", Matchers.is("Entre 3 y 6 horas"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("convivencia", Matchers.is("Sí")))).andExpect(MockMvcResultMatchers.view().name("questionnaire/questonnaireShow"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testAceptAdoptionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/accept/{questId}", QuestionnaireControllerTests.TEST_QUEST_ID_FAIL)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testAceptAdoption() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/adoptList/questionnaire/accept/{questId}", QuestionnaireControllerTests.TEST_QUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
