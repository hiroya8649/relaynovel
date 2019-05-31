package relaynovel.view;

import relaynovel.controller.ViewController;

/**
* View is the super class for all view, handle the first render when view appears.
* @author  Chang
*/
public abstract class View {
  protected String title = "";
  protected String message = "";
  protected String inputDescription = "";

  /**
  * constructor
  */
  public View() {}

  /**
  * Render the view.
  */
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

  /**
  * The main part to handle user input, will be implement by each class.
  */
  public abstract void main();

  /**
  * Clear screen by a lot of changing line.
  */
  private void clearScreen(){
    String s = null;
    for (int i = 0; i < 100; i++) {
      s = s + "\n";
    }
    System.out.printf("%s",s);
  }
}