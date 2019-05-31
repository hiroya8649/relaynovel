/**
 * 小説内の一文に対するレビューに関する操作をする
 * 
 * @author Yoshimasa Takahashi (cy17236)
 * @see ReviewText
 * @see TextDetailView
 */

package relaynovel.controller;

import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.ReviewText;


/**
 * 小説内の一文に対するレビューを操作するクラス
 */
public class ReviewTextController {

  private final static ReviewTextController instance = new ReviewTextController();

  /**
   * ReviewTextControllerのコンストラクタ
   */
  private ReviewTextController() {}

  /**
   * ReviewTextControllerのインスタンスを生成する
   * @return instance
   */
  public static ReviewTextController getInstance() {
    return instance;
  }

  // 成功すればtrue
  /**
   * 小説内の一文に対する新しいレビューを書き込むメソッド
   * 成功時true,失敗時falseを返す
   * 
   * @param reviewText
   * @return true
   * @return false
   */
  public boolean insert(ReviewText reviewText) {
    String query = "INSERT INTO review_text (user_id,text_id,text,score,date) values(?,?,?,?,?)";
    try {
      PreparedStatement stm = DBAdapter.getInstance().setPreparedStatement(query);
     
      stm.setInt(1, reviewText.userId);
      stm.setInt(2, reviewText.textId);
      stm.setString(3, reviewText.text);
      stm.setInt(4, reviewText.score);
      stm.setTimestamp(5, reviewText.date);
      boolean result = stm.execute();
      stm.close();
      return true;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

  /**
   * reviewTextIdをDBから読み取り、該当するIDを持つレビューを取得する
   * 
   * @param reviewTextId
   * @return reviewText
   */
  public ReviewText getById(int reviewTextId) {
    String query = "SELECT * from review_text where review_text = " + reviewTextId;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      ReviewText reviewText;
      if (rs.next()) {
        reviewText = new ReviewText(rs);
      } else {
        reviewText = new ReviewText();
      }
      DBAdapter.getInstance().closeStatement();
      return reviewText;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return new ReviewText();
  }


/**
 * ArrayList型のメソッド
 * 全レビュー内からtextIdが一致するものを呼び出す
 * 取得を開始したい行と何行取得するかを指定できる
 * 
 * @param textId
 * @param start
 * @param lines
 * @return reviewText
 */
  public ArrayList<ReviewText> reviews(int textId,int start,int lines) {
    ArrayList<ReviewText> reviewText = new ArrayList<ReviewText>();
    String query = "SELECT * from review_text where text_id = " + textId;
	
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
	  int line = 0;
      do {
      	if(line >= start && line < start + lines){
            reviewText.add(new ReviewText(rs));
	      }
	      line++;
      }while(rs.next());
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return reviewText;
  }
}