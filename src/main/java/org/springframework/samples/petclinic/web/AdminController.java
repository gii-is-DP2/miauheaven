
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final String			APPOINTMENT_LIST	= "admin/appointment/appointmentsList";
	private static final String			APPOINTMENT_SHOW	= "admin/appointment/appointmentsShow";
	private static final String			PETS_LIST			= "admin/pet/petList";
	private static final String			PETS_SHOW			= "admin/pet/petShow";
	private static final String			NOTIFICATION_CREATE	= "admin/notification/notificationCreate";
	private static final String			NOTIFICATION_LIST	= "admin/notification/notificationList";
	private static final String			NOTIFICATION_SHOW	= "admin/notification/notificationShow";
	private static final String			QUESTIONNAIRE_LIST	= "admin/questionnaires/questionnaireList";
	private static final String			QUESTIONNAIRE_SHOW	= "admin/questionnaires/questionnaireShow";
	private static final String			PRODUCT_LIST		= "admin/product/productList";
	private static final String			PRODUCT_SHOW		= "admin/product/productShow";
	private static final String			PRODUCT_FORM		= "admin/product/productForm";
	private static final String 		EVENT_LIST			= "admin/events/eventsList";
 	private static final String			EVENT_SHOW			= "admin/events/eventShow";
 	
	private final AppointmentService	appointmentService;
	private final PetService			petService;
	private final NotificationService	notificationService;
	private final QuestionnaireService	questionnaireService;
	private final EventService			eventService;
	private final ProductService		productService;


	@Autowired
	public AdminController(final AppointmentService appointmentService, final ProductService productService, final PetService petService, final NotificationService notificationService, final QuestionnaireService questionnaireService, final EventService eventService) {
		this.appointmentService = appointmentService;
		this.petService = petService;
		this.notificationService = notificationService;
		this.questionnaireService = questionnaireService;
		this.productService = productService;
		this.eventService = eventService;
		
	}
	// ------------------------------------------------ Event ------------------------------------------
 	@GetMapping(value = {
 			"/events"
 		})
 		public String showEventList(final Map<String, Object> model) {
 			Collection<Event> events;
 			events = this.eventService.findEvents();
 			model.put("events", events);
 			return AdminController.EVENT_LIST;
 		}

 		@GetMapping(value = {
 			"/events/{eventId}"
 		})
 		public String showEvent(final Map<String, Object> model, @PathVariable("eventId") final int eventId) {
 			Event event = this.eventService.findEventById(eventId);
 			model.put("event", event);
 			return AdminController.EVENT_SHOW;
 		}
	
	// ------------------------------------------------ Notification ------------------------------------------

	@GetMapping(value = "/notification/")
	public String notificationsList(final Map<String, Object> model) {
		Iterable<Notification> notifications = this.notificationService.findAll();
		model.put("notifications", notifications);
		return AdminController.NOTIFICATION_LIST;
	}

	@GetMapping(value = "/notification/{notificationId}")
	public String notificationsShow(final Map<String, Object> model, @PathVariable final int notificationId) {
		Notification notification = this.notificationService.findNotificationById(notificationId);
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

			notification.setDate(LocalDateTime.now());
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
	@GetMapping(value = "/appointments")
	public String appointmentList(final Map<String, Object> model) {
		Iterable<Appointment> appointments = this.appointmentService.findAll();
		model.put("appointments", appointments);
		return AdminController.APPOINTMENT_LIST;
	}

	@GetMapping(value = "/appointments/{appointmentId}")
	public String appointmentShow(final Map<String, Object> model, @PathVariable("appointmentId") final int appointmentId) {
		Appointment appointment = this.appointmentService.findOneById(appointmentId);
		model.put("appointment", appointment);
		return AdminController.APPOINTMENT_SHOW;

	}
	// ------------------------------------------------ Questionnaire --------------------------------------------
	@GetMapping(value = "/questionnaires")
	public String questionnaireList(final Map<String, Object> model) {
		Iterable<Questionnaire> questionnaires = this.questionnaireService.findAll();
		model.put("questionnaires", questionnaires);
		return AdminController.QUESTIONNAIRE_LIST;
	}

	@GetMapping(value = "/questionnaires/{questionnaireId}")
	public String questionnaireShow(final Map<String, Object> model, @PathVariable("questionnaireId") final int questionnaireId) {
		Questionnaire questionnaire = this.questionnaireService.findOneById(questionnaireId);
		model.put("questionnaire", questionnaire);
		return AdminController.QUESTIONNAIRE_SHOW;

	}

	// ------------------------------------------------ Product ---------------------------------------------------

	@GetMapping(value = "/product")
	public String productList(final Map<String, Object> model) {
		Collection<Product> products = this.productService.findAll();
		model.put("products", products);
		return AdminController.PRODUCT_LIST;
	}

	@GetMapping(value = "/product/{productId}")
	public String productShow(final Map<String, Object> model, @PathVariable("productId") final int productId) {
		Product product = this.productService.findProductById(productId);
		model.put("product", product);
		return AdminController.PRODUCT_SHOW;
	}

	@GetMapping(value = "/product/create")
	public String productCreate(final Map<String, Object> model) {
		Product product = new Product();
		List<Boolean> stock = new ArrayList<>();
		stock.add(true);
		stock.add(false);
		model.put("stock", stock);
		model.put("product", product);
		return AdminController.PRODUCT_FORM;
	}

	@GetMapping(value = "/product/update/{productId}")
	public String productUpdate(final Map<String, Object> model, @PathVariable("productId") final int productId) {
		Product product = this.productService.findProductById(productId);
		List<Boolean> stock = new ArrayList<>();
		stock.add(true);
		stock.add(false);
		model.put("stock", stock);
		model.put("product", product);
		return AdminController.PRODUCT_FORM;
	}

	@PostMapping(value = "/product/save")
	public String productSave(final Map<String, Object> model, @Valid final Product product, final BindingResult result) {
		if (result.hasErrors()) {
			List<Boolean> stock = new ArrayList<>();
			stock.add(true);
			stock.add(false);
			model.put("stock", stock);
			model.put("product", product);
			return AdminController.PRODUCT_FORM;
		} else {
			this.productService.save(product);
			return "redirect:/admin/product/";
		}
	}

	@GetMapping(value = "/product/delete/{productId}")
	public String productDelete(final Map<String, Object> model, @PathVariable("productId") final int productId) {
		Product product = this.productService.findProductById(productId);
		this.productService.delete(product);
		return "redirect:/admin/product/";
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
		if (!pet.getName().isEmpty()) {
			model.put("pet", pet);
			return AdminController.PETS_SHOW;
		}
		return "redirect:/oups";
	}

}
