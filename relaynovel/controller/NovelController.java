/*@aouther 新田俊輔
 *Novelと連携して、リレー小説の新規作成を行うクラス
 *主に、具体的な動作の動きを行っている
 */
package relaynovel.controller;

import java.sql.*;
import java.util.ArrayList;

import relaynovel.util.DBAdapter;
import relaynovel.model.Novel;
import relaynovel.model.Text;
import relaynovel.model.User;


public class NovelController {
  private final static NovelController instance = new NovelController();

  private NovelController() {}

  public static NovelController getInstance() {
    return instance;
  }

  /*
   *insert:入力された情報を持った小説の制作を行う
   *       user_id、小説タイトル、制作した日付を引数とする
   */
  public boolean insert(Novel novel) {
    String query = "INSERT INTO novel (user_id, title, date) values(?,?,?)";
    try {
      PreparedStatement stm = DBAdapter.getInstance().setPreparedStatement(query);
      stm.setInt(1, novel.userId);
      stm.setString(2, novel.title);
      stm.setTimestamp(3, novel.date);
      boolean result = stm.execute();
      stm.close();
      return true;
    } catch (SQLException se) {
      se.printStackTrace();
    }
    return false;
  }

  /*
   *getAll:データベースから、全小説のデータを読み込む
   */
  public ArrayList<Novel> getAll() {
    ArrayList<Novel> novels = new ArrayList<Novel>();
    try {
      ResultSet rs = DBAdapter.getInstance().getAll("novel");
      while(rs.next()) {
        novels.add(new Novel(rs));
      }
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return novels;
  }

  /*
   *getTexts:データベースから要求された数の文を持ってくる
   *          novel_idとどの文からいくつの文がほしいのかを引数とする
   *          startがほしい文の最初の一文、linesがほしい文の数
   */
  public ArrayList<Text> getTexts(int novelId, int start, int lines) {
    System.out.println(start);
    ArrayList<Text> texts = new ArrayList<Text>();
    String query = "SELECT * from text where novel_id = " + novelId;
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
      int line = 0;
      while(rs.next()){
	      if(line >= start && line < start + lines) {
	        texts.add(new Text(rs));
                System.out.println("a");	
	      }
      line++;
      }
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return texts;
  }

  /*
   *getNovels:データベースから要求された数の小説を持ってくる
   *          どの小説からいくつの小説がほしいのかを引数とする
   *          startがほしい文の最初の小説、linesがほしい小説の数
   * 
   */
  public ArrayList<Novel> getNovels(int startId, int lines) {
    ArrayList<Novel> novels = new ArrayList<Novel>();
    try {
      ResultSet rs = DBAdapter.getInstance().getAll("novel");
      int line = 0;
      do {
	      if(line >= startId && line < startId + lines) {
	        novels.add(new Novel(rs));	
	      }
	    line++;
      }while(rs.next());
      DBAdapter.getInstance().closeStatement();
    }
    catch (SQLException se) {
      se.printStackTrace();
    }
    return novels;
   }

   /**
    * Get the user list who join a specified novel.
    * @param novelId
    * @return ArrayList<User>
    */
  public ArrayList<User> getUsers(int novelId) {
    ArrayList<User> users = new ArrayList<User>();
    String query = "SELECT u.user_id, u.name, u.password, u.birthday, u.gender from user u, review_text rt, review_novel rn, text t, novel n where ((rt.text_id=t.text_id and t.novel_id=n.novel_id) or rn.novel_id=n.novel_id or u.user_id=n.user_id) and (u.user_id=n.user_id or u.user_id=t.user_id or u.user_id=rn.user_id or u.user_id=rt.user_id) and n.novel_id = " + novelId + " group by u.user_id";
    try {
      ResultSet rs = DBAdapter.getInstance().sendQuery(query);
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