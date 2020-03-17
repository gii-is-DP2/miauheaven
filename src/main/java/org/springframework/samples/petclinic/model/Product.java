
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends NamedEntity {

	private Double	price;

	private Boolean	stock;

	private String	description;

	private String	image;


	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Boolean getStock() {
		return this.stock;
	}

	public void setStock(final Boolean stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

}
