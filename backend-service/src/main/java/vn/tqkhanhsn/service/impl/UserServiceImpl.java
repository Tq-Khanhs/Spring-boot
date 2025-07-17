package vn.tqkhanhsn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tqkhanhsn.common.UserStatus;
import vn.tqkhanhsn.controller.request.AddressRequest;
import vn.tqkhanhsn.controller.request.UserCreationRequest;
import vn.tqkhanhsn.controller.request.UserPasswordRequest;
import vn.tqkhanhsn.controller.request.UserUpdateRequest;
import vn.tqkhanhsn.controller.response.UserResponse;
import vn.tqkhanhsn.exception.ResourceNotFoundException;
import vn.tqkhanhsn.model.AddressEntity;
import vn.tqkhanhsn.model.UserEntity;
import vn.tqkhanhsn.repository.AddressRepository;
import vn.tqkhanhsn.repository.UserRepository;
import vn.tqkhanhsn.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {

        return null;
    }

    @Override
    public UserResponse findById(Long id) {

        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(UserCreationRequest req) {

        log.info("Saving user with request: {}", req);
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(req.getFirstName());
        userEntity.setLastName(req.getLastName());
        userEntity.setGender(req.getGender());
        userEntity.setBirthDate(req.getBirthDate());
        userEntity.setUsername(req.getUsername());
        userEntity.setEmail(req.getEmail());
        userEntity.setPhone(req.getPhone());
        userEntity.setType(req.getType());
        userEntity.setStatus(UserStatus.NONE);
        userRepository.save(userEntity);
        if (userEntity.getId() != null) {
            log.info("user id: {}", userEntity.getId());
            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddresses().forEach(address -> {
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setApartmentNumber(address.getApartmentNumber());
                addressEntity.setFloor(address.getFloor());
                addressEntity.setBuilding(address.getBuilding());
                addressEntity.setStreetNumber(address.getStreetNumber());
                addressEntity.setStreet(address.getStreet());
                addressEntity.setCity(address.getCity());
                addressEntity.setCountry(address.getCountry());
                addressEntity.setAddressType(address.getAddressType());
                addressEntity.setUserId(userEntity.getId());
                addresses.add(addressEntity);
            });
            addressRepository.saveAll(addresses);
            log.info("Saved addresses: {}", addresses);
        }
        return userEntity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        log.info("Update User with request: {}", req);
        UserEntity userEntity = getUserById(req.getId());
        userEntity.setFirstName(req.getFirstName());
        userEntity.setLastName(req.getLastName());
        userEntity.setBirthDate(req.getBirthDate());
        userEntity.setUsername(req.getUsername());
        userEntity.setEmail(req.getEmail());
        userEntity.setPhone(req.getPhone());
        userEntity.setGender(req.getGender());

        userRepository.save(userEntity);
        log.info("Updated user: {}", userEntity);

        List<AddressEntity> addresses = new ArrayList<>();

        req.getAddresses().forEach(address -> {
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(userEntity.getId(), address.getAddressType());
            if (addressEntity == null) {
                addressEntity = new  AddressEntity();

            }

            addressEntity.setApartmentNumber(address.getApartmentNumber());
            addressEntity.setFloor(address.getFloor());
            addressEntity.setBuilding(address.getBuilding());
            addressEntity.setStreetNumber(address.getStreetNumber());
            addressEntity.setStreet(address.getStreet());
            addressEntity.setCity(address.getCity());
            addressEntity.setCountry(address.getCountry());
            addressEntity.setAddressType(address.getAddressType());
            addressEntity.setUserId(userEntity.getId());
            addresses.add(addressEntity);
        });

        addressRepository.saveAll(addresses);
        log.info("Updated addresses: {}", addresses);



    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Changing password for user with request: {}", req);
        UserEntity userEntity = getUserById(req.getId());
        if(req.getPassword().equals(req.getConfirmPassword())) {
            userEntity.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        userRepository.save(userEntity);
        log.info("Change password user: {}", userEntity);

    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        UserEntity userEntity = getUserById(id);
        userEntity.setStatus(UserStatus.INACTIVE);
        userRepository.save(userEntity);
        log.info("Deleted user: {}", userEntity);
    }

    private UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "));
    }
}
