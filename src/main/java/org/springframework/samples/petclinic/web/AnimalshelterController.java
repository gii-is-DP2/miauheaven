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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Animalshelter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AnimalshelterService;
import org.springframework.samples.petclinic.service.OwnerService;
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
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class AnimalshelterController {

	private final AnimalshelterService	animalshelterService;
	private final OwnerService			ownerService;
	private static final String			VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM	= "animalshelter/createOrUpdateAnimalshelterForm";


	@Autowired
	public AnimalshelterController(final AnimalshelterService clinicService, final OwnerService ownerService) {
		this.animalshelterService = clinicService;
		this.ownerService = ownerService;
	}

	@InitBinder("owner")
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	@GetMapping(value = {
		"/owners/{ownerId}/animalshelter/animalshelterList"
	})
	public String showAnimalshelterList(final Map<String, Object> model) {
		List<Animalshelter> animalshelters = new ArrayList<Animalshelter>();
		animalshelters.addAll(this.animalshelterService.findAnimalshelters());
		model.put("animalshelters", animalshelters);
		return "animalshelter/animalshelterList";
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") final int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}

	@GetMapping(value = "/owners/{ownerId}/animalshelter/new")
	public String initCreationForm(final Owner owner, final ModelMap model) {
		Animalshelter animalshelter = new Animalshelter();
		animalshelter.setOwner(owner);
		model.put("animalshelter", animalshelter);
		return AnimalshelterController.VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/animalshelter/new")
	public String processCreationForm(final Owner owner, @Valid final Animalshelter animalshelter, @PathVariable("ownerId") final int ownerId, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("animalshelter", animalshelter);
			return AnimalshelterController.VIEWS_ANIMAL_CREATE_OR_UPDATE_FORM;
		} else {
			animalshelter.setOwner(owner);
			owner.setId(ownerId);
			this.animalshelterService.saveAnimalshelter(animalshelter, owner);
			return "redirect:/owners/" + owner.getId();
		}
	}

}
