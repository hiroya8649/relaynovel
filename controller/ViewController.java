package relaynovel.controller;

import relaynovel.view.*;

public class ViewController {
  private final static ViewController instance = new ViewController();

  private ViewController() {}

  public static ViewController getInstance() { return instance; }

  public void start() {
    View view = new StartView();
    view.show();
  }

  public void switchView(View view) {
    view.show();
  }
}