package vn.tqkhanhsn.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tqkhanhsn.controller.request.UserCreationRequest;
import vn.tqkhanhsn.controller.request.UserUpdateRequest;
import vn.tqkhanhsn.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name="User controller")
@Slf4j(topic = "UserController")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "API to create a new user in the system")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request) {
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "User has been successfully created");
        result.put("data", userService.save(request));
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @Operation(summary = "Update user", description = "API to update user information")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody UserUpdateRequest request) {
        log.info("Updating user with request: {}", request);
        userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User has been successfully updated");
        result.put("data", "");
        return result;
    }

}
