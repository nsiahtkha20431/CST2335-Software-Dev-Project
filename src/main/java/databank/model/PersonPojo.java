package databank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.faces.view.ViewScoped;

/**
 *
 * Description: model for the Person object <br>
 * a little read about @ViewScoped <br>
 * https://stackoverflow.com/a/6026009/764951
 */
@ViewScoped
public class PersonPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String lastName;
	private String firstName;
	private int cardNumber;
	private int deptNumber;
	private String deptName;
	private String gender;

	public PersonPojo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getDeptNumber() {
		return deptNumber;
	}

	public void setDeptNumber(int deptNumber) {
		this.deptNumber = deptNumber;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	// Use getter's for member variables
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an
		// object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		return prime * result + Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		/*
		 * enhanced instanceof - yeah! As of JDK 14, no need for additional 'silly'
		 * cast: if (animal instanceof Cat) { Cat cat = (Cat)animal; cat.meow(); //
		 * other class Cat operations ... } Technically, 'otherPersonPojo' is a
		 * <i>pattern</i> that becomes an in-scope variable binding. Note: need to watch
		 * out just-in-case there is already a 'otherPersonPojo' variable in-scope!
		 */
		if (obj instanceof PersonPojo otherPersonPojo) {
			// see comment (above) in hashCode(): compare using only member variables that
			// are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherPersonPojo.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=").append(getId()).append(", ");
		if (getFirstName() != null) {
			builder.append("firstName=").append(getFirstName()).append(", ");
		}
		if (getLastName() != null) {
			builder.append("lastName=").append(getLastName()).append(", ");
		}
		builder.append("cardNumber=").append(getCardNumber()).append(", ");
		builder.append("deptNumber=").append(getDeptNumber()).append(", ");
		if (getDeptName() != null) {
			builder.append("deptName=").append(getDeptName()).append(", ");
		}
		if (getGender() != null) {
			builder.append("gender=").append(getGender()).append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

}