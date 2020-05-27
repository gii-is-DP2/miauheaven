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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.exceptions.PetNotRegistredException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class AppointmentController {

	//private final PetService	petService;
	private final AppointmentService	appointmentService;
	private final VetService			vetService;
	private final OwnerService			ownerService;
	private final PetService			petService;


	@Autowired
	public AppointmentController(final AppointmentService appointmentService, final OwnerService ownerService, final PetService petService, final VetService vetService) {
		this.appointmentService = appointmentService;
		this.ownerService = ownerService;
		this.petService = petService;
		this.vetService = vetService;

	}
	@InitBinder("owner")
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// Spring MVC calls method loadPetWithAppointment(...) before initNewAppointmentForm is called
	@ModelAttribute("vets")
	public Vets loadVets(@PathVariable("petId") final int petId) {
		final Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@GetMapping(value = "/owners/*/pets/{petId}/appointment/new")
	public String initNewAppointmentForm(@PathVariable("petId") final int petId, final ModelMap model) {
		final Appointment appointment = new Appointment();
		model.put("appointment", appointment);
		model.put("vetid", 1);
		return "appointment/createOrUpdateAppointmentForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/appointment/new")
	public String processNewAppointmentForm(@Valid final Appointment appointment, final BindingResult result, @PathVariable("petId") final int petId, @PathVariable("ownerId") final int ownerId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("appointment", appointment);
			if (appointment.getUrgent() == null) {
				result.rejectValue("date", "not defined", "Caracter de la cita no definido");
			}
			return "appointment/createOrUpdateAppointmentForm";
		} else {
			final Owner own = this.ownerService.findOwnerById(ownerId);
			final Pet pet = this.petService.findPetById(petId);
			appointment.setOwner(own);
			final Vet v = this.vetService.findVetById(appointment.getVet_id());
			appointment.setVet(v);
			appointment.setPet(pet);
			try {
				this.appointmentService.saveAppointment(appointment);
			} catch (PetNotRegistredException e) {
				result.rejectValue("pet", "not registred", "Mascota no registrada");
				e.printStackTrace();
			}
			return "redirect:/owners/{ownerId}";
		}
	}

}
