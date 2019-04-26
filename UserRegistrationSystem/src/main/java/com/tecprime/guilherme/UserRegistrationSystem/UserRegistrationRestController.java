package com.tecprime.guilherme.UserRegistrationSystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    if(users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }


  @GetMapping(value = "/paginate")
  public ResponseEntity<Map<String, Object>> listPaginateUsers(
      @RequestParam("page") int page,
      @RequestParam("perPage") int perPage) {

    Page<UserDTO> pageUsers = userJpaRepository.findAll(new PageRequest(page, perPage));
    long count = pageUsers.getTotalPages();
    List<UserDTO> users = pageUsers.getContent();

    Map<String, Object> response = new HashMap<>();
    response.put("content", users);
    response.put("totalPages", count);

    if(users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO user) {
    if(userJpaRepository.findByFirstName(user.getFirstName()) != null &&
      userJpaRepository.findByLastName(user.getLastName()) != null) {
      return new ResponseEntity<>(new CustomErrorType("Não foi possível criar um novo usuário." +
          " Usuário de nome " + user.getFirstName() + " " + user.getLastName() +
          " já existe!"), HttpStatus.CONFLICT);
    }
    userJpaRepository.save(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }


  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
    try{
      UserDTO user = userJpaRepository.findById(id).get();
      return new ResponseEntity<>(user, HttpStatus.OK);

    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(
          new CustomErrorType("Usuário de id:" + id +
              " não encontrado!"), HttpStatus.NOT_FOUND);
    }
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable("id") final Long id, @RequestBody UserDTO user) {
    try{
      UserDTO currentUser = userJpaRepository.findById(id).get();

      currentUser.setFirstName(user.getFirstName());
      currentUser.setLastName(user.getLastName());
      currentUser.setAvatar(user.getAvatar());

      userJpaRepository.saveAndFlush(currentUser);

      return new ResponseEntity<>(currentUser, HttpStatus.OK);

    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(new CustomErrorType("Não foi possível realizar a atualização." +
          " Usuário de id:" + id + " não encontrado!"), HttpStatus.NOT_FOUND);
    }
  }


  @DeleteMapping(value = "/{id}")
  public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
    try {
      UserDTO user = userJpaRepository.findById(id).get();
      userJpaRepository.delete(user);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(new CustomErrorType("Não foi possível realizar a exclusão." +
          " Usuário de id:" + id + " não encontrado!"), HttpStatus.NOT_FOUND);
    }
  }
}

