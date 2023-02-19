package com.appsdeveloperblog.app.ws.api.controller;

import com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.api.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.exceptions.AddressServiceException;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import com.appsdeveloperblog.app.ws.shared.dto.EnvelopeCollectionOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class AddressController {

    private AddressService addressService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut createAddress(@RequestBody AddressDtoIn addressDetails) {

        if (addressDetails.getCity().isEmpty() ||
                addressDetails.getCountry().isEmpty() ||
                addressDetails.getStreet().isEmpty() ||
                addressDetails.getPostalCode().isEmpty()) {
            throw new AddressServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        var createdAddress = addressService.createAddress(addressDetails);
        var modelMapper = new ModelMapper();

        return modelMapper.map(createdAddress, AddressDtoOut.class);

    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteAddress(@PathVariable String id) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        addressService.delete(UUID.fromString(id));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut getAddressById(@PathVariable String id) {

        var returnValue = new AddressDtoOut();
        var addressDto = addressService.loadById(UUID.fromString(id));
        BeanUtils.copyProperties(addressDto, returnValue);
        return returnValue;
    }

    @GetMapping
    public EnvelopeCollectionOut<AddressEntity> getAddresses(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "25", required = false) int limit,
                                                             @RequestParam(defaultValue = "", required = false) String city,
                                                             @RequestParam(defaultValue = "", required = false) String country,
                                                             @RequestParam(defaultValue = "", required = false) String streetName,
                                                             @RequestParam(defaultValue = "", required = false) String postalCode) {

        var returnValue = addressService.getAddresses(page, limit, city, country, streetName, postalCode);

        return new EnvelopeCollectionOut<>(returnValue);
    }

    @PutMapping(path = "/{id}")
    public AddressDtoOut updateAddress(@PathVariable String id, @RequestBody AddressDtoIn AddressDetails) {

        var returnValue = new AddressDtoOut();
        var dto = new AddressDtoIn();
        BeanUtils.copyProperties(AddressDetails, dto);

        var updateAddress = addressService.updateAddressByAddressId(UUID.fromString(id), dto);
        BeanUtils.copyProperties(updateAddress, returnValue);

        return returnValue;
    }


}
