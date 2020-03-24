
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "product")
public class Product extends NamedEntity {

	@Min(0)
	@NotNull
	private Double	price;

	@NotNull
	private Boolean	stock;

	private String	description;

	@URL
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
