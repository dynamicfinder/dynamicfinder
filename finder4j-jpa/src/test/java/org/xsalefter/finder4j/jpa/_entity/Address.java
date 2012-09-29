package org.xsalefter.finder4j.jpa._entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
@Entity
@Table(name="address")
public class Address implements Serializable {

	private static final long serialVersionUID = -6442985721540634575L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long id;

	@Column(name="street", nullable=false, length=255)
	private String street;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=City.class)
	@JoinColumn(name="city_id", nullable=false)
	private City city;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Person.class)
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Address() {}

	public Address(String street, City city) {
		this.street = street;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
