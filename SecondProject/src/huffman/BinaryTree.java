package huffman;


public class BinaryTree {

  static  Node buildUtil(byte[] in, byte[] post, int inStrt,
                   int inEnd, int postStrt, int postEnd)
    {
        // Base case
        if (inStrt > inEnd)
            return null;

        /* Pick current node from Postrder traversal using
           postIndex and decrement postIndex */
        Node node = new Node(post[postEnd]);

        /* If this node has no children then return */
        if (inStrt == inEnd)
            return node;
        int iIndex = search(in, inStrt, inEnd, node.getBytes());

        /* Using index in Inorder traversal, construct left
           and right subtress */
        node.setLeftChild(buildUtil(
                in, post, inStrt, iIndex - 1, postStrt,
                postStrt - inStrt + iIndex - 1));
        node.setRightChild (buildUtil(in, post, iIndex + 1, inEnd,
                postEnd - inEnd + iIndex,
                postEnd - 1));

        return node;
    }

    /* Function to find index of value in arr[start...end]
       The function assumes that value is postsent in in[]
     */
    static int search(byte[] arr, int strt, int end, byte value)
    {
        int i;
        for (i = strt; i <= end; i++) {
            if (arr[i] == value)
                break;
        }
        return i;
    }



//    // A recursive function to construct BST from post[].
//    // postIndex is used to keep track of index in post[].
//    static Node constructTreeUtil(byte[] postorder, int start, int end) {
//        // base case
//        if (start > end) {
//            return null;
//        }
//
//        // Construct the root node of the subtree formed by keys of the
//        // postorder sequence in range `[start, end]`
//        Node node = new Node(postorder[end]);
//
//        // search the index of the last element in the current range of postorder
//        // sequence, which is smaller than the root node's value
//        int i;
//        for (i = end; i >= start; i--) {
//            if (postorder[i] < node.getBytes()) {
//                break;
//            }
//        }
//
//        // Build the right subtree before the left subtree since the values are
//        // being read from the end of the postorder sequence.
//
//        // recursively construct the right subtree
//        node.setRightChild(constructTreeUtil(postorder, i + 1, end - 1));
//
//        // recursively construct the left subtree
//        node.setLeftChild(constructTreeUtil(postorder, start, i));
//
//        // return current node
//        return node;
//    }

//    static Node construct(byte[] inorder, int start, int end, byte[] postorder, Index index) {
//
//        if (start > end) {
//            return null;
//        }
//        Node node = new Node(postorder[(index.postindex)--]);
//        int i;
//        for (i = start; i < end; i++) {
//            if (inorder[i] == node.getBytes()) {
//                break;
//            }
//        }
//
//        node.setRightChild(construct(inorder, i + 1, end, postorder, index));
//
//        node.setLeftChild(construct(inorder, start, i - 1, postorder, index));
//
//        return node;
//    }

//    static Node constructTree(byte[] inorder, byte[] postorder, int n) {
//        Index index = new Index();
//        index.postindex = n;
//        return construct(inorder, 0, n, postorder, index);
//    }

    // The main function to construct BST from given postorder
    // traversal. This function mainly uses constructTreeUtil()
//   static Node constructTrees(byte[] post, int size) {
//        Index index = new Index();
//        index.postindex = size - 1;
//        return constructTreeUtil(post, index, post[index.postindex], Integer.MIN_VALUE, Integer.MAX_VALUE, size);
//    }
//

