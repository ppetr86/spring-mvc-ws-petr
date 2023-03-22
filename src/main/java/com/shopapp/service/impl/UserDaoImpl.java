package com.shopapp.service.impl;

import com.shopapp.api.model.response.ErrorMessages;
import com.shopapp.data.entity.*;
import com.shopapp.data.entity.snapshots.UserSnapshotEntity;
import com.shopapp.exceptions.InvalidParameterException;
import com.shopapp.exceptions.UserServiceException;
import com.shopapp.repository.PasswordResetTokenRepository;
import com.shopapp.repository.RoleRepository;
import com.shopapp.repository.UserRepository;
import com.shopapp.security.UserPrincipal;
import com.shopapp.service.UserDao;
import com.shopapp.service.UserSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import com.shopapp.service.specification.GenericSpecificationsBuilder;
import com.shopapp.shared.AmazonSES;
import com.shopapp.shared.Roles;
import com.shopapp.shared.Utils;
import com.shopapp.shared.dto.UserDtoIn;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shopapp.api.model.response.ErrorMessages.*;
import static com.shopapp.service.specification.GenericSpecification.SearchOperation;

@Service
@AllArgsConstructor
public class UserDaoImpl extends AbstractIdTimeRevisionDaoImpl<UserEntity> implements UserDao {

    private final UserSnapshotDao userSnapshotDao;
    private final RoleRepository roleRepository;
    private UserRepository userRepository;
    private Utils utils;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserEntity createUser(UserDtoIn dtoIn) {

        if (userRepository.existsByEmail(dtoIn.getEmail()))
            throw new RuntimeException(EMAIL_ADDRESS_IN_USE.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(dtoIn, UserEntity.class);
        userEntity.setId(UUID.randomUUID());

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(dtoIn.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(dtoIn.getEmail()));
        userEntity.setVerified(false);

        if (!dtoIn.getCreditCards().isEmpty())
            userEntity.setCreditCards(dtoIn.getCreditCards()
                    .stream()
                    .map(each -> new CreditCardEntity(each, userEntity))
                    .collect(Collectors.toSet()));

        if (dtoIn.getAddress() != null) {
            userEntity.setAddress(new AddressEntity(dtoIn.getAddress()));
            userEntity.getAddress().setUser(userEntity);
        }

        //Set Roles
        Set<RoleEntity> roleEntities = Set.of(roleRepository.findByName(Roles.ROLE_CUSTOMER));
        userEntity.setRoles(roleEntities);

        return this.save(userEntity);
    }

    @Override
    public void deleteUser(UUID id) {
        var found = this.loadById(id);

        if (found == null)
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());

        this.delete(id);
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
    public UserEntity findByUserId(UUID id) throws InvalidParameterException, UsernameNotFoundException {

        if (id == null)
            throw new InvalidParameterException(MISSING_REQUIRED_FIELD.getErrorMessage());

        var result = this.loadById(id);
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        return new UserPrincipal(userEntity);
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
            snapshot.setFirstName(dbStateOfObj.getAddress().getFirstName());
            snapshot.setLastName(dbStateOfObj.getAddress().getLastName());
            this.userSnapshotDao.save(snapshot);
        }
    }

    @Override
    public boolean requestPasswordReset(String email) {

        boolean returnValue = false;

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            return returnValue;
        }

        String token = new Utils().generatePasswordResetToken(userEntity.getId().toString());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = new AmazonSES().sendPasswordResetRequest(
                userEntity.getAddress().getFirstName(),
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
    public UserEntity updateUser(UUID id, UserDtoIn dto) {
        UserEntity entity = this.loadById(id);
        if (entity == null)
            throw new UserServiceException(NO_RECORD_FOUND.getErrorMessage());

        entity.setAddress(new AddressEntity(dto.getAddress()));

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
