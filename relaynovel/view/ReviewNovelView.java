package relaynovel.view;

import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Timestamp;

import relaynovel.controller.ViewController;
import relaynovel.controller.NovelController;
import relaynovel.controller.ReviewNovelController;
import relaynovel.controller.UserController;

import relaynovel.model.Novel;
import relaynovel.model.ReviewNovel;
import relaynovel.model.User;

/** 
 * 対象のNovelに対するRevieNovelwの内容を出力するViewです．
 * ひとつのReviewNovelViewが20行までのテキストを表示できます．
 * このViewの最初のインスタンスを作成するにはopenReviewNovelメソッドを使用してください．
 * @author 奥野哲史
 * */
public class ReviewNovelView extends View{
  private Novel novel;
  private int page;
  private View parentView;
  private static final int REVIEWS_PER_PAGE = 20;
  private LoginView lv;
  private User user;
  private Boolean reviewing;

  /**
   * 指定されたページと表示したいテキストを受けとり，新しいReviewNovelViewを作成します．
   * @param novelId:対象としているnovelId
   * @param page:このReviewNovelViewが何ページ目か
   * @param s:出力する文字列
   * @param view:Backが入力されたときに戻るViewのインスタンス
   * @param user:ログイン中のUser
   */
  private ReviewNovelView(int novelId, int page, String s, View view, User user){
    super();

    this.novel = NovelController.getInstance().getNovels(novelId, 1).get(0);
    this.page = page;
    this.parentView = view;
    this.lv = null;
    this.user = user;
    this.reviewing = false;


    this.title = novel.title + "のレビュー";
    this.message = s;
    this.inputDescription = "B:前のページ\nN:次のページ\nW:評価を書く\nBack:戻る\nHome:ホームに戻る";

  }
 
  /** 
   * 新しくレビューを閲覧するReviewNovelViewを作成します．
   * @param novelId:このNovelViewが表示するNovelのIDを指定します．
   * @param parentView:ユーザーがBackを入力したときに戻るViewを指定します．通常は呼び出し元のViewを指定します．
   * @param user:ログイン中のUserを指定します．
   * @return 作成に成功した場合はNovelViewのインスタンスが，失敗した場合はnullが戻ります．
   */ 
  public static View openReviewNovel(int novelId, View parentView, User user){
    return changePage(novelId, 1, parentView, user);
  };

  /**
   * ユーザーからの入力を受け付け，それに応じた処理を行います，
   */
  public void main(){
    //ユーザーの入力を処理
    Scanner scanner = new Scanner(System.in);

    if (lv != null){    
      user = lv.user;
    }
    if(reviewing == true && user != null){
      review();
      return;
    }
    
    boolean inputIsValid;
    do {      
      inputIsValid = true;
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view;
      switch (inputString) {
        //前の20行
        case "B":
        view = changePage(novel.novelId, page - 1, this, user );
        if(view == null){
          inputIsValid = false;
          System.out.println("前のページはありません");
        }else{
          ViewController.getInstance().switchView(view);
        }
          break;
        //次の20行
        case "N":
        view = changePage(novel.novelId, page + 1, this, user);
        if(view == null){
          inputIsValid = false;
          System.out.println("次のページはありません");
        }else{
          ViewController.getInstance().switchView(view);
        }
          break;
        //レビューを書く
        case "W":
          writeReview();
          //ViewController.getInstance().switchView(this);
          break;
        //小説に戻る
        case "Back":
          ViewController.getInstance().switchView(parentView);
          break;
        //ホームに戻る
        case "Home":
          ViewController.getInstance().switchView(new MainMenuView());
          break;
        default:
          System.out.println("無効なインプット");
          inputIsValid = false;
          break;
       
      }
    }while (!inputIsValid);
  }

  private static View changePage(int novelId, int page, View view, User user){
    if(page < 1){
      return null;
    }
    ArrayList<ReviewNovel> reviews = ReviewNovelController.getInstance().reviews(novelId, (page - 1) * REVIEWS_PER_PAGE + 1 , REVIEWS_PER_PAGE);
    String s = formatReviews(reviews, page);
    if(reviews.size() == 0){
      if(page == 1){
        s = s + "\n本文がありません";
      }else {
        return null;
      }
    }
    return new ReviewNovelView(novelId, page, s, view, user);
  }

  private static String formatReviews(ArrayList<ReviewNovel> reviews, int page){
    String s = String.format("Page:%d\n行:レビュー者:レビュー文", page);
    for(int i = 0; i < reviews.size(); i++){
      ReviewNovel r = reviews.get(i);
      s = s + String.format("\n%2d:%10s:%s", i + 1, UserController.getInstance().getById(reviews.get(i).userId).name, r.text);
    }
    return s;
  }

  private void writeReview(){
    if(user == null){
      login();
      reviewing = true;
    }else{
      Scanner scanner = new Scanner(System.in);
      while(true){
        System.out.println("現在のユーザーは "+ user.name +" です．別のユーザーに変更しますか(Y/N)");
        switch(scanner.nextLine()){
          case "Y":
            login();
            reviewing = true;
            return;
          case "N":
            review();
            return;
        }
      }
    }
  }

  private void login(){
    lv = new LoginView(this);
    ViewController.getInstance().switchView(lv);
  }

  private void review(){
    System.out.print("レビュー内容を入力してください\n> ");
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    if(!ReviewNovelController.getInstance().insert(new ReviewNovel(null, user.userId, novel.novelId, s, 0, new Timestamp(System.currentTimeMillis())))){
      System.out.println("レビューの投稿に失敗しました．はじめからやり直してください");
    }else{
      System.out.println("レビューを投稿しました．");
    }
    System.out.print("Enterキーを押して続ける\n> ");
    scanner.nextLine();
    reviewing = false;
    ViewController.getInstance().switchView(ReviewNovelView.changePage(novel.novelId, page, parentView, user));
    
  }
}

//NovelController.getTexts(int 小説id, int 開始行, int取得行数);
//NovelController.getRev(int 小説id, int 開始行, int取得行数);