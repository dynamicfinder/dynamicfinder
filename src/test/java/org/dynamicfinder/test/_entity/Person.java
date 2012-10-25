package org.dynamicfinder.test._entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
@Entity
@Table(name="person")
public class Person implements Serializable {

	private static final long serialVersionUID = 693113452428523285L;

	public static enum Gender {
		Male, Female;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="person_id")
	private Long id;

	@Column(name="person_name", nullable=false, length=150)
	private String name;

	@Column(name="birth_date", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column(name="gender", nullable=false, length=1)
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Column(name="hobby", nullable=false, length=50)
	private String hobby;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="person",
			cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Set<Address> addresses;

	public Person() {}

	public Person(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Transient
	public Person addAddress(Address address) {
		if (this.addresses == null)
			this.addresses = new HashSet<Address>();

		address.setPerson(this);
		this.addresses.add(address);

		return this;
	}
}
