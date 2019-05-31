/**
 * 小説に対するレビューに関する操作をする
 * 
 * @auther Yoshimasa Takahashi (cy17236)
 * @see ReviewNovel
 * @see ReviewNovelView
 */

package relaynovel.controller;

import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.ReviewNovel;

/**
 * 小説に対するレビューを操作するクラス
 */
public class ReviewNovelController {
  private final static ReviewNovelController instance = new ReviewNovelController();

  /**
   * ReviewNovelControllerのコンストラクタ
   */
  private ReviewNovelController() {}

  /**
   * ReviewNovelControllerのインスタンスを生成する
   * @return instance
   */
  public static ReviewNovelController getInstance() {
    return instance;
  }

  // 成功すればtrue
  /**
   * 小説に対する新しいレビューを書き込むメソッド
   * 成功時true,失敗時falseを返す
   * 
   * @param reviewNovel
   * @return true
   * @return false
   */
  public boolean insert(ReviewNovel reviewNovel) {
    String query = "INSERT INTO review_novel (user_id,novel_id,text,score,date) values(?,?,?,?,?)";

    try {
      PreparedStatement stm = DBAdapter.getInstance().setPreparedStatement(query);
      
      stm.setInt(1, reviewNovel.userId);
      stm.setInt(2, reviewNovel.novelId);
      stm.setString(3, reviewNovel.text);
      stm.setInt(4, reviewNovel.score);
      stm.setTimestamp(5, reviewNovel.date);
      stm.execute();
      stm.close();
      return true;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

/**
 * reviewNovelIdをDBから読み取り、該当するIDを持つレビューを取得する
 * 
 * @param reviewNovelId
 * @return　reviewText
 */
  public ReviewNovel getById(int reviewNovelId) {
    String query = "SELECT * from review_novel where review_novel_id = " + reviewNovelId;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      ReviewNovel reviewNovel;
      if (rs.next()) {
        reviewNovel = new ReviewNovel(rs);
      } else {
        reviewNovel = new ReviewNovel();
      }
      DBAdapter.getInstance().closeStatement();
      return reviewNovel;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return new ReviewNovel();
  }


//Reviews(int novelId,int start,int lines)

/**
 * ArrayList型のメソッド
 * 全レビュー内からnovelIdが一致するものを呼び出す
 * 取得を開始したい行と何行取得するかを指定できる
 * 
 * @param novelId
 * @param start
 * @param lines
 * @return reviewNovel
 */
  public ArrayList<ReviewNovel> reviews(int novelId,int start,int lines) {
    String query = "SELECT * from review_novel where novel_id = " + novelId;
    
    ArrayList<ReviewNovel> reviewNovel = new ArrayList<ReviewNovel>();
    try{
         ResultSet rs = DBAdapter.getInstance().sendQuery(query);

         int line = 0;
         
         do {
	            if(line >= start && line < start + lines){
                  reviewNovel.add(new ReviewNovel(rs));

              }
            line++;          
          }while(rs.next());
          DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }

    return reviewNovel;
  }
}