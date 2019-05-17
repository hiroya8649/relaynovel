package relaynovel.controller;

import relaynovel.view.*;

public class ViewController {
  private final static ViewController instance = new ViewController();
  private View viewNow = null;
  private View viewNext = null;

  private ViewController() {}

  public static ViewController getInstance() { return instance; }

  public void run() {
    this.viewNext = new MainMenuView();
    while (this.viewNext != null) {
      this.viewNow = this.viewNext;
      this.viewNext = null;
      this.viewNow.show();
    }
  }

  public void switchView(View view) {
    this.viewNext = view;
  }
}