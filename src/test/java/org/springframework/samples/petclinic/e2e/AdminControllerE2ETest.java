
package org.springframework.samples.petclinic.e2e;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(locations = "classpath:application-mysql.properties")
class AdminControllerE2ETest {

	private static final int	TEST_ADMIN_ID			= 1;
	private static final int	TEST_NOTIFICATION_ID	= 1;
	private static final int	TEST_APPOINTMENT_ID		= 2;
	private static final int	TEST_PET_ID				= 1;
	private static final int	TEST_OWNER_ID			= 1;
	private static final int	TEST_PRODUCT_ID			= 1;
	private static final int	TEST_QUESTIONNAIRE_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	// ------------------------------------------------ Notification
	// ------------------------------------------

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotificationList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/notification/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notifications"))
			.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotificationShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/notification/{notificationId}", AdminControllerE2ETest.TEST_NOTIFICATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("notification")).andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("id", Matchers.is(AdminControllerE2ETest.TEST_NOTIFICATION_ID))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("title", Matchers.is("¡Bienvenidos propietarios de animales!"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("message", Matchers.is("Quiero daros la bienvenida a todos los propietarios de animales"))))
			.andExpect(MockMvcResultMatchers.model().attribute("notification", Matchers.hasProperty("target", Matchers.is("owner"))))

			.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationShow"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/notification/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("notification"))
			.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationCreate"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/notification/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Prueba").param("message", "Prueba")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/notification/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Prueba")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("notification")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("notification", "message"))
			.andExpect(MockMvcResultMatchers.view().name("admin/notification/notificationCreate"));
	}

	// ------------------------------------------------ Appointment
	// --------------------------------------------
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowAppointmentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/appointments")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
			.andExpect(MockMvcResultMatchers.view().name("admin/appointment/appointmentsList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowAppointment() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/appointments/{appointmentId}", AdminControllerE2ETest.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("cause", Matchers.is("Operacion"))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 12, 1)))))
			.andExpect(MockMvcResultMatchers.model().attribute("appointment", Matchers.hasProperty("urgent", Matchers.is(true)))).andExpect(MockMvcResultMatchers.view().name("admin/appointment/appointmentsShow"));
	}

	// ------------------------------------------------ Product
	// ---------------------------------------------------
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProductList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("products"))
			.andExpect(MockMvcResultMatchers.view().name("admin/product/productList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProductShow() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/{productId}", AdminControllerE2ETest.TEST_PRODUCT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("id", Matchers.is(AdminControllerE2ETest.TEST_PRODUCT_ID))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price", Matchers.is(12.2)))).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("name", Matchers.is("Comida para perros"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("stock", Matchers.is(true))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description", Matchers.is("La mejor comida para alimentar a nuestros compañeros caninos"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("image", Matchers.is("https://www.petpremium.com/wp-content/uploads/2012/10/5-healthy-dog-foods-430x226.jpg"))))
			.andExpect(MockMvcResultMatchers.view().name("admin/product/productShow"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitCreationFormProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/create")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitUpdateFormProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/product/update/{productId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormSuccessProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/product/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("price", "10.00").param("stock", "true")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormHasErrorsProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/product/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("price", "10.00")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("product")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("product", "stock")).andExpect(MockMvcResultMatchers.view().name("admin/product/productForm"));
	}

	// ------------------------------------------------ PetList
	// --------------------------------------------

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowPetList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/pets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("pets")).andExpect(MockMvcResultMatchers.view().name("admin/pet/petList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowPet() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/pets/{petId}", AdminControllerE2ETest.TEST_PET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("name", Matchers.is("Leo")))).andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("birthDate", Matchers.is(LocalDate.of(2010, 9, 7)))))
			.andExpect(MockMvcResultMatchers.model().attribute("pet", Matchers.hasProperty("genre", Matchers.is("female")))).andExpect(MockMvcResultMatchers.view().name("admin/pet/petShow"));
	}

	// ------------------------------------------------ Questionnaires
	// --------------------------------------------

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowQuestionnairesList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/questionnaires")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("questionnaires"))
			.andExpect(MockMvcResultMatchers.view().name("admin/questionnaires/questionnaireList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowQuestionnaires() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/questionnaires/{questionnaireId}", AdminControllerE2ETest.TEST_QUESTIONNAIRE_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("ingresos", Matchers.is("Altos"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("vivienda", Matchers.is("Casa"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("horasLibres", Matchers.is("Entre 3 y 6 horas"))))
			.andExpect(MockMvcResultMatchers.model().attribute("questionnaire", Matchers.hasProperty("convivencia", Matchers.is("Sí")))).andExpect(MockMvcResultMatchers.view().name("admin/questionnaires/questionnaireShow"));
	}

}
