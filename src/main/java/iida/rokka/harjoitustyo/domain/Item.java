package iida.rokka.harjoitustyo.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Itemid;
	private String name, seller, location, description, email;
	private Double price;

	@ManyToMany(mappedBy = "itemsLiked")
	Set<User> likes;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	@JsonManagedReference
	private Category category;

	// KONSTRUKTORIT

	// Kategorian kanssa
	public Item(String name, String seller, String location, String description, String email, Double price,
			Category category) {
		super();
		this.name = name;
		this.seller = seller;
		this.location = location;
		this.description = description;
		this.email = email;
		this.price = price;
		this.category = category;
	}

	// Ilman kategoriaa
	public Item(String name, String seller, String location, String description, String email, Double price) {
		super();
		this.name = name;
		this.seller = seller;
		this.location = location;
		this.description = description;
		this.email = email;
		this.price = price;
	}

	// Tyhjä
	public Item() {

	}

	// GET SET

	public Long getId() {
		return Itemid;
	}

	public void setId(Long id) {
		this.Itemid = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Item [id=" + Itemid + ", name=" + name + ", seller=" + seller + ", location=" + location
				+ ", description=" + description + ", email=" + email + ", price=" + price + ", category=" + category
				+ "]";
	}

}
