package relaynovel.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import relaynovel.controller.ViewController;
import relaynovel.controller.NovelController;
import relaynovel.controller.TextController;

import relaynovel.model.Novel;
import relaynovel.model.Text;
import relaynovel.model.User;

/**
 * Novelの内容を出力するViewです．
 * ひとつのViewが20行までのテキストを表示します．
 * このViewの最初のインスタンスを作成するにはopenNovelメソッドを使用してください．
 * @author 奥野哲史 
 */
public class NovelView extends View{
  private Novel novel;
  private int page;
  private View parentView;
  private static final int TEXTS_PER_PAGE = 20;
  private LoginView lv;
  private User user;
  private Boolean writing;

  /**
   * 指定されたページと表示したいテキストを受けとり，新しいNovelListViewを作成します．
   * @param novelId:対象としているnovelId
   * @param page:このNovelViewが何ページ目か
   * @param s:出力する文字列
   * @param view:Backが入力されたときに戻るViewのインスタンス
   * @param user:ログイン中のUser
   */
  private NovelView(int novelId, int page, String s, View view, User user){
    super();
    this.novel = NovelController.getInstance().getNovels(novelId, 1).get(0);
    this.page = page;
    this.parentView = view;
    this.lv = null;
    this.user = user;
    this.writing = false;

    this.title = novel.title + "(" + novel.novelId + ")";
    this.message = s;
    this.inputDescription = "操作を選択してください\n\nB:前のページ\nN:次のページ\nE:この小説の続きを書く\nR:この小説を評価を見る/書く\nBack:戻る\nHome:ホームに戻る\n00:00行の詳細を見る";
    
  }

  /** 
   * 新しくNovelを閲覧するNovelViewを作成します．
   * @param novelId:このNovelViewが表示するNovelのIDを指定します．
   * @param parentView:ユーザーがBackを入力したときに戻るViewを指定します．通常は呼び出し元のViewを指定します．
   * @param user:ログイン中のUserを指定します．
   * @return 作成に成功した場合はNovelViewのインスタンスが，失敗した場合はnullが戻ります．
   */ 
  public static View openNovel(int novelId, View parentView, User user){
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
    if(writing == true && user != null){
      write();    
      return;
    }
    
    boolean inputIsValid;
    do {
      int inputNumber = -1;
      inputIsValid = true;
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view = null;
      switch (inputString) {
        //前の20行
        case "B":
          view = changePage(novel.novelId, page - 1, this, user);
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
        //小説編集
        case "E":
          writeText();
          break;
       //レビュー画面へ
        case "R":
          view = ReviewNovelView.openReviewNovel(novel.novelId, this, user);
          if(view == null){
            inputIsValid = false;
            System.out.println("レビューページがありません．");
          }else{
            ViewController.getInstance().switchView(view);
          }
          break;
          //小説一覧に戻る
        case "Back":
          ViewController.getInstance().switchView(parentView);
          break;
        //ホームに戻る
        case "Home":
          ViewController.getInstance().switchView(new MainMenuView());
          break;
        default:
          try{
            inputNumber = Integer.parseInt(inputString);
            if(1 <= inputNumber & inputNumber <= TEXTS_PER_PAGE){ 
              Text targetText = NovelController.getInstance().getTexts( novel.novelId , (page - 1) * TEXTS_PER_PAGE + inputNumber - 1, 1).get(0);

              view = TextDetailView.openTextDetail(novel.novelId, targetText.textId , this, user);
              //debug
              //end debug
              if(view == null){
                throw new Exception();
              }else{
                ViewController.getInstance().switchView(view);
                break;
              }
            }
          }catch (Exception e){ 
            System.out.println("無効なインプット");
            inputIsValid = false;
          }
          break;
      }      
    }while (!inputIsValid);
  }

  private static View changePage(int novelId, int page, View view, User user){
    if(page < 1){
      return null;
    }
    ArrayList<Text> texts = NovelController.getInstance().getTexts(novelId, (page - 1) * TEXTS_PER_PAGE, TEXTS_PER_PAGE);
    String s = formatTexts(texts, page);
    if(texts.size() == 0){
      if(page == 1){
        s = s + "\n本文がありません";
      }else{
        return null;
      }
    }
    return new NovelView(novelId, page, s, view, user);
  }

  private static String formatTexts(ArrayList<Text> texts, int page){
    String s = String.format("Page:%d\n行:本文", page);
    for(int i = 0; i < texts.size(); i++){
      Text t = texts.get(i);
      s = s + String.format("\n%2d:%s", i + 1, t.text);
    }
    return s;
  }

  private void writeText(){
    if(user == null){
      login();
      writing = true;
    }else{
      Scanner scanner = new Scanner(System.in);
      while(true){
        System.out.println("現在のユーザーは "+ user.name +" です．別のユーザーに変更しますか(Y/N)");
        switch(scanner.nextLine()){
          case "Y":
            login();
            writing = true;
            return;
          case "N":
            write();
            return;
        }
      }
    }
  }

  private void login(){
    lv = new LoginView(this);
    ViewController.getInstance().switchView(lv);
  }

  private void write(){
    System.out.print("文章の内容を入力してください\n> ");
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    if(!TextController.getInstance().insert(new Text(null, novel.novelId, user.userId, s, new Timestamp(System.currentTimeMillis())))){
      System.out.println("文章の投稿に失敗しました．はじめからやり直してください");
    }else{
      System.out.println("文章を投稿しました．");
    }
    System.out.print("Enterキーを押して続ける\n> ");
    scanner.nextLine();
    writing = false;
    ViewController.getInstance().switchView(NovelView.changePage(novel.novelId, page, parentView, user));
  }
}