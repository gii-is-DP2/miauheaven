
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AnimalshelterService;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {

	private static final String			EVENTS_LIST							= "events/eventsList";
	private static final String			EVENT_SHOW							= "events/eventShow";
	private static final String			VIEWS_EVENT_CREATE_OR_UPDATE_FORM	= "events/createOrUpdateEvent";

	private final EventService			eventService;
	private final AnimalshelterService	asService;


	public EventController(final EventService eventService, final AnimalshelterService asService) {
		this.eventService = eventService;
		this.asService = asService;
	}

	@GetMapping(value = {
		"events"
	})
	public String showEventList(final Map<String, Object> model) {
		Collection<Event> events;
		events = this.eventService.findEvents();
		events = events.stream().filter(x -> x.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
		model.put("events", events);
		return EventController.EVENTS_LIST;
	}

	@GetMapping(value = {
		"events/{eventId}"
	})
	public String showEvent(final Map<String, Object> model, @PathVariable("eventId") final int eventId) {
		final Event event = this.eventService.findEventById(eventId);
		model.put("event", event);
		return EventController.EVENT_SHOW;
	}

	@GetMapping(path = "events/new")
	public String initCreationForm(final Map<String, Object> model) {
		final String vista = "events/createOrUpdateEvent";
		final Event event = new Event();
		model.put("event", event);
		return vista;
	}

	@PostMapping(path = "events/new")
	public String processCreationForm(@Valid final Event event, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("event", event);
			return "events/createOrUpdateEvent";
		} else {
			final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			final String username = auth.getName();
			final Owner o = this.asService.findOwnerByUsername(username);
			final Animalshelter as = this.asService.findAnimalshelterByOwnerId(o.getId());
			event.setAnimalshelter(as);
			this.eventService.saveEvent(event);
			return "redirect:/events/" + event.getId();
		}
	}

	@GetMapping(value = "/events/{eventId}/edit")
	public String initUpdateEventForm(@PathVariable("eventId") final int eventId, final ModelMap model) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String username = auth.getName();
		if (!username.contains("shelter")) {
			return "redirect:/oups";
		} else {
			final Event ev = this.eventService.findEventById(eventId);
			model.put("event", ev);
			return EventController.VIEWS_EVENT_CREATE_OR_UPDATE_FORM;
		}
	}

	@PostMapping(value = "/events/{eventId}/edit")
	public String processUpdateEventForm(@Valid final Event event, final BindingResult result, @PathVariable("eventId") final int eventId) {
		if (result.hasErrors()) {
			return EventController.VIEWS_EVENT_CREATE_OR_UPDATE_FORM;
		} else {
			event.setId(eventId);
			this.eventService.saveEvent(event);
			return "redirect:/events/" + event.getId();
		}
	}

}
