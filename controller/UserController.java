package relaynovel.controller;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.User;

public class UserController {
  public final static UserController instance = new UserController();

  private UserController() {}

  public static UserController getInstance() {
    return instance;
  }

  public boolean insert(User user) {
    String query = "INSERT INTO user (name, password, birthday, gender) values(?,?,?,?)";
    try {
      PreparedStatement stm = DBAdapter.getInstance().setPreparedStatement(query);
      stm.setString(1, user.name);
      stm.setString(2, user.password);
      stm.setDate(3, new Date(user.birthday.getTime()));
      stm.setInt(4, user.gender);
      boolean result = stm.execute();
      stm.close();
      return result;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

  public User getById(int id) {
    String query = "SELECT * from user where id = " + id;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      User user;
      if (rs.next()) {
        user = new User(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("password"),
          rs.getDate("birthday"),
          rs.getInt("gender")
        );
      } else {
        user = new User();
      }
      DBAdapter.getInstance().closeStatement();
      return user;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return new User();
  }

  public ArrayList<User> getAll() {
    ArrayList<User> users = new ArrayList<User>();
    try {
      ResultSet rs = DBAdapter.getInstance().getAll("user");
      while(rs.next()) {
        users.add(new User(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("password"),
          rs.getDate("birthday"),
          rs.getInt("gender")
        ));
      }
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return users;
  }
}