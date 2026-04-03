public class StringEvaluator {
  private int lookahead;
  private InputStream input;
  
  public StringEvaluator(InputStream input) {
      this.input = input;
      consume();
  }
  
  private void consume() {
      try {
          lookahead = input.read();
      } catch (IOException e) {
          lookahead = -1;
      }
    }
}
