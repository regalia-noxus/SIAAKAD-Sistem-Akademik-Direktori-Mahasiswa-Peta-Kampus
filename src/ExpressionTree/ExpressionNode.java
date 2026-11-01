public class ExpressionNode {
    String data;
    ExpressionNode left;
    ExpressionNode right;
  
    public ExpressionNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}