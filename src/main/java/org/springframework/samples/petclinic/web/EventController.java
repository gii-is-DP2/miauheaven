
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EventController {

	private static final String	EVENTS_LIST	= "events/eventsList";
	private static final String	EVENT_SHOW	= "events/eventShow";

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
		return EVENTS_LIST;
	}

	@GetMapping(value = {
		"events/{eventId}"
	})
	public String showEvent(final Map<String, Object> model, @PathVariable("eventId") final int eventId) {
		Event event = this.eventService.findEventById(eventId);
		model.put("event", event);
		return EVENT_SHOW;
	}

}
