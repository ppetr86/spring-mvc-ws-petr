package com.appsdeveloperblog.app.ws.api.controller;

import com.appsdeveloperblog.app.ws.shared.dto.PasswordResetDto;
import com.appsdeveloperblog.app.ws.shared.dto.PasswordResetRequestDto;
import com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.api.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class UserController {

    private final UserService userService;
    private AddressService addressService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut createUser(@RequestBody UserDtoIn userDetails) {

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        var modelMapper = new ModelMapper();
        UserDtoIn userDtoIn = modelMapper.map(userDetails, UserDtoIn.class);

        var createdUser = userService.createUser(userDtoIn);

        return modelMapper.map(createdUser, UserDtoOut.class);

    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(UUID.fromString(id));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut getUser(@PathVariable String id) {

        var returnValue = new UserDtoOut();
        var userDto = userService.loadById(UUID.fromString(id));
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public EntityModel<AddressDtoOut> getUserAddress(@PathVariable String userId,
                                                     @PathVariable String addressId) {

        var addressesDto = addressService.loadById(UUID.fromString(addressId));

        ModelMapper modelMapper = new ModelMapper();

        // http://localhost:8080/users/<userId>/addresses/{addressId}
        var userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");

        var userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserAddresses(userId)).withRel("addresses");

        var selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserAddress(userId, addressId))
                .withSelfRel();

        var returnValue = modelMapper.map(addressesDto, AddressDtoOut.class);

        return EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink));
    }

    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public CollectionModel<AddressDtoOut> getUserAddresses(@PathVariable String id) {

        var returnValue = new ArrayList<AddressDtoOut>();
        var user = userService.loadById(UUID.fromString(id));
        var usersAddresses = user.getAddresses();

        if (usersAddresses != null && !usersAddresses.isEmpty()) {
            Type listType = new TypeToken<List<AddressDtoOut>>() {
            }.getType();
            returnValue = new ModelMapper().map(usersAddresses, listType);

            for (var addressRest : returnValue) {
                Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserAddress(id, addressRest.getId())).withSelfRel();

                addressRest.add(selfLink);
            }
        }

        var userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        var selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserAddresses(id)).withSelfRel();

        return CollectionModel.of(returnValue, userLink, selfLink);
    }

    @GetMapping
    public List<UserDtoOut> getUsers(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "25") int limit) {
        List<UserDtoOut> returnValue = new ArrayList<>();

        List<UserEntity> users = userService.getUsers(page, limit);

        var mapper = new ModelMapper();
        users.forEach(each -> {
            returnValue.add(mapper.map(each, UserDtoOut.class));
        });

        return returnValue;
    }

    /*
     * http://localhost:8080/mobile-app-ws/users/password-reset-request
     * */
    @PostMapping(path = "/password-reset-request",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.requestPasswordReset(passwordResetRequestDto.getEmail());

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    @PostMapping(path = "/password-reset",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.resetPassword(
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
    public UserDtoOut updateUser(@PathVariable String id, @RequestBody UserDtoIn userDetails) {

        var returnValue = new UserDtoOut();
        var dto = new UserDtoIn();
        BeanUtils.copyProperties(userDetails, dto);

        var updateUser = userService.updateUser(UUID.fromString(id), dto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    /*
     * http://localhost:8080/mobile-app-ws/users/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userService.verifyEmailToken(token);

        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

}
