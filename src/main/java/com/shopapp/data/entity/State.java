package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "states")
@NoArgsConstructor
@Getter
@Setter
public class State  extends IdBasedEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 45)
	private String name;

	//@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

	public State(String name, CountryEntity country) {
		this.name = name;
		this.country = country;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + "]";
	}

}