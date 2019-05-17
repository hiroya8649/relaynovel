package relaynovel.model;

import java.util.Date;

public class User {
  public Integer userId = null;
  public String name = null;
  public String password = null;
  public Date birthday = null;
  public Integer gender = null;

  public User() {}

  public User(Integer userId, String name, String password, Date birthday, Integer gender) {
    this.userId = userId;
    this.name = name;
    this.password = password;
    this.birthday = birthday;
    this.gender = gender;
  }

  public boolean isNotNull() {
    return this.userId != null;
  }
}