package com.shopapp.api.controller;

import com.shopapp.api.converter.UserExportConverter;
import com.shopapp.api.model.response.ErrorMessages;
import com.shopapp.api.model.response.OperationStatusModel;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.exceptions.UserServiceException;
import com.shopapp.service.UserDao;
import com.shopapp.shared.dto.PasswordResetDto;
import com.shopapp.shared.dto.PasswordResetRequestDto;
import com.shopapp.shared.dto.UserDtoIn;
import com.shopapp.shared.dto.UserDtoOut;
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
@RequestMapping("/users")
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class UserController {

    private final UserDao userDao;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut createUser(@RequestBody @Valid UserDtoIn userDetails) {

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        var modelMapper = new ModelMapper();
        UserDtoIn userDtoIn = modelMapper.map(userDetails, UserDtoIn.class);

        var createdUser = userDao.createUser(userDtoIn);

        return modelMapper.map(createdUser, UserDtoOut.class);

    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        var model = new OperationStatusModel();
        model.setOperationName(RequestOperationName.DELETE.name());

        userDao.deleteUser(UUID.fromString(id));

        model.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return model;
    }

    //default return format is MediaType.APPLICATION_XML_VALUE or APPLICATION_JSON_VALUE. Depends on accept header
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDtoOut getUser(@PathVariable String id) {

        var returnValue = new UserDtoOut();
        var userDto = userDao.loadById(UUID.fromString(id));
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @GetMapping
    public ResponseEntity<List<UserDtoOut>> getUsers(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "25") int limit) {
        List<UserEntity> users = userDao.getUsers(page, limit);

        var result = new UserExportConverter().convertToListDtoOut(users);

        return ResponseEntity.ok(result);
    }

    /*
     * http://localhost:8080/api/v1/users/password-reset-request
     * */
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

    @PutMapping(path = "/{id}")
    public UserDtoOut updateUser(@PathVariable String id, @RequestBody @Valid UserDtoIn userDetails) {

        var returnValue = new UserDtoOut();
        var dto = new UserDtoIn();
        BeanUtils.copyProperties(userDetails, dto);

        var updateUser = userDao.updateUser(UUID.fromString(id), dto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    /*
     * http://localhost:8080/api/v1/users/email-verification?token=sdfsdf
     * */
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
