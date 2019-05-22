package relaynovel.view;

import java.util.Scanner;

import relaynovel.controller.UserController;
import relaynovel.controller.ViewController;
import relaynovel.model.User;

public class LoginView extends View {
  public LoginView() {
    super();
    this.title = "ログイン";
    this.message = "ユーザー名とパスワードを入力してください";
  }

  public void main() {
    Scanner scanner = new Scanner(System.in);
    User user;

    // Loginを試す
    int loginTryLimit = 3;
    do {
      loginTryLimit--;
      System.out.print("ユーザー名: ");
      String name = scanner.nextLine();
      System.out.print("パスワード: ");
      String password = scanner.nextLine();
      user = UserController.getInstance().login(name, password);
    } while (!user.isNotNull() && loginTryLimit > 0);

    if (user.isNotNull()) {
      // Login成功
      System.out.println(user.name + "さん、こんにちは！");
      System.out.println("Enterを押して、次の画面に移動します。");
      System.out.print("> ");
      scanner.nextLine();
      View view = new MainMenuView();
      ViewController.getInstance().switchView(view);
    } else {
      // Login失敗
      System.out.println("Login三回失敗しました");
      System.out.println("Enterを押して、メニュー画面に移動します。");
      System.out.print("> ");
      scanner.nextLine();
      View view = new MainMenuView();
      ViewController.getInstance().switchView(view);

    }

  }
}