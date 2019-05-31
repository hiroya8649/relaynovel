package relaynovel.controller;

import relaynovel.view.*;

/**
* ViewController is to handle the view running now and change view
* @author  Chang
*/
public class ViewController {
  private final static ViewController instance = new ViewController();
  private View viewNow = null;
  private View viewNext = null;

  private ViewController() {}

  /**
  * This method is for singleton.
  * @return ViewController
  */
  public static ViewController getInstance() { return instance; }

  /**
  * Start ViewController.
  */
  public void run() {
    this.viewNext = new MainMenuView();
    while (this.viewNext != null) {
      this.viewNow = this.viewNext;
      this.viewNext = null;
      this.viewNow.show();
    }
  }

  /**
  * Switch the view.
  * @param view The next view.
  */
  public void switchView(View view) {
    this.viewNext = view;
  }
}