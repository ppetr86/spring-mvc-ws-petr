package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@Getter
@Setter
public class CountryEntity extends IdBasedEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 45)
	private String name;

	@Column(nullable = false, length = 5)
	private String code;

	//@JsonBackReference
	@OneToMany(mappedBy = "country")
	private Set<State> states;

	public CountryEntity(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public CountryEntity(UUID id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	public CountryEntity(String name) {
		this.name = name;
	}
	
	public CountryEntity(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", code=" + code + "]";
	}

}
