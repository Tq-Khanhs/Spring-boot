package vn.tqkhanhsn.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.tqkhanhsn.controller.request.UserCreationRequest;
import vn.tqkhanhsn.controller.request.UserPasswordRequest;
import vn.tqkhanhsn.controller.request.UserUpdateRequest;
import vn.tqkhanhsn.controller.response.UserPageResponse;
import vn.tqkhanhsn.controller.response.UserResponse;
import vn.tqkhanhsn.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name="User controller")
@Slf4j(topic = "UserController")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "API to create a new user in the system")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCreationRequest request) {
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "User has been successfully created");
        result.put("data", userService.save(request));
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @Operation(summary = "Update user", description = "API to update user information")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        log.info("Updating user with request: {}", request);
        userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User has been successfully updated");
        result.put("data", "");
        return result;
    }

    @Operation(summary = "Delete user", description = "API to delete a user by ID")
    @DeleteMapping("/del/{id}")
    public Map<String, Object> deleteUser(@PathVariable @Min(value = 1,message = "ID must be greater or equal than 0") Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.delete(id);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "User has been successfully deleted");
        result.put("data", "");
        return result;
    }

    @Operation(summary = "Change user password", description = "API to change user password")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody @Valid UserPasswordRequest request) {
        log.info("Changing password for user with request: {}", request);
        userService.changePassword(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "User password has been successfully changed");
        result.put("data", "");
        return result;
    }


    @Operation(summary = "Get user detail", description = "API to get user detail by ID")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable @Min(value = 1, message = "UserId must be equal or greater than 1") Long userId) {
        log.info("Getting user detail for user ID: {}", userId);
        UserResponse userDetail =  userService.findById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "User detail has been successfully retrieved");
        result.put("data", userDetail);
        return result;
    }

    @Operation(summary = "get list of users", description = "API to get list of users")
    @GetMapping("/list")
    public Map<String, Object> getUsers(@RequestParam (required = false) String keyword,
                                        @RequestParam (required = false) String sort,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        log.info("Getting list of users");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "List of users has been successfully retrieved");
        result.put("data", userService.findAll(keyword, sort, page, size));
        return result;
    }

}
