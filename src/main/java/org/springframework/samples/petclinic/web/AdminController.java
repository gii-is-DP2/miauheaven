
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final String			APPOINTMENT_LIST	= "admin/appointmentsList";
	private static final String			APPOINTMENT_SHOW	= "admin/appointmentsShow";

	private final AppointmentService	appointmentService;


	@Autowired
	public AdminController(final AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	@GetMapping(value = "/appointment")
	public String appointmentList(final Map<String, Object> model) {
		Iterable<Appointment> appointments = this.appointmentService.findAll();
		model.put("appointments", appointments);
		return AdminController.APPOINTMENT_LIST;
	}

	@GetMapping(value = "/appointment/{appointmentId}")
	public String appointmentShow(final Map<String, Object> model, @PathVariable("appointmentId") final int appointmentId) {
		Appointment appointment = this.appointmentService.findOneById(appointmentId);
		model.put("appointment", appointment);
		return AdminController.APPOINTMENT_SHOW;

	}

}
