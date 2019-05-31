package relaynovel.view;

import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import relaynovel.controller.UserController;
import relaynovel.controller.ViewController;
import relaynovel.model.User;

/**
* RegisterView is the view to let the user register.
* @author  Chang
*/
public class RegisterView extends View {
  /**
   * constructor
   */
  public RegisterView() {
    super();
    this.title = "ユーザー登録";
    this.message = "資料を入力してください";
  }

  /**
   * Handle the user input.
   */
  public void main() {
    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    System.out.print("ユーザー名: ");
    String username = scanner.nextLine();
    while(UserController.getInstance().getByName(username).isNotNull()) {
      System.out.println("ユーザー名は既に使われています。");
      System.out.print("ユーザー名: ");
      username = scanner.nextLine();
    }
    System.out.print("パスワード: ");
    String password = scanner.nextLine();

    boolean birthdayIsValid = false;
    String birthdayStr;
    Date birthday = null;
    while (!birthdayIsValid) {
      System.out.print("誕生日 (例 1999-09-09): ");
      birthdayStr= scanner.nextLine();
      try {
        birthday = format.parse(birthdayStr);
        birthdayIsValid = true;
      } catch (ParseException pe) {}
    }

    String genderStr = "-1";
    while ( !genderStr.equals("0") && !genderStr.equals("1")) {
      System.out.print("性別 (1: 男性, 0: 女性): ");
      genderStr = scanner.nextLine();
    }

    User user = new User(null, username, password, birthday, Integer.parseInt(genderStr));
    UserController.getInstance().insert(user);

    View view = new LoginView(new MainMenuView());
    ViewController.getInstance().switchView(view);
  }
}