package com.apress.ravi.UserRegistrationSystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDTO, Long> {

  UserDTO findByFirstName(String firstname);

}
