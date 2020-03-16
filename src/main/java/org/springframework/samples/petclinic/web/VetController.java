/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String			PETS_LIST			= "vets/petList";
	private static final String			PETS_SHOW			= "vets/petShow";
	private static final String			NOTIFICATION_LIST	= "vets/notification/notificationList";
	private static final String			NOTIFICATION_SHOW	= "vets/notification/notificationShow";
	private static final String			APPOINTMENT_LIST	= "vets/appointment/appointmentsList";
	private static final String			APPOINTMENT_SHOW	= "vets/appointment/appointmentsShow";

	private final VetService			vetService;
	private final PetService			petService;
	private final NotificationService	notificationService;
	private final AppointmentService	appointmentService;


	@Autowired
	public VetController(final AppointmentService appointmentService, final VetService clinicService, final PetService petService, final NotificationService notificationService) {
		this.vetService = clinicService;
		this.petService = petService;
		this.notificationService = notificationService;
		this.appointmentService = appointmentService;
	}

	@GetMapping(value = {
		"/vets"
	})
	public String showVetList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = {
		"/vets.xml"
	})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@GetMapping(value = {
		"/pets"
	})
	public String showPetList(final Map<String, Object> model) {
		final Iterable<Pet> pets = this.petService.findAllPets();
		model.put("pets", pets);
		return VetController.PETS_LIST;
	}

	@GetMapping(value = {
		"/pets/{petId}"
	})
	public String showPet(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		final Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return VetController.PETS_SHOW;
	}

	// ------------------------------------------------ Notification --------------------------------------------

	@GetMapping("vets/notification/")
	public String notificationList(final Map<String, Object> model) {
		final Iterable<Notification> notifications = this.notificationService.findAllForVets();
		model.put("notifications", notifications);
		return VetController.NOTIFICATION_LIST;
	}

	@GetMapping("vets/notification/{notificationId}")
	public String notificationShow(final Map<String, Object> model, @PathVariable final int notificationId) {
		final Notification notification = this.notificationService.findNotificationById(notificationId);
		if (notification.getTarget().equals("veterinarian")) {
			model.put("notification", notification);
			return VetController.NOTIFICATION_SHOW;
		}
		return "redirect:/oups";
	}

	// ------------------------------------------------ Appoitment --------------------------------------------

	//Historia de usuario 17: muestra todas las citas del veterinario loggeado y las citas futuras
	@GetMapping("vets/appointment")
	public String appointmentList(final Map<String, Object> model) {

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username = auth.getName();
		final Vet v = this.vetService.finVetByUsername(username);

		final Iterable<Appointment> appointments = this.appointmentService.findAllByVet(v.getId());

		model.put("appointments", appointments);

		return VetController.APPOINTMENT_LIST;
	}

	@GetMapping("vets/appointment/{appointmentId}")
	public String appointmentShow(final Map<String, Object> model, @PathVariable("appointmentId") final int appointmentId) {
		final Appointment appointment = this.appointmentService.findOneById(appointmentId);
		model.put("appointment", appointment);
		return VetController.APPOINTMENT_SHOW;
	}

}
