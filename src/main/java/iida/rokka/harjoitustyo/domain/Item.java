package iida.rokka.harjoitustyo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String item, seller, location, description, email;
	private Double price;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	@JsonManagedReference
	private Category category;

	// KONSTRUKTORIT

	public Item(String item, String seller, String location, String description, String email, Double price,
			Category category) {
		super();
		this.item = item;
		this.seller = seller;
		this.location = location;
		this.description = description;
		this.email = email;
		this.price = price;
		this.category = category;
	}

	public Item(String item, String seller, String location, String description, String email, Double price) {
		super();
		this.item = item;
		this.seller = seller;
		this.location = location;
		this.description = description;
		this.email = email;
		this.price = price;
	}

	public Item() {

	}

	// GET SET

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	// TO STRING

	@Override
	public String toString() {
		return "Item [id=" + id + ", item=" + item + ", seller=" + seller + ", location=" + location + ", description="
				+ description + ", email=" + email + ", price=" + price + ", category=" + category + "]";
	}

}
