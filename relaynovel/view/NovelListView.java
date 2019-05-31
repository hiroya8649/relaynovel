package relaynovel.view;

import java.sql.Timestamp;

import java.util.Scanner;
import java.util.ArrayList;
import relaynovel.controller.ViewController;
import relaynovel.controller.NovelController;
import relaynovel.model.Novel;
import relaynovel.model.User;

/**
 * 小説の一覧を表示するViewです．
 * NovelListViewは小説を10作品ずつ表示します．
 * このViewの最初のインスタンスを作成するにはopenNovelListメソッドを使用してください．
 * @author 奥野哲史
 */

public class NovelListView extends View{
  private static final int NOVELS_PER_PAGE = 10;
  private int page;
  private LoginView lv;
  private User user;
  private Boolean registering;

  /**
   * 指定されたページと表示したいテキストを受けとり，新しいNovelListViewを作成します．
   * @param page:
   * @param s
   */
  public NovelListView(int page, String s, User user){
    super();
    this.page = page;
    this.lv = null;
    this.user = user;
    this.registering = false;

    this.title = "小説一覧";
    this.message = s;
    this.inputDescription = "B:前の10件\nN:次の10件\nG:新規作成\nU:とある小説の参加ユーザー一覧\n00: 小説を開く\nHome:メニューに戻る";

  }

  /**
   * 新しいNovelListVistViewを生成します．
   * @return 作成に成功した場合はNovelListViewのインスタンスが，失敗した場合はnullが戻ります．
   */
  public static View openNovelList(User user){
    return changePage(1, user);
  };
 
  public void main(){
    //debug
    //end debug
    Scanner scanner = new Scanner(System.in);
    boolean inputIsValid;

    if (lv != null){    
      user = lv.user;
    }
    if(registering == true && user != null){
      register();
      return;
    }

    do {    
      Integer inputNumber = null;
      inputIsValid = true;
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view;
      switch (inputString) {
        case "B":
          view = changePage(page - 1, user);
          if(view == null){
            inputIsValid = false;
            System.out.println("前のページはありません");
          }else{
            ViewController.getInstance().switchView(view);
          }
          break;
        case "N":
          view = changePage(page + 1, user);
          if(view == null){
            inputIsValid = false;
            System.out.println("次のページはありません");
          }else{
            ViewController.getInstance().switchView(view);
          }
          break;
        case "G":
          registerNovel();
          break;
        case "U":
          System.out.print("小説IDを入力してください\n> ");
          String input = scanner.nextLine();
          ViewController.getInstance().switchView(new JoinedUserListView(Integer.parseInt(input)));
          break;
        case "Home":
          ViewController.getInstance().switchView(new MainMenuView());
          break;
        default:
          try{
            inputNumber = Integer.parseInt(inputString);
            if(inputNumber < NOVELS_PER_PAGE * (page - 1) + 1 || NOVELS_PER_PAGE * (page - 1) + NOVELS_PER_PAGE < inputNumber){
              throw new Exception();
            }
            view = NovelView.openNovel(inputNumber, this, user);
            if(view == null){
              throw new Exception();
            }
            ViewController.getInstance().switchView(view);
            break;
          }catch(Exception e){
            System.out.println("無効なインプット");
            inputIsValid = false;
            break;
          }
      }
    }while (!inputIsValid);
  }

  private static View changePage(int page, User user){
    if(page < 1){
      return null;
    }
    ArrayList<Novel> novels = NovelController.getInstance().getNovels((page - 1) * NOVELS_PER_PAGE + 1, NOVELS_PER_PAGE);
    if(novels.size() == 0){
      return null;
    }
    String s = formatNovelTitles(novels, page);
    return new NovelListView(page, s, user);
  }

  private static String formatNovelTitles(ArrayList<Novel>novels, int page){
    Integer maxId = 0;
    for (int i = 0; i < novels.size(); i++){
      if(maxId < novels.get(i).novelId){
        maxId = novels.get(i).novelId;
      }
    }
    int digits = maxId.toString().length();
    
    String s = String.format("Page:%d\nID" + repeat(" ", digits - 2) + ":小説タイトル", page);
    for(Novel n: novels){
      s = s + String.format("\n%" + digits + "d:%s", n.novelId, n.title);
    }
    return s;
 }

  private static String repeat(String s, int rep){
    String o = "";
    for(int i = 0; i < rep; i++){
      o = o + s;
    }
    return o;
  }

  private void registerNovel(){
    if(user == null){
      login();
      registering = true;
    }else{
      Scanner scanner = new Scanner(System.in);
      while(true){
        System.out.println("現在のユーザーは "+ user.name +" です．別のユーザーに変更しますか(Y/N)");
        switch(scanner.nextLine()){
          case "Y":
            login();
            registering = true;
            return;
          case "N":
            register();
            return;
        }
      }
    }
  }

  private void login(){
    lv = new LoginView(this);
    ViewController.getInstance().switchView(lv);
  }

  private void register(){
    System.out.print("小説タイトルを入力してください\n> ");
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    if(!NovelController.getInstance().insert(new Novel(null, user.userId, s, new Timestamp(System.currentTimeMillis())))){
      System.out.println("小説の作成に失敗しました．はじめからやり直してください");
    }else{
      System.out.println("小説を作成しました．");
    }
    System.out.print("Enterキーを押して続ける\n> ");
    scanner.nextLine();
    registering = false;
    System.out.println(page);
    ViewController.getInstance().switchView(NovelListView.changePage(page, user));

  }
}