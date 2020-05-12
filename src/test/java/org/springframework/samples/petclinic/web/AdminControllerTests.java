
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
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ProductService;
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

	private static final int TEST_ADMIN_ID = 1;
	private static final int TEST_NOTIFICATION_ID = 1;
	private static final int TEST_APPOINTMENT_ID = 2;
	private static final int TEST_PET_ID = 1;
	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PRODUCT_ID = 1;
	private static final int TEST_QUESTIONNAIRE_ID = 1;
	private static final int TEST_EVENT_ID = 1;

	@Autowired
	private AdminController adminController;

	@MockBean
	private OwnerService clinicService;

	@MockBean
	private QuestionnaireService questionnaireService;

	@MockBean
	private NotificationService notificationService;

	@MockBean
	private PetService petService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private ProductService productService;
	@MockBean
	private EventService eventService;
	
	@Autowired
	private MockMvc mockMvc;
	private Pet leo;
	private Owner george;
	private Vet vet1;
	private Appointment appointment;
	private Notification notification;
	private Product product;
	private Questionnaire questionnaire;
	private Event event;

	@BeforeEach
	void setup() {

		this.event = new Event();
		this.event.setId(AdminControllerTests.TEST_EVENT_ID);
		this.event.setName("Prueba");
		this.event.setDescription("Prueba");
		this.event.setDate(LocalDate.now());

		BDDMockito.given(this.eventService.findEventById(AdminControllerTests.TEST_EVENT_ID)).willReturn(this.event);

		
		this.questionnaire = new Questionnaire();
		this.questionnaire.setId(AdminControllerTests.TEST_QUESTIONNAIRE_ID);
		this.questionnaire.setIngresos("Altos");
		this.questionnaire.setVivienda("Casa");
		this.questionnaire.setHorasLibres("Más de 6 horas");
		this.questionnaire.setConvivencia("Sí");
		this.questionnaire.setOwner(george);
		this.questionnaire.setPet(leo);
		BDDMockito.given(this.questionnaireService.findOneById(AdminControllerTests.TEST_QUESTIONNAIRE_ID)).willReturn(this.questionnaire);
		
	
		
		this.product= new Product();
		this.product.setId(AdminControllerTests.TEST_PRODUCT_ID);
		this.product.setName("Prueba");
		this.product.setDescription("Prueba");
		this.product.setPrice(10.00);
		this.product.setStock(true);
		this.product.setImage("Prueba");
		BDDMockito.given(this.productService.findProductById(AdminControllerTests.TEST_PRODUCT_ID)).willReturn(this.product);
		
		
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
		this.leo = new Pet();
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
		
		Vet james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		this.vet1 = new Vet();
		this.vet1.setId(1);
		this.vet1.setFirstName("James");
		this.vet1.setLastName("Carter");
		User user = new User();
		user.setEnabled(true);
		user.setUsername("vet1");
		user.setPassword("v3t");
		this.vet1.setUser(user);

		this.appointment = new Appointment();
		this.appointment.setCause("Operacion");
		this.appointment.setDate(LocalDate.of(2020, 5, 1));
		this.appointment.setId(AdminControllerTests.TEST_APPOINTMENT_ID);
		this.appointment.setUrgent(true);
		this.appointment.setVet(this.vet1);
		this.appointment.setPet(this.leo);
		this.appointment.setOwner(george);
		BDDMockito.given(this.appointmentService.findOneById(AdminControllerTests.TEST_APPOINTMENT_ID)).willReturn(this.appointment);
		
		
	}
	// ------------------------------------------------ Notification
	// ------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	void testNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/notification/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
				.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotificationShow() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/notification/{notificationId}",
						AdminControllerTests.TEST_NOTIFICATION_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
				.andExpect(MockMvcResultMatchers.model().attribute("notification",
						Matchers.hasProperty("id", Matchers.is(AdminControllerTests.TEST_NOTIFICATION_ID))))
				.andExpect(MockMvcResultMatchers.model().attribute("notification",
						Matchers.hasProperty("title", Matchers.is("Prueba"))))
				.andExpect(MockMvcResultMatchers.model().attribute("notification",
						Matchers.hasProperty("message", Matchers.is("Prueba"))))
				.andExpect(MockMvcResultMatchers.model().attribute("notification",
						Matchers.hasProperty("target", Matchers.is("admin"))))
				.andExpect(MockMvcResultMatchers.model().attribute("notification",
						Matchers.hasProperty("url", Matchers.is("www.us.es"))))
				.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationShow"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/notification/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
				.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationCreate"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/notification/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Prueba").param("message", "Prueba"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/admin/notification/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Prueba"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("notification"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("notification", "message"))
				.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationCreate"));
	}

	// ------------------------------------------------ Appointment
	// --------------------------------------------
	@WithMockUser(value = "spring")
	@Test
	void testShowAppointmentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/appointments"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
				.andExpect(MockMvcResultMatchers.view().name("admin/appointment/appointmentsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAppointment() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/admin/appointments/{appointmentId}", AdminControllerTests.TEST_APPOINTMENT_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("appointment",
						Matchers.hasProperty("cause", Matchers.is("Operacion"))))
				.andExpect(MockMvcResultMatchers.model().attribute("appointment",
						Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 5, 1)))))
				.andExpect(MockMvcResultMatchers.model().attribute("appointment",
						Matchers.hasProperty("urgent", Matchers.is(true))))
				.andExpect(MockMvcResultMatchers.view().name("admin/appointment/appointmentsShow"));
	}

	// ------------------------------------------------ Product
	// ---------------------------------------------------
	@WithMockUser(value = "spring")
	@Test
	void testProductList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("products"))
				.andExpect(MockMvcResultMatchers.view().name("admin/product/productList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProductShow() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/product/{productId}", AdminControllerTests.TEST_PRODUCT_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("product"))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("id", Matchers.is(AdminControllerTests.TEST_PRODUCT_ID))))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("price", Matchers.is(10.00))))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("name", Matchers.is("Prueba"))))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("stock", Matchers.is(true))))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("description", Matchers.is("Prueba"))))
				.andExpect(MockMvcResultMatchers.model().attribute("product",
						Matchers.hasProperty("image", Matchers.is("Prueba"))))
				.andExpect(MockMvcResultMatchers.view().name("admin/product/productShow"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/create"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("product"))
				.andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateFormProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/update/{productId}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("product"))
				.andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccessProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/product/save")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("price", "10.00").param("stock", "true"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsProduct() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/admin/product/save")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("price", "10.00"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("product"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("product", "stock"))
				.andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	// ------------------------------------------------ PetList
	// --------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	void testShowPetList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/pets")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pets"))
				.andExpect(MockMvcResultMatchers.view().name("admin/pet/petList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowPet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/pets/{petId}", AdminControllerTests.TEST_PET_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("pet",
						Matchers.hasProperty("name", Matchers.is("Leo"))))
				.andExpect(MockMvcResultMatchers.model().attribute("pet",
						Matchers.hasProperty("birthDate", Matchers.is(LocalDate.of(2010, 9, 7)))))
				.andExpect(MockMvcResultMatchers.model().attribute("pet",
						Matchers.hasProperty("genre", Matchers.is("female"))))
				.andExpect(MockMvcResultMatchers.view().name("admin/pet/petShow"));
	}

	// ------------------------------------------------ Questionnaires
	// --------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	void testShowQuestionnairesList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/questionnaires"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("questionnaires"))
				.andExpect(MockMvcResultMatchers.view().name("admin/questionnaires/questionnaireList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowQuestionnaires() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/questionnaires/{questionnaireId}", AdminControllerTests.TEST_QUESTIONNAIRE_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("questionnaire",
						Matchers.hasProperty("ingresos", Matchers.is("Altos"))))
				.andExpect(MockMvcResultMatchers.model().attribute("questionnaire",
						Matchers.hasProperty("vivienda", Matchers.is("Casa"))))
				.andExpect(MockMvcResultMatchers.model().attribute("questionnaire",
						Matchers.hasProperty("horasLibres", Matchers.is("Más de 6 horas"))))
				.andExpect(MockMvcResultMatchers.model().attribute("questionnaire",
						Matchers.hasProperty("convivencia", Matchers.is("Sí"))))
				.andExpect(MockMvcResultMatchers.view().name("admin/questionnaires/questionnaireShow"));
	}
	// ------------------------------------------------ Event
	// --------------------------------------------
	@WithMockUser(value = "spring")
	@Test
	void testEventList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/events/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("events"))
		.andExpect(MockMvcResultMatchers.view().name("admin/events/eventsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEventShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/events/{eventId}", AdminControllerTests.TEST_EVENT_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("event"))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("id", Matchers.is(AdminControllerTests.TEST_EVENT_ID))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("name", Matchers.is("Prueba"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("description", Matchers.is("Prueba"))))
			.andExpect(MockMvcResultMatchers.model().attribute("event", Matchers.hasProperty("date", Matchers.is(LocalDate.now()))))
			.andExpect(MockMvcResultMatchers.view().name("admin/events/eventShow"));
	}
}
