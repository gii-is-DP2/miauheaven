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

package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@Column(name = "cause")
	@NotEmpty
	private String		cause;

	@Column(name = "urgent")
	@NotNull
	private Boolean		urgent;

	@Column(name = "vet_id", insertable = false, updatable = false)
	@NotNull
	private int			vet_id;
	@OneToOne(cascade = CascadeType.ALL)
	private Owner		owner;

	@OneToOne(cascade = CascadeType.ALL)
	private Vet			vet;

	@OneToOne(cascade = CascadeType.ALL)
	private Pet			pet;


	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(final String cause) {
		this.cause = cause;
	}

	public Boolean getUrgent() {
		return this.urgent;
	}

	public void setUrgent(final Boolean urgent) {
		this.urgent = urgent;
	}

	public int getVet_id() {
		return this.vet_id;
	}

	public void setVet_id(final int vet_id) {
		this.vet_id = vet_id;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	public Vet getVet() {
		return this.vet;
	}

	public void setVet(final Vet vet) {
		this.vet = vet;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}

}
