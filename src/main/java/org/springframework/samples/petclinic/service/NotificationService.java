
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository repository;


	public NotificationService(final NotificationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void save(final Notification notification) {
		this.repository.save(notification);
	}

	@Transactional(readOnly = true)
	public Iterable<Notification> findAll() throws DataAccessException {
		return this.repository.findAll();
	}

	@Transactional(readOnly = true)
	public Iterable<Notification> findAllForOwner() throws DataAccessException {
		return this.repository.findAllForOwner();
	}

	@Transactional(readOnly = true)
	public Notification findNotificationById(final int id) throws DataAccessException {
		return this.repository.findById(id);
	}

	@Transactional
	public void delete(final Notification notification) {
		this.repository.delete(notification);
	}

}
