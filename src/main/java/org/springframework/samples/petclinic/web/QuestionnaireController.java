
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Questionnaire;
import org.springframework.samples.petclinic.service.QuestionnaireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

	@Autowired
	QuestionnaireService questService;


	@GetMapping(path = "/new")
	public String crearCuestionario(final ModelMap modelMap) {
		String vista = "questionnaire/editQuestionnaire";
		Questionnaire q = new Questionnaire();
		//QuestionnaireController.rellenaPreguntas(q);
		modelMap.addAttribute("questionnaire", q);
		return vista;
	}

	@PostMapping(path = "/save")
	public String salvaCuestionario(@Valid final Questionnaire cuestionario, final BindingResult result, final ModelMap modelMap) {
		String vista = "pets/adoptionPetList";
		if (result.hasErrors()) {
			modelMap.addAttribute("questionnaire", cuestionario);
			return "questionnaire/editQuestionnaire";
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

	/*
	 * private static void rellenaPreguntas(final Questionnaire q) {
	 * List<String> preguntas = Arrays.asList("¿Dónde vives?", "¿Cómo considerarías tus ingresos mensuales?", "¿Dispones de mucho tiempo a lo largo del día?", "¿Tienes otras mascotas que tengan problemas de convivencia?");
	 * Set<String> def = preguntas.stream().collect(Collectors.toSet());
	 * q.setPreguntas(def);
	 * }
	 */
}
