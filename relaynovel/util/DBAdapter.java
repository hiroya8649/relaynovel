package relaynovel.util;

import java.sql.*;

/**
* DBAdapter is to handle the connection and statement to database.
* @author  Chang
*/
public class DBAdapter {
  private static final DBAdapter instance = new DBAdapter();
  private Connection con = null;
  private Statement stm = null;

  /**
  * constructor, setup the connection to database.
  * @author  Chang
  */
  private DBAdapter() {
    try {
      this.con = DriverManager.getConnection(
        "jdbc:mysql://localhost/relay_novel?useSSL=false", "root", "Card13531"
        );
    }
    catch (SQLException se) {
      System.out.println("MySQLの接続に失敗しました。");
      se.printStackTrace();
      this.closeStatement();
      this.closeConnection();
    }
  };

  /**
  * This method is for singleton.
  * @return DBAdapter
  */
  public static DBAdapter getInstance() {
    return instance;
  }

  /**
  * This method will return ResultSet which is all data in a specified table.
  * @param tableName The name of the table.
  * @return ResultSet
  */
  public ResultSet getAll(String tableName) {
    String query = "SELECT * from " + tableName;
    return sendQuery(query);
  }

  /**
  * This method will execute a query and return ResultSet.
  * @param query The specified query.
  * @return ResultSet
  */
  public ResultSet sendQuery(String query) {
    this.stm = null;
    ResultSet rs = null;
    try {
      this.stm = this.con.createStatement();
      rs = stm.executeQuery(query);
    }
    catch (SQLException se) {
      System.out.println("queryの送信に失敗しました。");
      System.out.println(query);
      se.printStackTrace();
      this.closeStatement();
      this.closeConnection();
    }
    return rs;
  }

  /**
  * This method return a PreparedStatement (for doing insert) with the specified query.
  * @param query The specified query.
  * @return PreparedStatement
  */
  public PreparedStatement setPreparedStatement(String query) {
    try  {
      return con.prepareStatement(query);
    } catch (SQLException se) {
      System.out.println("prepareStatementの初期化に失敗しました。");
      se.printStackTrace();
    }
    return null;
  }

  /**
  * This method is to close the connection to database.
  */
  public void closeConnection() {
    if (this.con != null) {
      try {
        this.con.close();
      } catch (SQLException e) {
        System.out.println("Connectionのクローズに失敗しました。");
        e.printStackTrace();
      }
      System.exit(1);
    }
  }

  /**
  * This method is to close the statement to connection.
  */
  public void closeStatement() {
    if (this.stm != null) {
      try {
        this.stm.close();
      } catch (SQLException e) {
        System.out.println("Statementのクローズに失敗しました。");
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
}