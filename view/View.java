package relaynovel.view;

import relaynovel.controller.ViewController;

public abstract class View {
  protected String title = "";
  protected String message = "";
  protected String inputDescription = "";

  protected ViewController viewController;

  public View(ViewController viewController) {
    this.viewController = viewController;
  }

  public void show() {
    this.clearScreen();
    System.out.println(title);
    System.out.println("===================");
    System.out.println("\n" + message + "\n");
    System.out.println("===================");
    if (inputDescription != "") {
      System.out.println("\n" + inputDescription);
    }
    main();
  }

  public abstract void main();

  private void clearScreen() {
    for (int i = 0; i < 50; i++) {
      System.out.println("");
    }
  }
}