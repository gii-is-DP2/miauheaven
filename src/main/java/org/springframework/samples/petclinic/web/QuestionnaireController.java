
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
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.security.core.Authentication;
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
@RequestMapping("owners/{ownerId}/questionnaire")
public class QuestionnaireController {

	@Autowired
	QuestionnaireService questService;


	@GetMapping(path = "/new/{petId}")
	public String crearCuestionario(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		String vista = "questionnaire/createOrUpdateQuestionnaire";
		Questionnaire q = new Questionnaire();
		q.setPet(this.questService.findPetById(petId));
		//QuestionnaireController.rellenaPreguntas(q);
		model.put("questionnaire", q);
		return vista;
	}

	@PostMapping(path = "/new/{petId}")
	public String salvaCuestionario(@Valid final Questionnaire cuestionario, @PathVariable("ownerId") final int ownerId, @PathVariable("petId") final int petId, final BindingResult result, final ModelMap modelMap) {
		String vista = "redirect:/owners/{ownerId}";
		Pet pet = this.questService.findPetById(petId);
		cuestionario.setPet(pet);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Owner o = this.questService.findOwnerByUsername(username);
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
			return vista;
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

	@GetMapping(value = "/questionnaireList/{petId}")
	public String showAnimalshelterList(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		List<Questionnaire> questionnaire = new ArrayList<Questionnaire>();
		questionnaire.addAll(this.questService.findQuestionnaireByPetId(petId));
		model.put("questionnaire", questionnaire);
		return "questionnaire/questionnaireList";
	}

	/*
	 * private static void rellenaPreguntas(final Questionnaire q) {
	 * List<String> preguntas = Arrays.asList("¿Dónde vives?", "¿Cómo considerarías tus ingresos mensuales?", "¿Dispones de mucho tiempo a lo largo del día?", "¿Tienes otras mascotas que tengan problemas de convivencia?");
	 * Set<String> def = preguntas.stream().collect(Collectors.toSet());
	 * q.setPreguntas(def);
	 * }
	 */
}
