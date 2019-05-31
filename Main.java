import relaynovel.controller.*;
import relaynovel.util.DBAdapter;

public class Main {
  public static void main(String[] args) {
    try {
      ViewController.getInstance().run();
    } finally {
      DBAdapter.getInstance().closeConnection();
    }
  }
}