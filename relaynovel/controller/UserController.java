package relaynovel.controller;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.User;

/**
* UserController can help the Views to get/put the User data
* @author  Chang
*/
public class UserController {
  private final static UserController instance = new UserController();

  private UserController() {}

  /**
  * This method is for singleton.
  * @return UserController
  */
  public static UserController getInstance() {
    return instance;
  }

  /**
  * This method is for inserting user (No need to assign user_id).
  * @param user the user which userId is null.
  * @return boolean success or not.
  */
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
      return true;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

  /**
  * This method is for getting user (using user_id).
  * @param userId the user's id
  * @return User
  */
  public User getById(int userId) {
    String query = "SELECT * from user where user_id = " + userId;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      User user;
      if (rs.next()) {
        user = new User(rs);
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

  /**
  * This method is for getting user (using name).
  * @param name the user's name
  * @return User
  */
  public User getByName(String name) {
    String query = "SELECT * from user where name = '" + name + "'";
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      User user;
      if (rs.next()) {
        user = new User(rs);
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

  /**
  * This method is for getting user (using name, password).
  * @param name the user's name
  * @param password the user's password
  * @return User
  */
  public User login(String name, String password) {
    String query = "select * from user where name = '" + name + "' and password = '" + password + "'";
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      User user;
      if (rs.next()) {
        user = new User(rs);
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

  /**
  * This method is for getting all users.
  * @return ArrayList<User>
  */
  public ArrayList<User> getAll() {
    ArrayList<User> users = new ArrayList<User>();
    try {
      ResultSet rs = DBAdapter.getInstance().getAll("user");
      while(rs.next()) {
        users.add(new User(rs));
      }
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return users;
  }
}