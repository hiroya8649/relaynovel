package relaynovel;

import relaynovel.controller.*;
import relaynovel.util.DBAdapter;

public class Main {
  public static void main(String[] args) {
    ViewController.getInstance().run();
    DBAdapter.getInstance().closeConnection();
  }
}