
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AdminControllerTests {

	private static final int		TEST_ADMIN_ID	= 1;
	private static final int		TEST_NOTIFICATION_ID	= 1;
	private static final int		TEST_APPOINTMENT_ID		= 2;
	private static final int		TEST_PET_ID				= 1;
	private static final int 		TEST_OWNER_ID = 1;
	private static final int 		TEST_PRODUCT_ID = 1;
	@Autowired
	private AdminController			adminController;

	@MockBean
	private OwnerService			clinicService;
	
	@MockBean
	private QuestionnaireService	questionnaireService;
	
	@MockBean
	private NotificationService		notificationService;
	
	@MockBean
	private PetService				petService;
	
	@MockBean
	private UserService				userService;
	
	@MockBean
	private AuthoritiesService		authoritiesService;

	@Autowired
	private MockMvc					mockMvc;
	private Pet						leo;
	private Owner					george;

	private Notification 			notification;
	
	@BeforeEach
	void setup() {

		this.george = new Owner();
		this.george.setId(AdminControllerTests.TEST_OWNER_ID);
		this.george.setFirstName("George");
		this.george.setLastName("Franklin");
		this.george.setAddress("110 W. Liberty St.");
		this.george.setCity("Madison");
		this.george.setTelephone("6085551023");
		BDDMockito.given(this.clinicService.findOwnerById(AdminControllerTests.TEST_OWNER_ID)).willReturn(this.george);

		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("cat");
		this.leo.setId(AdminControllerTests.TEST_PET_ID);
		this.leo.setBirthDate(LocalDate.of(2010, 9, 7));
		this.leo.setGenre("female");
		this.leo.setName("Leo");
		this.leo.setType(cat);
		this.leo.setVisits(new HashSet<Visit>());
		this.leo.changeOwner(george);
		BDDMockito.given(this.petService.findPetById(AdminControllerTests.TEST_PET_ID)).willReturn(this.leo);

		
		this.notification = new Notification();
		this.notification.setId(AdminControllerTests.TEST_NOTIFICATION_ID);
		this.notification.setTitle("Prueba");
		this.notification.setMessage("Prueba");
		this.notification.setDate(LocalDateTime.now());
		this.notification.setTarget("admin");
		this.notification.setUrl("www.us.es");
		
		BDDMockito.given(this.notificationService.findNotificationById(AdminControllerTests.TEST_NOTIFICATION_ID)).willReturn(this.notification);

	}
	// ------------------------------------------------ Notification ------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	void testNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
		.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotificationShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/{notificationId}", AdminControllerTests.TEST_NOTIFICATION_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
		.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("id",Matchers.is(AdminControllerTests.TEST_NOTIFICATION_ID))))
		.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title",Matchers.is("Prueba"))))
		.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message",Matchers.is("Prueba"))))
		.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target",Matchers.is("admin"))))
		.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("url",Matchers.is("www.us.es"))))
		.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationShow"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/new"))
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
			.andExpect(MockMvcResultMatchers.view().name("notification/notificationCreate"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/notification/new")
			.param("title", "Prueba")
			.param("date", "2015/02/12")
			.param("target", "owner")
			.param("url", "us@us.es"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/notification/new").with(SecurityMockMvcRequestPostProcessors
				.csrf()).param("title", "Prueba")
				.param("date", "2015/02/12")
				.param("target", "owner")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("notification"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("notification", "url"))
			.andExpect(MockMvcResultMatchers.view().name("notification/notificationCreate"));
	}
	
	// ------------------------------------------------ Appoitment --------------------------------------------
	@WithMockUser(value = "vet1")
	@Test
	void testShowAppointmentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
			.andExpect(MockMvcResultMatchers.view().name("appointment/appointmentsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAppointment() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}", AdminControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("cause", Matchers.is("Operacion"))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 5, 1)))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("urgent", Matchers.is(true)))).
			andExpect(MockMvcResultMatchers.view().name("appointments/appointmentsShow"));
	}

	
	// ------------------------------------------------ Product ---------------------------------------------------
	@WithMockUser(value = "spring")
	@Test
	void testProductList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("products"))
		.andExpect(MockMvcResultMatchers.view().name("admin/product/productList"));
	}
		
	@WithMockUser(value = "spring")
	@Test
	void testProductShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", AdminControllerTests.TEST_PRODUCT_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
		.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("id",Matchers.is(AdminControllerTests.TEST_PRODUCT_ID))))
		.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price",Matchers.is("10,00"))))
		.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("stock",Matchers.is("true"))))
		.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description",Matchers.is("Prueba"))))
		.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("image",Matchers.is("Prueba"))))
		.andExpect(MockMvcResultMatchers.view().name("admin/product/productShow"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/product/create"))
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.view().name("product/productForm"));
	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormSuccessProduct() throws Exception {
//		this.mockMvc.perform(
//			MockMvcRequestBuilders.post("/product/create")
//			.param("price", "10,00")
//			.param("stock", "true")
//			.param("description", "Prueba")
//			.param("image", "Prueba")
//			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//	}
//	
//
//	
	
	
	
	

	// ------------------------------------------------ PetList --------------------------------------------
	
	
	@WithMockUser(value = "spring")
	@Test
	void testShowPetList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pets"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("pets"))
		.andExpect(MockMvcResultMatchers.view().name("admin/petList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowPet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pets/{petId}", AdminControllerTests.TEST_PET_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("name", Matchers.is("Leo"))))
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("birthDate", Matchers.is(LocalDate.of(2010, 9, 7)))))
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("genre", Matchers.is("female"))))
			.andExpect(MockMvcResultMatchers.view().name("admin/petShow"));
	}
	
	

}
