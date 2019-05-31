/**
 * 小説内の一文に対するレビューを一時的に保持する
 * 
 * @author Yoshimasa Takahashi (cy17236)
 * @see ReviewtextController
 */

package relaynovel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * 小説内の一文に対するレビューを一時的に保持するクラス
 */
public class ReviewText {
  public Integer reviewTextId = null;
  public Integer userId = null;
  public Integer textId = null;
  public String text = null;
  public Integer score = null;
  public Timestamp date = null;

  /**
   * ReviewTextのコンストラクタ
   */
  public ReviewText() {}

  /**
   * 各変数を代入するメソッド
   * 
   * @param reviewTextId
   * @param userId
   * @param textId
   * @param text
   * @param score
   * @param date
   */
  public ReviewText(Integer reviewTextId, Integer userId, Integer textId, String text, Integer score, Timestamp date) {
    this.reviewTextId = reviewTextId;
    this.userId = userId;
    this.textId = textId;
    this.text = text;
    this.score = score;
    this.date = date;
  }


  /**
   * DB上の表から値を読み取るメソッド
   * 
   * @param rs
   */

  public ReviewText(ResultSet rs) {
    try {
      this.reviewTextId = rs.getInt("review_text_id");
      this.userId = rs.getInt("user_id");
      this.textId = rs.getInt("text_id");
      this.text = rs.getString("text");
      this.score = rs.getInt("score");
      this.date = rs.getTimestamp("date");

    } catch(SQLException se) {
      System.out.println("ResultSetからReviewTextの変換にエラーが発生しました。");
      se.printStackTrace();
    }

  }

  /**
   * reviewTextIdがNullの時にその情報を返す
   * 
   * @return　this.reviewTextId != null
   */
  public boolean isNotNull() {
    return this.reviewTextId != null;
  }
}