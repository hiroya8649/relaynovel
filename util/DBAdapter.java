package relaynovel.util;

import java.sql.*;

public class DBAdapter {
  private static final DBAdapter instance = new DBAdapter();
  private Connection con = null;
  private Statement stm = null;

  // DBとのConnectionを立てる
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

  public static DBAdapter getInstance() {
    return instance;
  }

  public ResultSet getAll(String tableName) {
    String query = "SELECT * from " + tableName;
    return sendQuery(query);
  }

  // DBに資料を取得する
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

  // INSERTを実行するためのPreparedStatementを用意する
  // queryExample = "INSERT INTO course (course_code, course_desc, course_chair) VALUES (?, ?, ?)";
  public PreparedStatement setPreparedStatement(String query) {
    try  {
      return con.prepareStatement(query);
    } catch (SQLException se) {
      System.out.println("prepareStatementの初期化に失敗しました。");
      se.printStackTrace();
    }
    return null;
  }

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