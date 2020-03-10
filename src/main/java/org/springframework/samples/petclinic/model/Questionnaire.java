
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Questionnaire extends NamedEntity {

	private Integer	umbral;
	private String	vivienda;
	private String	ingresos;
	private String	horasLibres;
	private String	convivencia;
	private Integer	puntuación;

	//Relaciones

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet		pet;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner	owner;

}
