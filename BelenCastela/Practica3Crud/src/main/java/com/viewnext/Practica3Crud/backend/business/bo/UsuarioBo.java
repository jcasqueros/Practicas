package com.viewnext.Practica3Crud.backend.business.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioBo {

	String mDni;
	String mName;
	String mSurname;
	int mAge;
}
