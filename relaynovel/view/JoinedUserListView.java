package relaynovel.view;

import java.util.ArrayList;
import java.util.Scanner;

import relaynovel.controller.ViewController;
import relaynovel.model.User;
import relaynovel.controller.NovelController;

/**
 * JoinedUserListView is the view which can view who joined this novel.
 */
public class JoinedUserListView extends View {
  /**
   * constructor.
   */
  public JoinedUserListView(int novelId) {
    super();
    String message = "";
    ArrayList<User> users = NovelController.getInstance().getUsers(novelId);
    for (int i = 0; i < users.size(); i++){
      User u = users.get(i);
      message += u.userId + " " + u.name + " " + u.birthday + " " + u.gender + "\n"; 
    }
    this.title = "参加したユーザー一覧";
    this.message = message;
    this.inputDescription = "Enter を押して小説一覧に帰る";
  }

  /**
   * Handle the user input.
   */
  public void main() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("> ");
    String inputString = scanner.nextLine();

    View view = NovelListView.openNovelList(null);
    ViewController.getInstance().switchView(view);
  }
}