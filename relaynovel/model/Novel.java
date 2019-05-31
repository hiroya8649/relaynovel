/*@aouther 新田俊輔
 *新たに制作する小説を一時的に保持するモデル
 */
package relaynovel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class Novel {
  public Integer novelId = null;
  public Integer userId = null;
  public String title = null;
  public Timestamp date = null;


  /**
   * 各変数を代入するメソッド
   * 
   * @param novelId
   * @param userId
   * @param title
   * @param date
   */
  public Novel(Integer novelId, Integer userId, String title, Timestamp date) {
    this.novelId = novelId;
    this.userId = userId;
    this.title = title;
    this.date = date;
  }

  /**
   * DB上の表から値を読み取るメソッド
   * 
   * @param rs
   */
  public Novel(ResultSet rs) {
    try {
      this.novelId = rs.getInt("novel_id");
      this.userId = rs.getInt("user_id");
      this.title = rs.getString("title");
      this.date = rs.getTimestamp("date");
    } catch(SQLException se) {
      System.out.println("ResultSetからNovelの変換にエラーが発生しました。");
      se.printStackTrace();
    }
  }

  /**
   * novelIdがNullの時にその情報を返す
   * 
   * @return　this.novelId != null
   */
  public boolean isNotNull() {
    return this.novelId != null;
  }
}