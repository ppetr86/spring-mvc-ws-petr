package com.shopapp.api.controller;

import com.shopapp.api.converter.UserExportConverter;
import com.shopapp.api.model.response.OperationStatusModel;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.service.UserDao;
import com.shopapp.shared.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
@Tag(
        name = "Read REST APIs for UserEntity",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
public class UserController {

    private final UserDao userDao;

    @Operation(summary = "Create UserEntity REST API")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut createUser(@RequestBody @Valid UserDtoIn userDtoIn) {

        var modelMapper = new ModelMapper();
        var createdUser = userDao.createUser(userDtoIn);

        return modelMapper.map(createdUser, UserDtoOut.class);

    }

    @Operation(summary = "Delete UserEntity REST API")
    @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO_CONTENT")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        userDao.deleteUser(UUID.fromString(id));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    @Operation(summary = "Get UserEntity REST API")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut getUser(@PathVariable String id) {

        var userEntity = userDao.loadById(UUID.fromString(id));
        return new UserExportConverter().convertToDtoOut(userEntity);
    }

    @Operation(summary = "Get AddressEntity from UserEntity REST API")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressDtoOut getUserAddress(@PathVariable String id) {

        var returnValue = new AddressDtoOut();
        var user = userDao.loadById(UUID.fromString(id));
        BeanUtils.copyProperties(user.getAddress(), returnValue);
        return returnValue;
    }

    @Operation(summary = "Get List of UserEntity REST API")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<UserDtoOut>> getUsers(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "25") int limit) {
        List<UserEntity> users = userDao.getUsers(page, limit);

        var result = new UserExportConverter().convertToListDtoOut(users);

        return ResponseEntity.ok(result);
    }

    //http://localhost:8080/api/v1/users/password-reset-request
    @Operation(summary = "Request UserEntity password reset REST API")
    @ApiResponse(responseCode = "202", description = "HTTP Status 202 ACCEPTED")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PostMapping(path = "/password-reset-request",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel requestReset(@RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userDao.requestPasswordReset(passwordResetRequestDto.getEmail());

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    @Operation(summary = "Reset UserEntity password REST API")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(path = "/password-reset",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userDao.resetPassword(
                passwordResetDto.getToken(),
                passwordResetDto.getPassword());

        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    @Operation(summary = "Update UserEntity REST API",
            description = "Update UserEntity REST API is used to update the resource in database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping(path = "/{id}")
    public UserDtoOut updateUser(@PathVariable String id, @RequestBody @Valid UserDtoIn userDetails) {

        var returnValue = new UserDtoOut();
        var dto = new UserDtoIn();
        BeanUtils.copyProperties(userDetails, dto);

        var updateUser = userDao.updateUser(UUID.fromString(id), dto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    //http://localhost:8080/api/v1/users/email-verification?token=sdfsdf
    @Operation(summary = "Verify email token")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/email-verification", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userDao.verifyEmailToken(token);

        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

}
