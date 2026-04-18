package com.lab.app;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserServiceTest {

  UserService service = new UserService();

  @Test
  public void edadValida() {
    Assert.assertTrue(service.validarEdad(30));
  }

  @Test
  public void edadInvalidaMenor18() {
    Assert.assertFalse(service.validarEdad(10));
  }

  @Test
  public void edadInvalidaMayor65() {
    Assert.assertFalse(service.validarEdad(66));
  }

}