package huffman;


import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BinaryTree {

    // Function to build tree using given traversal


    public void inorderTraversal(Node root) {
        Stack<Node> nodes = new Stack<>();
        Node current = root;
        while (!nodes.isEmpty() || current != null) {
            if (current != null) {
                nodes.push(current);
                current = current.getLeftChild();
            } else {
                Node node = nodes.pop();
                // System.out.printf("%s ", node.data);
                current = node.getRightChild();

            }

        }
    }

//    // Recursive function to perform inorder traversal on a given binary tree
//    public static void inorderTraversal(Node root) {
//        if (root == null) {
//            return;
//        }
//
//        inorderTraversal(root.getLeftChild());
//        System.out.print(root.getBytes() + ", ");
//        inorderTraversal(root.getRightChild());
//    }
//
//    // Recursive function to perform postorder traversal on a given binary tree
//    public static void preorderTraversal(Node root) {
//        if (root == null) {
//            return;
//        }
//
//        System.out.print(root.getBytes() + ", ");
//        preorderTraversal(root.getLeftChild());
//        preorderTraversal(root.getRightChild());
//    }

}

