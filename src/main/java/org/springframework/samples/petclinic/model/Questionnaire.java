
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
@Setter
@Table(name = "questionnaires")
public class Questionnaire extends NamedEntity {

	private Integer	umbral;

	@NotBlank
	private String	vivienda;

	@NotBlank
	private String	ingresos;

	@NotBlank
	private String	horasLibres;

	@NotBlank
	private String	convivencia;

	private Integer	puntuacion;

	//Relaciones

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet		pet;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner	owner;

}
