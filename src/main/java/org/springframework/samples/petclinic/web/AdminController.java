
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AnimalshelterService;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final String			APPOINTMENT_LIST	= "admin/notification/appointmentsList";
	private static final String			APPOINTMENT_SHOW	= "admin/notification/appointmentsShow";
	private static final String			PETS_LIST			= "admin/pet/petList";
	private static final String			PETS_SHOW			= "admin/pet/petShow";
	private static final String			NOTIFICATION_CREATE	= "admin/notification/notificationCreate";
	private static final String			NOTIFICATION_LIST	= "admin/notification/notificationList";
	private static final String			NOTIFICATION_SHOW	= "admin/notification/notificationShow";

	private final AppointmentService	appointmentService;
	private final PetService			petService;
	private final OwnerService			ownerService;
	private final VetService			vetService;
	private final AnimalshelterService	animalShelterService;
	private final NotificationService	notificationService;


	@Autowired
	public AdminController(final AppointmentService appointmentService, final PetService petService, final OwnerService ownerService, final VetService vetService, final AnimalshelterService animalShelterService,
		final NotificationService notificationService) {
		this.appointmentService = appointmentService;
		this.petService = petService;
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.animalShelterService = animalShelterService;
		this.notificationService = notificationService;
	}

	// ------------------------------------------------ Notification ------------------------------------------

	@GetMapping(value = "/notification/")
	public String notificationsList(final Map<String, Object> model) {
		Iterable<Notification> notifications = this.notificationService.findAll();
		model.put("notifications", notifications);
		return AdminController.NOTIFICATION_LIST;
	}

	@GetMapping(value = "/notification/{appointmentId}")
	public String notificationsShow(final Map<String, Object> model, @PathVariable final int appointmentId) {
		Notification notification = this.notificationService.findNotificationById(appointmentId);
		model.put("notification", notification);
		return AdminController.NOTIFICATION_SHOW;
	}

	@GetMapping(value = "/notification/new")
	public String initNotificationCreate(final Map<String, Object> model) {
		Notification notification = new Notification();
		List<String> targets = new ArrayList<>();
		targets.add("owner");
		targets.add("veterinarian");
		targets.add("animal_shelter");
		model.put("notification", notification);
		model.put("targets", targets);
		return AdminController.NOTIFICATION_CREATE;
	}

	@PostMapping(value = "/notification/new")
	public String notificationCreate(@Valid final Notification notification, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			List<String> targets = new ArrayList<>();
			targets.add("owner");
			targets.add("veterinarian");
			targets.add("animal_shelter");
			model.put("notification", notification);
			model.put("targets", targets);
			return AdminController.NOTIFICATION_CREATE;
		} else {

			Calendar calendar = Calendar.getInstance();
			notification.setDate(calendar.getTime());

			/*
			 * switch (notification.getTarget()) {
			 *
			 * case "owner":
			 * Iterable<Owner> owners = this.ownerService.findAllOwner();
			 * for (Owner i : owners) {
			 * i.addNotification(notification);
			 * this.ownerService.saveOwner(i);
			 * }
			 * break;
			 *
			 * case "veterinarian":
			 * Iterable<Vet> vets = this.vetService.findVets();
			 * for (Vet i : vets) {
			 * i.addNotification(notification);
			 * this.vetService.save(i);
			 * }
			 * break;
			 *
			 * case "animal shelter":
			 * Iterable<Animalshelter> animalShelters = this.animalShelterService.findAnimalshelters();
			 * for (Animalshelter i : animalShelters) {
			 * i.addNotification(notification);
			 * this.animalShelterService.save(i);
			 * }
			 * break;
			 * }
			 */

			this.notificationService.save(notification);
			return "redirect:/admin/notification/";
		}
	}

	@GetMapping(value = "/notification/delete/{notificationId}")
	public String notificationCreate(final Map<String, Object> model, @PathVariable final int notificationId) {
		Notification notification = this.notificationService.findNotificationById(notificationId);
		this.notificationService.delete(notification);
		return "redirect:/admin/notification/";
	}

	// ------------------------------------------------ Appoitment --------------------------------------------
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

	@GetMapping(value = {
		"/pets"
	})
	public String showPetList(final Map<String, Object> model) {
		Iterable<Pet> pets = this.petService.findAllPets();
		model.put("pets", pets);
		return AdminController.PETS_LIST;
	}

	@GetMapping(value = {
		"/pets/{petId}"
	})
	public String showPet(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return AdminController.PETS_SHOW;
	}

}
