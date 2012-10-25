package org.dynamicfinder.test._entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 4603141419727671571L;
	
	public static enum Gender {
		M, F;

		private String label;
		public final String getLabel() {
			switch (this) {
			case M: this.label = "Male";
			case F: this.label = "Female";
			default: this.label = "Unknown";
			}
			return this.label;
		}
	}

	@Id @Column(name="emp_no")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date", nullable=false)
	private Date birthDate;

	@Column(name="first_name", nullable=false, length=14)
	private String firstName;

	@Column(name="last_name", nullable=false, length=16)
	private String lastName;

	@Enumerated(EnumType.STRING)
	@Column(name="gender", nullable=false)
	private Gender gender;

	@Column(name="hire_date", nullable=false)
	private Date hireDate;

	public Employee() {}

	public Employee(Integer id, String firstName) {
		this.id = id;
		this.firstName = firstName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
}
