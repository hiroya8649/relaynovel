/**
 * 小説に対するレビューを一時的に保持する
 * 
 * @author  Yoshimasa Takahashi (cy17236)
 * @see reviewNovel
 */

package relaynovel.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 小説に対するレビューを一時的に保持するクラス
 * 
 */
public class ReviewNovel {

  public Integer reviewNovelId = null;
  public Integer userId = null;
  public Integer novelId = null;
  public String text = null;
  public Integer score = null;
  public Timestamp date = null;

  /**
   * ReviewNovelのコンストラクタ
   */
  public ReviewNovel() {}
  
  /**
   * 各変数を代入するメソッド
   * 
   * @param reviewNovelId
   * @param userId
   * @param novelId
   * @param text
   * @param score
   * @param date
   */
  public ReviewNovel(Integer reviewNovelId, Integer userId, Integer novelId, String text, Integer score, Timestamp date) {
    this.reviewNovelId = reviewNovelId;
    this.userId = userId;
    this.novelId = novelId;
    this.text = text;
    this.score = score;
    this.date = date;

  }

  
  /**
   * DB上の表から値を読み取るメソッド
   * 
   * @param rs
   */
  public ReviewNovel(ResultSet rs) {
    try {

        this.reviewNovelId = rs.getInt("review_novel_id");
        this.userId = rs.getInt("user_id");
        this.novelId = rs.getInt("novel_id");
        this.text = rs.getString("text");
        this.score = rs.getInt("score");
        this.date = rs.getTimestamp("date");

    } catch(SQLException se) {
      System.out.println("ResultSetからReviewNovelの変換にエラーが発生しました。");
      se.printStackTrace();
    }

  }


  /**
   * reviewNovelIdがNullの時にその情報を返す
   * 
   * @return　this.reviewNovelId != null
   */
  public boolean isNotNull() {
    return this.reviewNovelId != null;
  }
}