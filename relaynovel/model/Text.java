/*@aouther 新田俊輔
 *書き込んだ小説内の一文を一時的に保持するモデル
 */

package relaynovel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class Text {
  public Integer textId = null;
  public Integer novelId = null;
  public Integer userId = null;
  public String text = null;
  public Timestamp date = null;

  /**
   * Textのコンストラクタ
   */
  public Text() {}

  /**
   * 各変数を代入するメソッド
   * 
   * @param textId
   * @param novelId
   * @param userId
   * @param text
   * @param date
   */
  public Text(Integer textId, Integer novelId, Integer userId, String text, Timestamp date) {
    this.textId = textId;
    this.novelId = novelId;
    this.userId = userId;
    this.text = text;
    this.date = date;
  }

  /**
   * DB上の表から値を読み取るメソッド
   * 
   * @param rs
   */
  public Text(ResultSet rs) {
    try {
      this.textId = rs.getInt("text_id");
      this.novelId = rs.getInt("novel_id");
      this.userId = rs.getInt("user_id");
      this.text = rs.getString("text");
      this.date = rs.getTimestamp("date");
    } catch(SQLException se) {
      System.out.println("ResultSetからTextの変換にエラーが発生しました。");
      se.printStackTrace();
    }
  }
  /**
   * textIdがNullの時にその情報を返す
   * 
   * @return　this.textId != null
   */
  public boolean isNotNull() {
    return this.textId != null;
  }
}