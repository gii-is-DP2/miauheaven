
package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

	@NotEmpty
	private String			title;

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDateTime	date;

	@NotEmpty
	private String			message;

	@Pattern(regexp = "owner|veterinarian|animal_shelter")
	private String			target;

	@URL
	private String			url;


	public String getMessage() {
		return this.message;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getTarget() {
		return this.target;
	}

	public String setTarget(final String target) {
		return this.target = target;
	}

}
