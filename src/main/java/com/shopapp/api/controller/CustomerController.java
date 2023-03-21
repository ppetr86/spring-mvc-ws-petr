package com.shopapp.api.controller;

import com.shopapp.api.converter.AddressExportConverter;
import com.shopapp.api.converter.CustomerExportExportConverter;
import com.shopapp.api.model.response.OperationStatusModel;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.service.AddressDao;
import com.shopapp.service.CustomerDao;
import com.shopapp.service.specification.GenericSpecification;
import com.shopapp.service.specification.GenericSpecificationsBuilder;
import com.shopapp.shared.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class CustomerController {

    private final CustomerDao customerDao;
    private AddressDao addressDao;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CustomerDtoOut createCustomer(@RequestBody @Valid CustomerDtoIn customerDetails) {

        var modelMapper = new ModelMapper();
        CustomerDtoIn customerDtoIn = modelMapper.map(customerDetails, CustomerDtoIn.class);

        var createdCustomer = customerDao.createCustomer(customerDtoIn);

        return modelMapper.map(createdCustomer, CustomerDtoOut.class);

    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteCustomer(@PathVariable String id) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        customerDao.deleteCustomer(UUID.fromString(id));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CustomerDtoOut getCustomer(@PathVariable String id) {

        var returnValue = new CustomerDtoOut();
        var customerDto = customerDao.loadById(UUID.fromString(id));
        BeanUtils.copyProperties(customerDto, returnValue);
        return returnValue;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDtoOut>> getCustomers(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "25") int limit) {
        List<CustomerEntity> customer = customerDao.getCustomers(page, limit);

        var result = new CustomerExportExportConverter().convertToListDtoOut(customer);

        return ResponseEntity.ok(result);
    }

    /*
     * http://localhost:8080/api/v1/customer/password-reset-request
     * */
    @PostMapping(path = "/password-reset-request",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel requestReset(@RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = customerDao.requestPasswordReset(passwordResetRequestDto.getEmail());

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    @PostMapping(path = "/password-reset",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = customerDao.resetPassword(
                passwordResetDto.getToken(),
                passwordResetDto.getPassword());

        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public CustomerDtoOut updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerDtoIn customerDetails) {

        var returnValue = new CustomerDtoOut();
        var dto = new CustomerDtoIn();
        BeanUtils.copyProperties(customerDetails, dto);

        var updateCustomer = customerDao.updateCustomer(UUID.fromString(id), dto);
        BeanUtils.copyProperties(updateCustomer, returnValue);

        return returnValue;
    }

    /*
     * http://localhost:8080/api/v1/customer/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = customerDao.verifyEmailToken(token);

        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

    @DeleteMapping(path = "{customerId}/address/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteAddress(@PathVariable String addressId, @PathVariable String customerId) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());


        var specBuilder = new GenericSpecificationsBuilder<AddressEntity>();
        specBuilder.with("customer", GenericSpecification.SearchOperation.EQUALITY, List.of(customerId));
        addressDao.findOneBy(specBuilder.build())
                .ifPresent(addressEntity -> addressDao.delete(addressEntity.getId()));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{customerId}/address", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut getCustomerAddress(@PathVariable String customerId) {

        return new AddressExportConverter().convertToDtoOut(customerDao.loadById(UUID.fromString(customerId)).getAddress());
    }

    @PutMapping(path = "/{customerId}/address")
    public AddressDtoOut updateCustomerAddress(@PathVariable String customerId, @RequestBody @Valid AddressDtoIn dtoIn) {

        /*return Optional.ofNullable(customerDao.loadById(UUID.fromString(customerId)))
                .map(customerEntity -> {
                    customerEntity.setAddress(new AddressEntity(dtoIn));
                    return customerEntity;
                })
                .map(customerDao::save)
                .map(CustomerEntity::getAddress)
                .map(address -> {
                    var returnValue = new AddressDtoOut();
                    BeanUtils.copyProperties(address, returnValue);
                    return returnValue;
                })
                .orElseGet(AddressDtoOut::new);*/
        //TODO: create import converters first
        return new AddressDtoOut();
    }

}
