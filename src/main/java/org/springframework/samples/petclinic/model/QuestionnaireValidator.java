
package org.springframework.samples.petclinic.model;

public class QuestionnaireValidator {

	public static Integer calculaPuntuacion(final Questionnaire q) {
		Integer res = 0;
		if (q.getIngresos().equals("Altos")) {
			res = res + 2;
		} else if (q.getIngresos().equals("Medios")) {
			res = res + 1;
		}

		if (q.getVivienda().equals("Casa")) {
			res = res + 2;
		} else if (q.getVivienda().equals("Piso")) {
			res = res + 1;
		}

		if (q.getHorasLibres().equals("Más de 6 horas")) {
			res = res + 2;
		} else if (q.getHorasLibres().equals("Entre 3 y 6 horas")) {
			res = res + 1;
		}

		if (q.getConvivencia().equals("Sí")) {
			res = res + 2;
		}

		return res;
	}

	public static Integer estableceUmbral() {
		return 4;
	}
}
