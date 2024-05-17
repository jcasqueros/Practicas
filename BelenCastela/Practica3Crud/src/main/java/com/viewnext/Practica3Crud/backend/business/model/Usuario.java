package com.viewnext.Practica3Crud.backend.business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="mUser")
public class Usuario {

	@Id
	String mDni;
	String mName;
	String mSurname;
	int mAge;
}
