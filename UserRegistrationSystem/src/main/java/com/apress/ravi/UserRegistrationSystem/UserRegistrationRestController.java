package com.apress.ravi.UserRegistrationSystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRegistrationRestController {
  public static final Logger logger =
      LoggerFactory.getLogger(UserRegistrationRestController.class);


  private UserJpaRepository userJpaRepository;

  @Autowired
  public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<UserDTO>> listAllUsers() {
    List<UserDTO> users = userJpaRepository.findAll();
    return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
  }

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO user) {
    userJpaRepository.save(user);
    return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
    UserDTO user = userJpaRepository.findById(id).get();
    return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable("id") final Long id, @RequestBody UserDTO user) {

    UserDTO currentUser = userJpaRepository.findById(id).get();
    currentUser.setFirstName(user.getFirstName());
    currentUser.setLastName(user.getLastName());
    currentUser.setAvatar(user.getAvatar());

    userJpaRepository.saveAndFlush(currentUser);

    return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
//    userJpaRepository.delete(id); <- ORIGINAL

//  ALTERADO
    UserDTO user = userJpaRepository.findById(id).get();
    userJpaRepository.delete(user);

    return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
  }
}
