package iida.rokka.harjoitustyo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Likeid;
	private Long ItemID;
	private String user;

	// KONSTRUKTORIT
	// String koska otan vain usernamen

	public ItemUser() {

	}

	public ItemUser(String user) {
		super();
		this.user = user;
	}

	// GET SET

	public Long getLikeid() {
		return Likeid;
	}

	public void setLikeid(Long likeid) {
		Likeid = likeid;
	}

	public Long getItemID() {
		return ItemID;
	}

	public void setItemID(Long itemID) {
		ItemID = itemID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	// TO STRING

	@Override
	public String toString() {
		return "ItemUser [Likeid=" + Likeid + ", ItemID=" + ItemID + ", user=" + user + "]";
	}

}
