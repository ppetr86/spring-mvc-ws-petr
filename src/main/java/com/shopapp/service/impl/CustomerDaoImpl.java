package com.shopapp.service.impl;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.data.entity.PasswordResetTokenEntity;
import com.shopapp.exceptions.CustomerServiceException;
import com.shopapp.exceptions.InvalidParameterException;
import com.shopapp.repository.CustomerRepository;
import com.shopapp.repository.PasswordResetTokenRepository;
import com.shopapp.security.UserPrincipal;
import com.shopapp.service.CustomerDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import com.shopapp.service.specification.GenericSpecification;
import com.shopapp.service.specification.GenericSpecificationsBuilder;
import com.shopapp.shared.AmazonSES;
import com.shopapp.shared.Utils;
import com.shopapp.shared.dto.CustomerDtoIn;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shopapp.api.model.response.ErrorMessages.EMAIL_ADDRESS_IN_USE;
import static com.shopapp.api.model.response.ErrorMessages.NO_RECORD_FOUND;

@Service
@AllArgsConstructor
public class CustomerDaoImpl extends AbstractIdDaoImpl<CustomerEntity> implements CustomerDao {

    private CustomerRepository customerRepository;

    private Utils utils;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public Class<CustomerEntity> getPojoClass() {
        return CustomerEntity.class;
    }

    @Override
    public CustomerRepository getRepository() {
        return this.customerRepository;
    }

    @Override
    public CustomerEntity createCustomer(CustomerDtoIn dtoIn) {
        if (customerRepository.existsByEmail(dtoIn.getEmail()))
            throw new RuntimeException(EMAIL_ADDRESS_IN_USE.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        CustomerEntity customerEntity = modelMapper.map(dtoIn, CustomerEntity.class);

        customerEntity.setId(UUID.randomUUID());
        customerEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(dtoIn.getPassword()));
        customerEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(dtoIn.getEmail()));
        customerEntity.setVerified(false);

        if (!dtoIn.getCreditCards().isEmpty())
            customerEntity.setCreditCards(dtoIn.getCreditCards()
                    .stream()
                    .map(each -> new CreditCardEntity(each, customerEntity))
                    .collect(Collectors.toSet()));

        if (dtoIn.getAddress() != null) {
            customerEntity.setAddress(new AddressEntity(dtoIn.getAddress()));
            customerEntity.getAddress().setCustomer(customerEntity);
        }

        return this.save(customerEntity);
    }

    @Override
    public void deleteCustomer(UUID id) {
        var found = this.loadById(id);
        if (found == null)
            throw new CustomerServiceException(NO_RECORD_FOUND.getErrorMessage());

        this.delete(id);
    }

    @Override
    public List<CustomerEntity> getCustomers() {
        return this.getCustomers(1, Integer.MAX_VALUE);
    }

    @Override
    public List<CustomerEntity> getCustomers(int page, int limit) {
        return this.getCustomers(page, limit, null, null, null, null, true);
    }

    @Override
    public List<CustomerEntity> getCustomers(int page, int limit, String firstName, String lastName, String email, Boolean isVerified, boolean isAsc, String... sortByProperties) {
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

        var customerEntityGenericSpecificationsBuilder = new GenericSpecificationsBuilder<CustomerEntity>();
        if (StringUtils.hasText(firstName))
            customerEntityGenericSpecificationsBuilder.with("firstName", GenericSpecification.SearchOperation.LIKE, false, List.of(firstName));
        if (StringUtils.hasText(lastName))
            customerEntityGenericSpecificationsBuilder.with("lastName", GenericSpecification.SearchOperation.LIKE, false, List.of(lastName));
        if (StringUtils.hasText(email))
            customerEntityGenericSpecificationsBuilder.with("email", GenericSpecification.SearchOperation.LIKE, false, List.of(email));
        if (isVerified != null)
            customerEntityGenericSpecificationsBuilder.with("isVerified", GenericSpecification.SearchOperation.EQUALITY, false, List.of(isVerified));


        return this.loadAll(customerEntityGenericSpecificationsBuilder.build(), pageable).getContent();
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;

        var customerEntity = customerRepository.findByEmail(email);

        if (customerEntity == null) {
            return returnValue;
        }

        String token = new Utils().generatePasswordResetToken(customerEntity.getId().toString());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setCustomerDetails(customerEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = new AmazonSES().sendPasswordResetRequest(
                customerEntity.getAddress().getFirstName(),
                customerEntity.getEmail(),
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

        // Update Customer password in database
        CustomerEntity customerEntity = passwordResetTokenEntity.getCustomerDetails();
        customerEntity.setEncryptedPassword(encodedPassword);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);

        // Verify if password was saved successfully
        if (savedCustomerEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnValue = true;
        }

        // Remove Password Reset token from database
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }


    @Override
    public CustomerEntity updateCustomer(UUID id, CustomerDtoIn dto) {
        CustomerEntity entity = this.loadById(id);
        if (entity == null)
            throw new CustomerServiceException(NO_RECORD_FOUND.getErrorMessage());

        if (!dto.getAddress().getFirstName().isBlank())
            entity.getAddress().setFirstName(dto.getAddress().getFirstName());
        if (!dto.getAddress().getLastName().isBlank())
            entity.getAddress().setLastName(dto.getAddress().getLastName());

        return customerRepository.save(entity);

        /*BeanUtils.copyProperties(persisted, returnValue);
        return returnValue;*/
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        // Find customer by token
        CustomerEntity customerEntity = customerRepository.findCustomerByEmailVerificationToken(token);

        if (customerEntity != null) {
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired) {
                customerEntity.setEmailVerificationToken(null);
                customerEntity.setVerified(Boolean.TRUE);
                customerRepository.save(customerEntity);
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerEntity customerEntity = customerRepository.findByEmail(email);

        if (customerEntity == null)
            throw new UsernameNotFoundException(email);

        return new UserPrincipal(customerEntity);

        // return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
        // 		userEntity.isVerified(),
        // 		true, true,
        // 		true, new ArrayList<>());

        //return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
