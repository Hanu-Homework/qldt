package edu.hanu.qldt.controller;

import edu.hanu.qldt.service.auth.impl.UserServiceImpl;
import edu.hanu.qldt.dto.response.UserResponseDTO;
import edu.hanu.qldt.model.user.User;
import edu.hanu.qldt.service.auth.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link UserController} contains all rest api function that need to
 * manage the application users.
 *
 * @see UserService
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get all Users.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The users don't exists"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/user/all")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') " +
            "or hasRole('ROLE_HEADTEACHER') " +
            "or hasRole('ROLE_ADMIN') " +
            "or @securityService.hasStudentAccess(principal.id, #id)")
    @ApiOperation(value = "Get User by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/user/{id}")
    public User getById(@PathVariable Long id) {
        return  userService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Reset credentials.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/user/reset-credentials/{username}")
    public ResponseEntity<Map> resetCredentials(@PathVariable String username) {
        userService.resetCredentials(username);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    @ApiOperation(value = "Check the username is unique.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The username is not valid"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping(value = "/user/username/{username}")
    public boolean isUsernameUnique(@PathVariable String username) {
        return userService.isUsernameUnique(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #id")
    @ApiOperation(value = "Update user by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't found"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping(value = "/user/update/{id}")
    public User update(@PathVariable Long id,
                       @RequestBody UserResponseDTO userResponseDTO) {
        return userService.update(id, userResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete User by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping(value = "/user/{id}")
    public String delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Add new User.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Username is already in use"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping(value = "/user/create")
    public ResponseEntity<?> addUser(@RequestBody UserResponseDTO userResponseDTO,
                                     UriComponentsBuilder ucBuilder) {
        User user = userService.save(userResponseDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get current User.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Current user not initialized."),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping("/user/whoami")
    public User user() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
