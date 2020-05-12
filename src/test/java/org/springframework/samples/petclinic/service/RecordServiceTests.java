
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Record;
import org.springframework.stereotype.Service;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RecordServiceTests {

	@Autowired
	private OwnerService	ownerService;

	@Autowired
	private RecordService	recordService;


	@Test
	public void findAllByUsename() {
		Collection<Record> records = this.recordService.findAllByUsename("owner1");
		for (Record r : records) {
			Assertions.assertThat(r.getOwner_id()).isNotNull();
		}
	}

	// ---------------------------------------------------------------- HU.24 ----------------------------------------------------------------------------------------------------

	@Test //+
	public void animalShelterShouldCreateRecord() {
		//Crea el record asociado a un usuario con normalidad
		Owner owner = this.ownerService.findOwnerById(1);
		Owner animalshelter = this.ownerService.findOwnerById(11);
		Record record = new Record();
		record.setAnimalshelter(animalshelter);
		record.setOwner(owner);
		record.setOwner_id(owner.getId());
		this.recordService.saveRecord(record);
		for (Record rec : this.recordService.findAllByUsename(owner.getUser().getUsername())) {
			if (rec.getAnimalshelter().equals(record.getAnimalshelter())) {
				Assertions.assertThat(rec).isEqualTo(record);
			}
		}
	}

	@Test //-
	public void animalShelterShouldNotCreateRecord() throws NullPointerException {
		Owner owner = this.ownerService.findOwnerByUsername("Etereo"); //No existe
		Owner animalshelter = this.ownerService.findOwnerById(11);
		Record record = new Record();
		//Al no existir el owner, surge una NullPointerException
		assertThrows(NullPointerException.class, () -> {
			record.setAnimalshelter(animalshelter);
			record.setOwner(owner);
			record.setOwner_id(owner.getId());
			this.recordService.saveRecord(record);

		});

	}
}
