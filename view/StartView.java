package relaynovel.view;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

import relaynovel.controller.UserController;
import relaynovel.controller.ViewController;
import relaynovel.model.User;

public class StartView extends View {
  public StartView() {
    super();
    this.title = "開始";
    this.message = "ログインまたはユーザー登録してください";
    this.inputDescription = "L: ログイン\nR: ユーザー登録";
  }

  public void main() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view;
      switch (inputString) {
        case "L":
            view = new LoginView();
            ViewController.getInstance().switchView(view);
          break;
        case "R":
            view = new RegisterView();
            ViewController.getInstance().switchView(view);
          break;
        default:
            System.out.println("無効なインプット");
          break;
      }
    }
  }
}