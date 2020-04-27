
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.model.QuestionnaireValidator;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.samples.petclinic.service.exceptions.UmbralInferiorException;
import org.springframework.samples.petclinic.service.exceptions.UnrelatedPetException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("owners/adoptList/questionnaire/")
public class QuestionnaireController {

	private static final String			QUESTIONNAIRE_SHOW	= "questionnaire/questonnaireShow";

	@Autowired
	private final QuestionnaireService	questService;
	private final OwnerService			ownerService;
	private final PetService			petService;


	@Autowired
	public QuestionnaireController(final OwnerService ownerService, final QuestionnaireService questService, final PetService petService) {
		this.ownerService = ownerService;
		this.questService = questService;
		this.petService = petService;
	}

	@GetMapping(path = "/new/{petId}")
	public String crearCuestionario(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		String vista = "questionnaire/createOrUpdateQuestionnaire";
		Questionnaire q = new Questionnaire();
		q.setPet(this.questService.findPetById(petId));
		model.put("questionnaire", q);
		return vista;
	}

	@PostMapping(path = "/new/{petId}")
	public String salvaCuestionario(@Valid final Questionnaire cuestionario, @PathVariable("petId") final int petId, final BindingResult result, final ModelMap modelMap) {
		Pet pet = this.questService.findPetById(petId);
		cuestionario.setPet(pet);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Owner o = this.ownerService.findOwnerByUsername(username);
		cuestionario.setOwner(o);
		cuestionario.setName("Quest-" + o.getFirstName() + " " + o.getLastName());
		Integer puntuacion = QuestionnaireValidator.calculaPuntuacion(cuestionario);
		cuestionario.setPuntuacion(puntuacion);
		cuestionario.setUmbral(QuestionnaireValidator.estableceUmbral());
		if (result.hasErrors()) {
			modelMap.addAttribute("questionnaire", cuestionario);
			return "questionnaire/createOrUpdateQuestionnaire";
		} else {
			this.questService.saveQuest(cuestionario);
			modelMap.addAttribute("message", "Questionnaire successfully saved!");
			return "redirect:/owners/" + o.getId();
		}

	}
	@ModelAttribute("viviendas")
	public Collection<String> viviendas() {
		Collection<String> res = Arrays.asList("Apartamento", "Piso", "Casa");
		return res;
	}

	@ModelAttribute("ingresos")
	public Collection<String> ingresos() {
		Collection<String> res = Arrays.asList("Bajos", "Medios", "Altos");
		return res;
	}

	@ModelAttribute("horasLibres")
	public Collection<String> horasLibres() {
		Collection<String> res = Arrays.asList("Menos de 3 horas", "Entre 3 y 6 horas", "Más de 6 horas");
		return res;
	}

	@ModelAttribute("convivencia")
	public Collection<String> convivencia() {
		Collection<String> res = Arrays.asList("Sí", "No");
		return res;
	}

	@GetMapping(value = "/{petId}")
	public String showAnimalshelterList(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		List<Questionnaire> questionnaire = new ArrayList<Questionnaire>();
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Owner shelter = this.ownerService.findOwnerByUsername(name);
		try {
			questionnaire.addAll(this.questService.findMyQuestionnaireByPetId(shelter.getId(), petId));
		} catch (UnrelatedPetException e) {
			return "redirect:/oups";
		}
		model.put("questionnaire", questionnaire);
		return "questionnaire/questionnaireList";

	}

	@GetMapping(value = "/show/{questId}")
	public String showQuestionnaire(final Map<String, Object> model, @PathVariable("questId") final int questId) {
		Questionnaire questionnaire = this.questService.findQuestionnaireById(questId);
		SecurityContext context = SecurityContextHolder.getContext();
		Boolean isShelter = false;
		for (GrantedAuthority i : context.getAuthentication().getAuthorities()) {
			if (i.getAuthority().equals("animalshelter")) {
				isShelter = true;
			}
		}
		if (isShelter) {
			model.put("questionnaire", questionnaire);

			return QuestionnaireController.QUESTIONNAIRE_SHOW;
		} else {
			return "redirect:/oups";
		}
	}

	@GetMapping(value = "/accept/{questId}")
	public String acceptAdoption(final Map<String, Object> model, @PathVariable("questId") final int questId) {
		Questionnaire questionnaire = this.questService.findQuestionnaireById(questId);
		SecurityContext context = SecurityContextHolder.getContext();
		Boolean isShelter = false;
		for (GrantedAuthority i : context.getAuthentication().getAuthorities()) {
			if (i.getAuthority().equals("animalshelter")) {
				isShelter = true;
			}
		}
		if (isShelter) {
			Pet pet = questionnaire.getPet();
			Owner owner = questionnaire.getOwner();
			owner.addPet(pet);
			try {
				this.ownerService.saveOwnerQuest(owner, questionnaire.getUmbral(), questionnaire.getPuntuacion());
				this.petService.save(pet);

			} catch (UmbralInferiorException e) {
				e.printStackTrace();
			}

			model.put("questionnaire", questionnaire);
			return "redirect:/owners/" + owner.getId();
		} else {
			return "redirect:/oups";
		}
	}
}
