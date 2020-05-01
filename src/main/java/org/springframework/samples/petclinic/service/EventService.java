
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Event;
import org.springframework.samples.petclinic.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

	private final EventRepository eventRepository;


	@Autowired
	public EventService(final EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Event> findEvents() throws DataAccessException {
		return this.eventRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Event findEventById(final int id) throws DataAccessException {
		return this.eventRepository.findById(id);
	}
}
