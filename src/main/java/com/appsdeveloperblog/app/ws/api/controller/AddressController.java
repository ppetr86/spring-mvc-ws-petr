package com.appsdeveloperblog.app.ws.api.controller;

import com.appsdeveloperblog.app.ws.api.model.request.AddressRequestModel;
import com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.api.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.exceptions.AddressServiceException;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.UserService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/addresses")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class AddressController {

    private final UserService userService;
    private AddressService addressService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut createAddress(@RequestBody AddressRequestModel addressDetails) {

        if (addressDetails.getCity().isEmpty() ||
                addressDetails.getCountry().isEmpty() ||
                addressDetails.getStreetName().isEmpty() ||
                addressDetails.getPostalCode().isEmpty()) {
            throw new AddressServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        AddressDtoIn addressDtoIn = modelMapper.map(addressDetails, AddressDtoIn.class);

        var createdAddress = addressService.createAddress(addressDtoIn);

        return createdAddress;

    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteAddress(@PathVariable String addressId) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        addressService.deleteAddressByAddressId(addressId);

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut getAddressById(@PathVariable String id) {

        var returnValue = new AddressDtoOut();
        var addressDto = addressService.findByAddressId(id);
        BeanUtils.copyProperties(addressDto, returnValue);
        return returnValue;
    }

    @GetMapping
    public EnvelopeCollectionOut<AddressDtoOut> getAddresses(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "25", required = false) int limit,
                                                             @RequestParam(defaultValue = "", required = false) String addressId,
                                                             @RequestParam(defaultValue = "", required = false) String city,
                                                             @RequestParam(defaultValue = "", required = false) String country,
                                                             @RequestParam(defaultValue = "", required = false) String streetName,
                                                             @RequestParam(defaultValue = "", required = false) String postalCode) {

        var returnValue = addressService.getAddress(page, limit, addressId, city, country, streetName, postalCode);

        return new EnvelopeCollectionOut<>(returnValue);
    }

    @PutMapping(path = "/{addressId}")
    public AddressDtoOut updateAddress(@PathVariable String addressId, @RequestBody AddressRequestModel AddressDetails) {

        var returnValue = new AddressDtoOut();
        var dto = new AddressDtoIn();
        BeanUtils.copyProperties(AddressDetails, dto);

        var updateAddress = addressService.updateAddressByAddressId(addressId, dto);
        BeanUtils.copyProperties(updateAddress, returnValue);

        return returnValue;
    }


}
