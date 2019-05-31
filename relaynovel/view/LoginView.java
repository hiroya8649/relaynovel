package relaynovel.view;

import java.util.Scanner;

import relaynovel.controller.UserController;
import relaynovel.controller.ViewController;
import relaynovel.model.User;

/**
* RegisterView is the view to let the user register.
* @author  Chang
*/
public class LoginView extends View {
  public User user;
  private View parentView;

  /**
   * constructor
   * @param parentView The next view after login.
   */
  public LoginView(View parentView) {
    super();
    this.parentView = parentView;
    this.user = null;
    this.title = "ログイン";
    this.message = "ユーザー名とパスワードを入力してください";
  }

  /**
   * Handle the user input.
   */
  public void main() {
    Scanner scanner = new Scanner(System.in);

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
      ViewController.getInstance().switchView(parentView);
    } else {
      // Login失敗
      System.out.println("Login三回失敗しました");
      System.out.println("Enterを押して、メニュー画面に移動します。");
      System.out.print("> ");
      scanner.nextLine();
      ViewController.getInstance().switchView(new MainMenuView());
    }
  }
}