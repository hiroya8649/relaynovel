/*@aouther 新田俊輔
 *Textと連携して、リレー小説の新規作成を行うクラス
 *主に、具体的な動作の動きを行っている
 */
package relaynovel.controller;

import java.sql.*;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.Novel;
import relaynovel.model.Text;


public class TextController {
  private final static TextController instance = new TextController();

  private TextController() {}

  public static TextController getInstance() {
    return instance;
  }

  /*
   *insert:入力された情報を持った小説の制作を行う
   *       novel_id、user_id、書き込まれた文、制作した日付を引数とする
   */
  public boolean insert(Text text) {
    String query = "INSERT INTO text (novel_id, user_id, text, date) values(?,?,?,?)";
    try {
      PreparedStatement stm = DBAdapter.getInstance().setPreparedStatement(query);
      stm.setInt(1, text.novelId);
      stm.setInt(2, text.userId);
      stm.setString(3, text.text);
      stm.setTimestamp(4, text.date); //date処理が必要
      boolean result = stm.execute();
      stm.close();
      return true;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

  /*
   *getAll:データベースから、全文のデータを読み込む
   */
  public ArrayList<Text> getAll() {
    ArrayList<Text> texts = new ArrayList<Text>();
    try {
      ResultSet rs = DBAdapter.getInstance().getAll("text");
      while(rs.next()) {
        texts.add(new Text(rs));
      }
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return texts;
  }

  /**
  * This method is for getting user (using user_id).
  * @param userId the user's id
  * @return User
  */
  public Text getById(int textId) {
    String query = "SELECT * from text where text_id = " + textId;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      Text text;
      if (rs.next()) {
        text = new Text(rs);
      } else {
        text = new Text();
      }
      DBAdapter.getInstance().closeStatement();
      return text;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return new Text();
  }

}