package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.data.entity.PasswordResetTokenEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.UserSnapshotEntity;
import com.appsdeveloperblog.app.ws.exceptions.InvalidParameterException;
import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.repository.PasswordResetTokenRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.security.UserPrincipal;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.service.UserSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages.EMAIL_ADDRESS_IN_USE;
import static com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages.MISSING_REQUIRED_FIELD;
import static com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages.NO_RECORD_FOUND;
import static com.appsdeveloperblog.app.ws.api.model.response.ErrorMessages.NO_RECORD_FOUND_BY_PROVIDED_PARAMETER;
import static com.appsdeveloperblog.app.ws.service.specification.GenericSpecification.SearchOperation;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<UserEntity> implements UserService {

    private UserRepository userRepository;

    private Utils utils;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PasswordResetTokenRepository passwordResetTokenRepository;

    //private AmazonSES amazonSES;

    private final UserSnapshotService userSnapshotService;

    @Override
    public UserEntity createUser(UserDtoIn user) {

        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException(EMAIL_ADDRESS_IN_USE.getErrorMessage());

        for (int i = 0; i < user.getAddresses().size(); i++) {
            AddressDtoIn address = user.getAddresses().get(i);
            address.setAddressId(utils.generateId(30));
            user.getAddresses().set(i, address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(user.getUserId()));
        userEntity.setVerified(false);

        return this.save(userEntity);

       /* UserDtoIn returnValue = modelMapper.map(storedUserDetails, UserDtoIn.class);

        return returnValue;*/
    }

    @Override
    public void deleteUser(String userId) {
        var found = userRepository.findByUserId(userId);

        if (found == null)
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());

        this.getRepository().deleteByUserId(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return getRepository().existsByEmail(email);
    }

    @Override
    public UserEntity findByEmail(String email) throws InvalidParameterException, UsernameNotFoundException {

        if (!StringUtils.hasText(email))
            throw new InvalidParameterException(MISSING_REQUIRED_FIELD.getErrorMessage());

        return userRepository.findByEmail(email);

        /*var dto = new UserDtoIn();
        BeanUtils.copyProperties(found, dto);
        return dto;*/
    }

    @Override
    public UserEntity findByUserId(String userId) throws InvalidParameterException, UsernameNotFoundException {

        if (!StringUtils.hasText(userId))
            throw new InvalidParameterException(MISSING_REQUIRED_FIELD.getErrorMessage());

        var result = userRepository.findByUserId(userId);
        if (result == null)
            throw new InvalidParameterException(NO_RECORD_FOUND_BY_PROVIDED_PARAMETER.getErrorMessage());

        return result;
    }

    @Override
    public UserEntity findOneBy(String firstName, String lastName, String email, Boolean isVerified) throws RuntimeException {

        List<UserEntity> allMatches = this.getUsers(1, Integer.MAX_VALUE, firstName, lastName, email, isVerified, true);
        if (allMatches.isEmpty())
            return null;
        else if (allMatches.size() == 1)
            return allMatches.get(0);
        else
            throw new RuntimeException(ErrorMessages.MULTIPLE_RESULTS_MATCH_CRITERIA_EXPECTED_ONE.getErrorMessage());
    }

    @Override
    public Class<UserEntity> getPojoClass() {
        return UserEntity.class;
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public List<UserEntity> getUsers() {
        return this.getUsers(1, Integer.MAX_VALUE);
    }

    @Override
    public List<UserEntity> getUsers(int page, int limit) {
        return this.getUsers(page, limit, null, null, null, null, true);
    }

    @Override
    public List<UserEntity> getUsers(int page, int limit, String firstName, //
                                     String lastName, String email, Boolean isVerified, //
                                     boolean isAsc, String... sortByProperties) {

        if (page <= 0)
            throw new InvalidParameterException("page must be greater than 0");
        else if (limit <= 0)
            throw new InvalidParameterException("limit must be greater than 0");

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable;
        if (sortByProperties == null || sortByProperties.length == 0)
            pageable = PageRequest.of(page - 1, limit, direction, "createdAt");
        else
            pageable = PageRequest.of(page - 1, limit, direction, sortByProperties);

        var userSpecificationBuilder = new GenericSpecificationsBuilder<UserEntity>();
        if (StringUtils.hasText(firstName))
            userSpecificationBuilder.with("firstName", SearchOperation.LIKE, false, List.of(firstName));
        if (StringUtils.hasText(lastName))
            userSpecificationBuilder.with("lastName", SearchOperation.LIKE, false, List.of(lastName));
        if (StringUtils.hasText(email))
            userSpecificationBuilder.with("email", SearchOperation.LIKE, false, List.of(email));
        if (isVerified != null)
            userSpecificationBuilder.with("isVerified", SearchOperation.EQUALITY, false, List.of(isVerified));


        return this.loadAll(userSpecificationBuilder.build(), pageable).getContent();

        /*List<UserDtoIn> returnValue = new ArrayList<>();
        for (UserEntity userEntity : users) {
            ModelMapper mapper = new ModelMapper();
            returnValue.add(mapper.map(userEntity, UserDtoIn.class));
        }*/
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        return new UserPrincipal(userEntity);

        // return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
        // 		userEntity.isVerified(),
        // 		true, true,
        // 		true, new ArrayList<>());

        //return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public void onBeforeWrite(UserEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {

            //load obj from db and persist db state to snapshot
            var dbStateOfObj = loadById(dbObj.getId());
            var snapshot = new UserSnapshotEntity();
            snapshot.setMaxRevision(dbStateOfObj.getRevision());
            snapshot.setEmail(dbStateOfObj.getEmail());
            snapshot.setEncryptedPassword(dbStateOfObj.getEncryptedPassword());
            snapshot.setFirstName(dbStateOfObj.getFirstName());
            snapshot.setLastName(dbStateOfObj.getLastName());
            this.userSnapshotService.save(snapshot);
        }
    }

    @Override
    public boolean requestPasswordReset(String email) {

        boolean returnValue = false;

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            return returnValue;
        }

        String token = new Utils().generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = new AmazonSES().sendPasswordResetRequest(
                userEntity.getFirstName(),
                userEntity.getEmail(),
                token);

        return returnValue;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;

        if (Utils.hasTokenExpired(token)) {
            return returnValue;
        }

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null) {
            return returnValue;
        }

        // Prepare new password
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // Update User password in database
        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
        userEntity.setEncryptedPassword(encodedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // Verify if password was saved successfully
        if (savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnValue = true;
        }

        // Remove Password Reset token from database
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }

    @Override
    public UserEntity updateUser(String id, UserDtoIn dto) {
        UserEntity entity = userRepository.findByUserId(id);
        if (entity == null)
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());

        if (!dto.getFirstName().isBlank())
            entity.setFirstName(dto.getFirstName());
        if (!dto.getLastName().isBlank())
            entity.setLastName(dto.getLastName());

        return userRepository.save(entity);

        /*BeanUtils.copyProperties(persisted, returnValue);
        return returnValue;*/
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        // Find user by token
        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);

        if (userEntity != null) {
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setVerified(Boolean.TRUE);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }

        return returnValue;
    }
}
