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

package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class VetServiceTests {

	@Autowired
	private PetService				petService;

	@Autowired
	protected VetService			vetService;

	@Autowired
	protected AppointmentService	appointmentService;


	@Test
	public void findVetByUsername() {
		Vet vet = this.vetService.findVetByUsername("vet1");
		Assertions.assertThat(vet.getFirstName()).isNotNull();
		Assertions.assertThat(vet.getLastName()).isNotNull();

	}

	@Test
	void shouldFindVets() {
		Collection<Vet> vets = this.vetService.findVets();

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		Assertions.assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}

	//HU.17
	//Negative Case
	/* With this Test you can see that if you obtain all appointments, you get old appointments */
	@Test
	void shouldFindAllMyAppointments() {
		Collection<Vet> vets = this.vetService.findVets();
		Vet vet = EntityUtils.getById(vets, Vet.class, 1);
		Iterable<Appointment> app = this.appointmentService.findAll();
		Iterator<Appointment> list = app.iterator();
		while (list.hasNext()) {
			Appointment date = list.next();
			if (date.getDate().isAfter(LocalDate.now())) {
				Assertions.assertThat(date.getDate().isAfter(LocalDate.now())).isEqualTo(true);
			} else {
				Assertions.assertThat(date.getDate().isAfter(LocalDate.now())).isEqualTo(false);
			}
		}
	}
	//Positive Case
	/* With this Test you can see that if you obtain your next appointments, so all must be in the future */
	@Test
	void shouldFindMyNextAppointments() {
		Collection<Vet> vets = this.vetService.findVets();
		Vet vet = EntityUtils.getById(vets, Vet.class, 1);
		List<Appointment> appointments = (List<Appointment>) this.appointmentService.findAllByVet(vet.getId());

		for (Appointment apponint : appointments) {
			if (apponint.getVet_id() == vet.getId()) {
				Assertions.assertThat(apponint.getDate().isAfter(LocalDate.now())).isEqualTo(true);
			}
		}
	}
	// ---------------------------------------------------------------- HU.18 ----------------------------------------------------------------------------------------------------

	@Test //-

	void testNotSeePet() throws Exception {
		Collection<Pet> pets = (Collection<Pet>) this.petService.findAllPets();
		Assertions.assertThat(this.petService.findPetById(pets.size() + 1)).isNull();

	}

}
