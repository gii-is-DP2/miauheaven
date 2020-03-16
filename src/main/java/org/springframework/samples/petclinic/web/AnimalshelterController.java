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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Notification;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.samples.petclinic.service.AnimalshelterService;
import org.springframework.samples.petclinic.service.NotificationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.samples.petclinic.service.RecordService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class AnimalshelterController {

	private static final String			NOTIFICATION_LIST					= "animalshelter/notification/notificationList";
	private static final String			NOTIFICATION_SHOW					= "animalshelter/notification/notificationShow";
	private static final String			VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM	= "animalshelter/createOrUpdateAnimalshelterForm";
	private static final String			ANIMAL_SHELTER_SHOW					= "animalshelter/animalshelter/animalshelterShow";

	private final AnimalshelterService	animalshelterService;

	private final NotificationService	notificationService;

	private final OwnerService			ownerService;
	private final RecordService			recordService;
	private final QuestionnaireService	questService;


	@Autowired
	public AnimalshelterController(final AnimalshelterService clinicService, final NotificationService notificationService, final QuestionnaireService questService, final OwnerService ownerService, final RecordService recordService) {
		this.animalshelterService = clinicService;
		this.notificationService = notificationService;
		this.recordService = recordService;
		this.questService = questService;
		this.ownerService = ownerService;

	}
	@GetMapping(value = "/animalshelter")
	public String showAnimalshelterList(final Map<String, Object> model) {
		List<Animalshelter> animalshelters = new ArrayList<Animalshelter>();
		animalshelters.addAll(this.animalshelterService.findAnimalshelters());
		model.put("animalshelters", animalshelters);
		return "animalshelter/animalshelterList";
	}

	@GetMapping(value = "/animalshelter/new")

	/*
	 * @ModelAttribute("/owner")
	 * public Owner findOwner(@PathVariable("ownerId") final int ownerId) {
	 * return this.ownerService.findOwnerById(ownerId);
	 * }
	 */

	public String initCreationForm(final Owner owner, final ModelMap model) {
		Animalshelter animalshelter = new Animalshelter();
		animalshelter.setOwner(owner);
		model.put("animalshelter", animalshelter);
		return AnimalshelterController.VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/animalshelter/new")
	public String processCreationForm(@Valid final Animalshelter animalshelter, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("animalshelter", animalshelter);
			return AnimalshelterController.VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM;
		} else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			Owner o = this.animalshelterService.findOwnerByUsername(username);
			animalshelter.setOwner(o);
			this.animalshelterService.saveAnimalshelter(animalshelter, o);
			return "redirect:/animalshelter";
		}
	}

	@GetMapping("/owners/myAnimalShelter")
	public String findMyAnimalShelter(final Map<String, Object> model) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Owner owner = this.animalshelterService.findOwnerByUsername(name);
		model.put("owner", owner);
		return AnimalshelterController.ANIMAL_SHELTER_SHOW;
	}

	// ------------------------------------------------ Records ------------------------------------------

	@ModelAttribute("quests")
	public List<Questionnaire> findQuests() {
		return this.questService.findAll();
	}
	@ModelAttribute("owners")
	public List<Owner> findOwners() {
		List<Questionnaire> quests = this.questService.findAll();
		List<Owner> res = new ArrayList<>();
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Record> records = this.recordService.findAllByUsename(name);

		for (Questionnaire quest : quests) {
			res.add(quest.getOwner());
		}
		for (Owner record : records.stream().map(x -> x.getOwner()).collect(Collectors.toList())) {
			res.remove(record);
		}
		return res;
	}

	@GetMapping(value = "/owners/myAnimalShelter/records")
	public String showRecodsList(final Map<String, Object> model) {
		List<Record> records = new ArrayList<>();
		records.addAll(this.recordService.findAll());
		model.put("records", records);
		return "records/recordList";
	}

	@GetMapping(value = "/owners/myAnimalShelter/records/new")
	public String initRecordForm(final Map<String, Object> model) {
		Record record = new Record();
		model.put("record", record);
		return "records/createOrUpdateRecordForm";
	}

	@PostMapping(value = "/owners/myAnimalShelter/records/new")
	public String processRecordForm(@Valid final Record record, final BindingResult result) {
		if (result.hasErrors()) {
			return "records/createOrUpdateRecordForm";
		} else {
			Owner ow = this.ownerService.findOwnerById(record.getOwner_id());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			Owner animalshelter = this.animalshelterService.findOwnerByUsername(username);
			record.setOwner(ow);
			record.setAnimalshelter(animalshelter);
			this.recordService.saveRecord(record);

			return "redirect:/owners/myAnimalShelter/records";
		}
	}

	// ------------------------------------------------ Notification --------------------------------------------

	@GetMapping("/animalshelter/notification/")
	public String notificationList(final Map<String, Object> model) {
		Iterable<Notification> notifications = this.notificationService.findAllForAnimalShelters();
		model.put("notifications", notifications);
		return AnimalshelterController.NOTIFICATION_LIST;
	}

	@GetMapping("/animalshelter/notification/{notificationId}")
	public String notificationShow(final Map<String, Object> model, @PathVariable final int notificationId) {
		Notification notification = this.notificationService.findNotificationById(notificationId);
		if (notification.getTarget().equals("animal_shelter")) {
			model.put("notification", notification);
			return AnimalshelterController.NOTIFICATION_SHOW;
		}
		return "redirect:/oups";
	}

}
