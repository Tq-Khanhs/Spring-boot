package vn.tqkhanhsn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.tqkhanhsn.common.UserStatus;
import vn.tqkhanhsn.controller.request.AddressRequest;
import vn.tqkhanhsn.controller.request.UserCreationRequest;
import vn.tqkhanhsn.controller.request.UserPasswordRequest;
import vn.tqkhanhsn.controller.request.UserUpdateRequest;
import vn.tqkhanhsn.controller.response.UserPageResponse;
import vn.tqkhanhsn.controller.response.UserResponse;
import vn.tqkhanhsn.exception.ResourceNotFoundException;
import vn.tqkhanhsn.model.AddressEntity;
import vn.tqkhanhsn.model.UserEntity;
import vn.tqkhanhsn.repository.AddressRepository;
import vn.tqkhanhsn.repository.UserRepository;
import vn.tqkhanhsn.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order( Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("^(\\w+)(:)(asc|desc)$");
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String column = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,column);
                }else{
                    order = new Sort.Order(Sort.Direction.DESC,column);
                }
            }
        }

        int pageNo =0;
        if(page>0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));
        Page<UserEntity> userEntities = null;
        if(StringUtils.hasLength(keyword)){
            userEntities =  userRepository.searchByKeywords(keyword,pageable);
        }else {
            userEntities =userRepository.findAll(pageable);
        }

        return getUserPageResponse(page, size, userEntities);
    }



    @Override
    public UserResponse findById(Long id) {

        log.info("Get user detail by id: {}", id);
        UserEntity userEntity = getUserById(id);


        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthDate())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
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


    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> userEntities) {
        List<UserResponse> userList = userEntities.stream().map(
                userEntity -> UserResponse.builder()
                        .id(userEntity.getId())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .gender(userEntity.getGender())
                        .birthday(userEntity.getBirthDate())
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .phone(userEntity.getPhone())
                        .build()
        ).toList();

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(page);
        userPageResponse.setPageSize(size);
        userPageResponse.setTotalPages(userEntities.getTotalPages());
        userPageResponse.setTotalElements(userEntities.getTotalElements());
        userPageResponse.setUsers(userList);
        return userPageResponse;
    }
}
