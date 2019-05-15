package relaynovel.controller;

import relaynovel.view.*;

public class ViewController {
  public ViewController() {}

  public void start() {
    View view = new StartView(this);
    view.show();
  }

  public void switchView(View view) {
    view.show();
  }
}