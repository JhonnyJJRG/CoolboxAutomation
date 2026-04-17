package com.lab.app;

public class LoginService {

  private AccountState state = AccountState.ACTIVE;
  private int failedAttempts = 0;

  public boolean login(String user, String pass) {
    if (user == null || pass == null) return false;
    return user.equals("admin") && pass.equals("1234");
  }

  public void loginValidateRetries(boolean success) {
    if (state == AccountState.BLOCKED) {
      return;
    }

    if (success) {
      failedAttempts = 0;
    } else {
      failedAttempts++;

      if (failedAttempts >= 3) {
        state = AccountState.BLOCKED;
      }
    }
  }

  public AccountState getState() {
    return state;
  }

  public int getFailedAttempts() {
    return failedAttempts;
  }

}
