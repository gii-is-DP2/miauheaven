
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class AppointmentControllerTests {

	private static final int		TEST_APPOINTMENT_ID	= 1;

	private static final int		TEST_OWNER_ID		= 6;

	private static final int		TEST_PET_ID			= 7;

	private static final int		TEST_VET_ID			= 2;

	@Autowired
	private AppointmentController	appointmentController;

	@MockBean
	private AppointmentService		appointmentService;

	@Autowired
	private MockMvc					mockMvc;

	private Appointment				ap;


	@BeforeEach
	void setup() {

		this.ap = new Appointment();
		this.ap.setId(AppointmentControllerTests.TEST_APPOINTMENT_ID);
		this.ap.setCause("No come nada");
		this.ap.setDate(LocalDate.now());
		this.ap.setUrgent(true);
		this.ap.setOwner(AppointmentControllerTests.TEST_OWNER_ID);
		this.ap.setPet(AppointmentControllerTests.TEST_PET_ID);
		this.ap.setVet_id(AppointmentControllerTests.TEST_VET_ID);
		BDDMockito.given(this.appointmentService.findOneById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).willReturn(this.ap);

	}

}
