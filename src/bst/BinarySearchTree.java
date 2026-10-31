package bst;

public class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    // =========================================================================
    // OPERASI INSERT
    // =========================================================================

    public void insert(long key, String nama, double nilai) {
        root = insertRecursive(root, key, nama, nilai);
    }

    private BSTNode insertRecursive(BSTNode current, long key, String nama, double nilai) {
        if (current == null) {
            return new BSTNode(key, nama, nilai);
        }

        if (key < current.key) {
            current.left = insertRecursive(current.left, key, nama, nilai);
        } else if (key > current.key) {
            current.right = insertRecursive(current.right, key, nama, nilai);
        } else {
            current.nama = nama;
            current.nilai = nilai;
        }

        return current;
    }

    // =========================================================================
    // OPERASI SEARCH
    // =========================================================================

    public BSTNode search(long key) {
        return searchRecursive(root, key);
    }

    private BSTNode searchRecursive(BSTNode current, long key) {
        if (current == null || current.key == key) {
            return current;
        }

        if (key < current.key) {
            return searchRecursive(current.left, key);
        }

        return searchRecursive(current.right, key);
    }

    // =========================================================================
    // OPERASI DELETE (3 Kasus LN 09)
    // =========================================================================

    public boolean delete(long key) {
        if (search(key) == null) {
            return false;
        }
        root = deleteRecursive(root, key);
        return true;
    }

    private BSTNode deleteRecursive(BSTNode current, long key) {
        if (current == null) {
            return null;
        }

        if (key < current.key) {
            current.left = deleteRecursive(current.left, key);
        } else if (key > current.key) {
            current.right = deleteRecursive(current.right, key);
        } else {
            // Kasus 1: Node daun (tanpa anak)
            if (current.left == null && current.right == null) {
                return null;
            }

            // Kasus 2: Node dengan 1 anak
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            // Kasus 3: Node dengan 2 anak (menggunakan Inorder Successor)
            BSTNode successor = findMinNode(current.right);
            current.key = successor.key;
            current.nama = successor.nama;
            current.nilai = successor.nilai;
            current.right = deleteRecursive(current.right, successor.key);
        }

        return current;
    }

    // Mencari Inorder Successor (node terkecil pada subtree kanan)
    private BSTNode findMinNode(BSTNode current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // =========================================================================
    // OPERASI TRAVERSAL (Inorder, Preorder, Postorder)
    // =========================================================================

    // Inorder: Kiri -> Root -> Kanan (menghasilkan data terurut menaik)
    public void inorder() {
        System.out.println("\n--- Inorder Traversal (Terurut Menaik berdasarkan Key/NIM) ---");
        if (isEmpty()) {
            System.out.println("Pohon kosong.");
            return;
        }
        inorderRecursive(root);
        System.out.println("Total node: " + countNodes());
    }

    private void inorderRecursive(BSTNode current) {
        if (current != null) {
            inorderRecursive(current.left);
            System.out.println("  " + current);
            inorderRecursive(current.right);
        }
    }

    // Preorder: Root -> Kiri -> Kanan
    public void preorder() {
        System.out.println("\n--- Preorder Traversal (Root -> Kiri -> Kanan) ---");
        if (isEmpty()) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.print("Kunci: ");
        preorderRecursive(root);
        System.out.println();
    }

    private void preorderRecursive(BSTNode current) {
        if (current != null) {
            System.out.print(current.key + " ");
            preorderRecursive(current.left);
            preorderRecursive(current.right);
        }
    }

    // Postorder: Kiri -> Kanan -> Root
    public void postorder() {
        System.out.println("\n--- Postorder Traversal (Kiri -> Kanan -> Root) ---");
        if (isEmpty()) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.print("Kunci: ");
        postorderRecursive(root);
        System.out.println();
    }

    private void postorderRecursive(BSTNode current) {
        if (current != null) {
            postorderRecursive(current.left);
            postorderRecursive(current.right);
            System.out.print(current.key + " ");
        }
    }

    // =========================================================================
    // VISUALISASI TREE
    // =========================================================================

    public void printTree() {
        System.out.println("\n===== VISUALISASI STRUKTUR BINARY SEARCH TREE =====");
        if (root == null) {
            System.out.println("(Pohon masih kosong)");
            return;
        }
        printTreeRecursive(root, "", true);
    }

    private void printTreeRecursive(BSTNode node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "\\-- " : "+-- ") + node.key + " (" + node.nama + " | Nilai: " + node.nilai + ")");
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    printTreeRecursive(node.left, prefix + (isTail ? "    " : "|   "), node.right == null);
                }
                if (node.right != null) {
                    printTreeRecursive(node.right, prefix + (isTail ? "    " : "|   "), true);
                }
            }
        }
    }

    public int countNodes() {
        return countNodesRecursive(root);
    }

    private int countNodesRecursive(BSTNode current) {
        if (current == null) {
            return 0;
        }
        return 1 + countNodesRecursive(current.left) + countNodesRecursive(current.right);
    }

    public BSTNode getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }
}
