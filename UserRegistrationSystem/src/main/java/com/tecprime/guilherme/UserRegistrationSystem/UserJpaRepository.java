package com.tecprime.guilherme.UserRegistrationSystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDTO, Long> {

  UserDTO findByFirstName(String firstname);
  UserDTO findByLastName(String lastname);

  @Override
  Page<UserDTO> findAll(Pageable pageable);


}
