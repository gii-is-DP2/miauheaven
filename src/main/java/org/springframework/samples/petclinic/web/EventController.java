
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EventController {

	private static final String	EVENTS_LIST							= "events/eventsList";
	private static final String	EVENT_SHOW							= "events/eventShow";
	private static final String	VIEWS_EVENT_CREATE_OR_UPDATE_FORM	= "events/createOrUpdateEvent";

	private final EventService	eventService;


	public EventController(final EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping(value = {
		"events"
	})
	public String showEventList(final Map<String, Object> model) {
		Collection<Event> events;
		events = this.eventService.findEvents();
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
			this.eventService.saveEvent(event);
			return "redirect:/events/" + event.getId();
		}
	}

	@GetMapping(value = "/event/{eventId}/edit")
	public String initUpdateEventForm(@PathVariable("eventId") final int eventId, final Model model) {
		final Event ev = this.eventService.findEventById(eventId);
		model.addAttribute(ev);
		return EventController.VIEWS_EVENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/event/{eventId}/edit")
	public String processUpdateEventForm(@Valid final Event event, final BindingResult result, @PathVariable("eventId") final int eventId) {
		if (result.hasErrors())
			return EventController.VIEWS_EVENT_CREATE_OR_UPDATE_FORM;
		else {
			event.setId(eventId);
			this.eventService.saveEvent(event);
			return "redirect:/event/" + event.getId();
		}
	}

	//	@GetMapping("/event/{eventId}")
	//	public ModelAndView showEvent(@PathVariable("eventId") final int eventId) {
	//		final ModelAndView mav = new ModelAndView("event/{eventId}");
	//		mav.addObject(this.eventService.findEventById(eventId));
	//		return mav;
	//	}

}
