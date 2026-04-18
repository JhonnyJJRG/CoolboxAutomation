package com.lab.app;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class LoginServiceTest {

  @Test
  void shouldIncreaseAttempts() {
    LoginService acc = new LoginService();

    acc.loginValidateRetries(false);

    assertEquals(1, acc.getFailedAttempts());
    assertEquals(AccountState.ACTIVE, acc.getState());
  }

  @Test
  void shouldBlockAfterThreeFailures() {
    LoginService acc = new LoginService();

    acc.loginValidateRetries(false);
    acc.loginValidateRetries(false);
    acc.loginValidateRetries(false);

    assertEquals(AccountState.BLOCKED, acc.getState());
  }

  @Test
  void shouldResetAttemptsOnSuccess() {
    LoginService acc = new LoginService();

    acc.loginValidateRetries(false);
    acc.loginValidateRetries(false);
    acc.loginValidateRetries(true);

    assertEquals(0, acc.getFailedAttempts());
    assertEquals(AccountState.ACTIVE, acc.getState());
  }

  @Test
  void blockedLoginServiceShouldNotChange() {
    LoginService acc = new LoginService();

    acc.loginValidateRetries(false);
    acc.loginValidateRetries(false);
    acc.loginValidateRetries(false);

    acc.loginValidateRetries(true);

    assertEquals(AccountState.BLOCKED, acc.getState());
  }


}