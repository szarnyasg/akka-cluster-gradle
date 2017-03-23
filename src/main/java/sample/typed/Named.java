package sample.typed;

import java.util.Random;

public class Named implements HasName {
  private int id = new Random().nextInt(1024);

  @Override
  public String name() {
    return "name-" + id;
  }
}
