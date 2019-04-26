package com.tecprime.guilherme.UserRegistrationSystem;

public class CustomErrorType extends UserDTO {
  private String errorMessage;

  public CustomErrorType(final String errorMessage){
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage(){
    return errorMessage;
  }
}
