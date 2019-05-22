package relaynovel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
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

  public User(ResultSet rs) {
    try {
      this.userId = rs.getInt("user_id");
      this.name = rs.getString("name");
      this.password = rs.getString("password");
      this.birthday = rs.getDate("birthday");
      this.gender = rs.getInt("gender");
    } catch(SQLException se) {
      System.out.println("ResultSetからUserの変換にエラーが発生しました。");
      se.printStackTrace();
    }
  }

  public boolean isNotNull() {
    return this.userId != null;
  }
}