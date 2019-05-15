package relaynovel.view;

import java.util.Scanner;

import relaynovel.controller.ViewController;

public class MainMenuView extends View {
  public MainMenuView(ViewController viewController) {
    super(viewController);
    this.title = "メニュー";
    this.message = "操作を選択して下さい";
    this.inputDescription = "N: 小説一覧\nE: 終了";
  }

  public void main() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("> ");
      String inputString = scanner.nextLine();
      View view;
      switch (inputString) {
        case "N":
            view = new LoginView(this.viewController);
            this.viewController.switchView(view);
          break;
        case "E":
            System.exit(0);
          break;
        default:
            System.out.println("無効なインプット");
          break;
      }
    }
  }
}