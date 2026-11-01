package ExpressionTree;

// Package mengikuti folder src/ExpressionTree agar dapat diimpor dari Main.
public class ExpressionTree {
    private ExpressionNode root;

    public ExpressionTree() {
        this.root = null;
    }

    public void buildTreeFromPostfix(String postfix) {
        java.util.Stack<ExpressionNode> stack = new java.util.Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (isOperator(token)) {
                ExpressionNode node = new ExpressionNode(token);
                node.right = stack.pop();
                node.left = stack.pop();
                stack.push(node);
            } else {
                stack.push(new ExpressionNode(token));
            }
        }
        if (!stack.isEmpty()) {
            root = stack.pop();
        }
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public double evaluate() {
        return evaluateRecursive(root);
    }

    private double evaluateRecursive(ExpressionNode node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return Double.parseDouble(node.data);
        }

        double leftVal = evaluateRecursive(node.left);
        double rightVal = evaluateRecursive(node.right);

        switch (node.data) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                if (rightVal == 0)
                    throw new ArithmeticException("Pembagian dengan nol!");
                return leftVal / rightVal;
            default:
                return 0;
        }
    }

    public void inorderTraversal() {
        System.out.print("Inorder Traversal   : ");
        inorderRecursive(root);
        System.out.println();
    }

    private void inorderRecursive(ExpressionNode node) {
        if (node != null) {
            if (node.left != null || node.right != null)
                System.out.print("(");
            inorderRecursive(node.left);
            System.out.print(node.data + " ");
            inorderRecursive(node.right);
            if (node.left != null || node.right != null)
                System.out.print(")");
        }
    }

    public void postorderTraversal() {
        System.out.print("Postorder Traversal : ");
        postorderRecursive(root);
        System.out.println();
    }

    private void postorderRecursive(ExpressionNode node) {
        if (node != null) {
            postorderRecursive(node.left);
            postorderRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }
}
