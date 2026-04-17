package com.lab.app;

public class UserService {

  public boolean validarEdad(int edad) {
    return edad >= 18 && edad <= 65;
  }

  public boolean validarPassword(String password) {
    return password != null && password.length() >= 8;
  }

}