    public static void main(String[] args) {
        byte[] post = {-92, 96, 0, -128, 0, -111, -55, 0, -85, 82, 0, 0, 0, 72, -54, 0, 114, -46, 0, 0, 74, -107, 0,
                68, 76, 0, 0, 0, 0, 64, 84, -109, 0, 0, -91, 53, 0, 106, -43, 0, 0, 0, 48, 98, 0, 42, 80, 0, 0, 41,
                34, 0, -80, 40, 0, 0, 0, 0, 0, -1, -14, 0, 36, 65, 0, 0, 104, 87, 83, 0, 0, 0, 4, -22, 0, -120, -110,
                0, 0, -39, 37, 0, 0, 0, 0, 0, 38, 73, 0, 101, 78, 0, 0, -84, 39, 0, -96, 0, 0, 108, -44, 0, -71, 43, 0,
                0, -118, 69, 0, 88, -126, 0, 0, 0, 0, 0, 0, 9, 121, 0, -36, -20, 0, 0, -103, -74, 0, 81, -75, 0, 0, 0,
                -89, 75, 0, 123, 105, 0, 0, 26, -40, 0, -23, 77, 0, 0, 0, 0, 86, -88, 0, 16, -32, 0, 0, 54, 89, 0, -90,
                50, 0, 0, 0, -27, 19, 0, 44, -82, 0, 0, -94, -7, 0, 21, -104, 0, 0, 0, 0, 0, 79, 13, 0, -100, 33, 0, 0, -10,
                6, 0, 92, 24, 0, 0, 0, -34, 35, 0, 97, -67, 0, 0, -98, 110, 0, 18, 10, 0, 0, 0, 0, 107, 94, 0, -101, -72, 0, 0,
                -125, 91, 0, 46, 119, 0, 0, 0, 111, -122, 0, -81, 8, 0, 0, 70, -68, 0, -115, 55, 0, 0, 0, 0, 0, 0, 0, -69, 59, 0,
                67, 49, 0, 0, -79, 93, 0, 27, -119, 0, 0, 0, -106, 95, 0, 117, -60, 0, 0, -99, 12, 0, -50, -2, 0, 0, 0, 0, -62, -42,
                0, -26, 17, 0, 0, 127, -83, 0, -73, 118, 0, 0, 0, 61, 109, 0, -123, -105, 0, 0, -48, -65, -59, 0, 0, 0, 0, 0, -66, 2,
                0, 122, 126, 0, 0, -63, -4, 0, -53, -77, 0, 0, 0, -52, -18, 0, 103, -33, 0, 0, 66, -124, 0, -45, 71, 0, 0, 0, 0, -117,
                20, 0, -11, -70, 0, 0, 57, -17, 0, 23, -116, 0, 0, 0, -41, 47, 0, 11, -37, 0, 0, 5, -5, 0, -35, 29, 0, 0, 0, 0, 0, 0,
                -38, -9, 0, -47, 112, 0, 0, -58, -93, 0, 25, 58, 0, 0, 0, -19, -95, 0, -13, 99, 0, 0, 102, 22, 0, 51, 124, 0, 0, 0, 0, 30,
                -113, 0, 115, -102, 0, 0, -97, -61, 0, -21, -16, 0, 0, 0, 120, -3, 0, 63, -6, 0, 0, -114, -30, 0, 60, -51, 0, 0, 0, 0, 0, -31,
                -25, 0, 15, 125, 0, 0, 31, 45, 0, 116, 90, 0, 0, 0, -121, -8, 0, -12, 62, 0, 0, 14, 3, 0, -49, 52, 0, 0, 0, 0, 56, -29, 0,
                113, -57, 0, 0, 7, -15, 0, -86, 85, 0, 32, -64, 0, 0, 0, 0, 28, -76, 0, -24, -28, -127, 0, 0, 0, -87, -112, 0, -56, -108, 0,
                0, 100, -78, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};

        byte[]in = {-92, 0, 96, 0, -128, 0, -111, 0, -55, 0, -85, 0, 82, 0, 72, 0, -54, 0, 114, 0, -46, 0, 74, 0, -107, 0,
                68, 0, 76, 0, 64, 0, 84, 0, -109, 0, -91, 0, 53, 0, 106, 0, -43, 0, 48, 0, 98, 0, 42, 0, 80, 0, 41, 0, 34, 0, -80,
                0, 40, 0, -1, 0, -14, 0, 36, 0, 65, 0, 104, 0, 87, 0, 83, 0, 4, 0, -22, 0, -120, 0, -110, 0, -39, 0, 37, 0, 0, 0, 38,
                0, 73, 0, 101, 0, 78, 0, -84, 0, 39, 0, -96, 0, 108, 0, -44, 0, -71, 0, 43, 0, -118, 0, 69, 0, 88, 0, -126, 0, 9, 0, 121,
                0, -36, 0, -20, 0, -103, 0, -74, 0, 81, 0, -75, 0, -89, 0, 75, 0, 123, 0, 105, 0, 26, 0, -40, 0, -23, 0, 77, 0, 86, 0, -88,
                0, 16, 0, -32, 0, 54, 0, 89, 0, -90, 0, 50, 0, -27, 0, 19, 0, 44, 0, -82, 0, -94, 0, -7, 0, 21, 0, -104, 0, 79, 0, 13, 0, -100,
                0, 33, 0, -10, 0, 6, 0, 92, 0, 24, 0, -34, 0, 35, 0, 97, 0, -67, 0, -98, 0, 110, 0, 18, 0, 10, 0, 107, 0, 94, 0, -101, 0, -72, 0,
                -125, 0, 91, 0, 46, 0, 119, 0, 111, 0, -122, 0, -81, 0, 8, 0, 70, 0, -68, 0, -115, 0, 55, 0, -69, 0, 59, 0, 67, 0, 49, 0, -79, 0,
                93, 0, 27, 0, -119, 0, -106, 0, 95, 0, 117, 0, -60, 0, -99, 0, 12, 0, -50, 0, -2, 0, -62, 0, -42, 0, -26, 0, 17, 0, 127, 0, -83,
                0, -73, 0, 118, 0, 61, 0, 109, 0, -123, 0, -105, 0, -48, 0, -65, 0, -59, 0, -66, 0, 2, 0, 122, 0, 126, 0, -63, 0, -4, 0, -53, 0,
                -77, 0, -52, 0, -18, 0, 103, 0, -33, 0, 66, 0, -124, 0, -45, 0, 71, 0, -117, 0, 20, 0, -11, 0, -70, 0, 57, 0, -17, 0, 23, 0, -116,
                0, -41, 0, 47, 0, 11, 0, -37, 0, 5, 0, -5, 0, -35, 0, 29, 0, -38, 0, -9, 0, -47, 0, 112, 0, -58, 0, -93, 0, 25, 0, 58, 0, -19, 0,
                -95, 0, -13, 0, 99, 0, 102, 0, 22, 0, 51, 0, 124, 0, 30, 0, -113, 0, 115, 0, -102, 0, -97, 0, -61, 0, -21, 0, -16, 0, 120, 0, -3,
                0, 63, 0, -6, 0, -114, 0, -30, 0, 60, 0, -51, 0, -31, 0, -25, 0, 15, 0, 125, 0, 31, 0, 45, 0, 116, 0, 90, 0, -121, 0, -8, 0, -12,
                0, 62, 0, 14, 0, 3, 0, -49, 0, 52, 0, 56, 0, -29, 0, 113, 0, -57, 0, 7, 0, -15, 0, -86, 0, 85, 0, 32, 0, -64, 0, 28, 0, -76, 0,
                -24, 0, -28, 0, -127, 0, -87, 0, -112, 0, -56, 0, -108, 0, 100, 0, -78, 0, 1};

        int n= post.length;
        Node root = buildUtil(in, post,0,n-1,0,n-1);
    }
    // *******************************************************
//    // Function to build tree using given traversal
//
//
//    public void inorderTraversal(Node root) {
//        Stack<Node> nodes = new Stack<>();
//        Node current = root;
//        while (!nodes.isEmpty() || current != null) {
//            if (current != null) {
//                nodes.push(current);
//                current = current.getLeftChild();
//            } else {
//                Node node = nodes.pop();
//                // System.out.printf("%s ", node.data);
//                current = node.getRightChild();
//
//            }
//
//        }
//    }

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

class Index {
    int postindex = 0;
}
