package relaynovel.view;

import java.util.Scanner;

import relaynovel.controller.ViewController;

public class LoginView extends View {
  public LoginView() {
    super();
    this.title = "ログイン";
    this.message = "ユーザー名とパスワードを入力してください";
  }

  public void main() {
    System.out.print("ユーザー名: ");
    Scanner scanner = new Scanner(System.in);
    String inputString = scanner.nextLine();
    System.out.print("パスワード: ");
    inputString = scanner.nextLine();

    View view = new MainMenuView();
    ViewController.getInstance().switchView(view);
  }
}