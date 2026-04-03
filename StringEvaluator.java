public class StringEvaluator {
  private int lookahead;
  private InputStream input;

  public StringEvaluator(InputStream input) {
    this.input = input;
    consume();
  }

  // read next char
  private void consume() {
    try {
      lookahead = input.read();
    } catch (IOException e) {
      lookahead = -1;
    }
  }

  // make sure that i found what i expected
  private void match(int expected) throws Exception {
    if (lookahead == expected) {
      consume();
    } else {
      throw new Exception("Parse Error");
    }
  }
}
