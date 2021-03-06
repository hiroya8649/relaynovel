package relaynovel.view;

import java.util.Scanner;

import relaynovel.controller.ViewController;

/**
 * MainMenuView is the main menu.
 */
public class MainMenuView extends View {
  /**
   * constructor.
   */
  public MainMenuView() {
    super();
    this.title = "メニュー";
    this.message = "操作を選択して下さい";
    this.inputDescription = "N: 小説一覧\nR: ユーザー登録\nE: 終了";
  }

  /**
   * Handle the user input.
   */
  public void main() {
    Scanner scanner = new Scanner(System.in);
    boolean inputIsValid;
    do {
      inputIsValid = true;
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view;
      switch (inputString) {
        case "N":
            view = NovelListView.openNovelList(null);
            ViewController.getInstance().switchView(view);
          break;
        case "R":
            view = new RegisterView();
            ViewController.getInstance().switchView(view);
          break;
        case "E":
          break;
        default:
            System.out.println("無効なインプット");
            inputIsValid = false;
          break;
      }
    } while (!inputIsValid);
  }
}