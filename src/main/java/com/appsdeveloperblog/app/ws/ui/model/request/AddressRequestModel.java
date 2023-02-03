package com.appsdeveloperblog.app.ws.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequestModel {
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
}
