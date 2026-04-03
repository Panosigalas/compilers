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
      throw new Exception("parse error");
    }
  }
  
  // read str : they can be letters a to z or A to Z
  public String parseStr() throws Exception {
    StringBuilder sb = new StringBuilder();
    if (!Character.isLetter(lookahead)) throw new Exception("parse error");
      // if it is a letter a-z or A-Z then continue...
      while (Character.isLetter(lookahead)) {
        sb.append((char) lookahead);
        consume();
      }
    return sb.toString();
  }
}
