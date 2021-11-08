package huffman;

import java.util.HashMap;
import java.util.Map;

public class Temp {
//    // Recursive function to construct a binary tree from a given
//    // inorder and preorder sequence
//    public static int preIndex = 0;
//
//    public static Node construct(int start, int end,
//                                 byte[] preorder, Index preIndex,
//                                 Map<Byte, Integer> map) {
//        // base case
//        if (preIndex.index >= preorder.length ||  start > end) {
//            return null;
//        }
//
//        // The next element in `preorder[]` will be the root node of subtree
//        // formed by sequence represented by `inorder[start, end]`
//        Node root = new Node(preorder[preIndex.index]);
//        preIndex.index +=1;
//
//        // get the root node index in sequence `inorder[]` to determine the
//        // left and right subtree boundary
//        int index = map.get(root.getBytes());
//
//        // recursively construct the left subtree
//        root.setLeftChild(construct(start, index - 1, preorder, preIndex, map));
//
//        // recursively construct the right subtree
//        root.setRightChild(construct(index + 1, end, preorder, preIndex, map));
//
//        // return current node
//        return root;
//    }
//
//    // Construct a binary tree from inorder and preorder traversals.
//    // This function assumes that the input is valid, i.e., given
//    // inorder and preorder sequence forms a binary tree.
//    public static Node construct(byte[] inorder, byte[] preorder)
//    {
//        // create a map to efficiently find the index of any element in
//        // a given inorder sequence
//        Map<Byte, Integer> map = new HashMap<>();
//        for (int i = 0; i < inorder.length; i++) {
//            map.put(inorder[i], i);
//        }
//
//        // `pIndex` stores the index of the next unprocessed node in a preorder
//        // sequence. We start with the root node (present at 0th index).
//        Index index  =new Index();
//
//        return construct(0, inorder.length - 1, preorder, index, map);
//    }



//   static Index index = new Index();
//
//    // A recursive function to construct Full from pre[].
//    // preIndex is used to keep track of index in pre[].
//    static Node constructTreeUtil(byte[] pre, Index preIndex,
//                                  int low, int high, int size)
//    {
//
//        // Base case
//        if (preIndex.index >= size || low > high) {
//            return null;
//        }
//
//        // The first node in preorder traversal is root. So
//        // take the node at preIndex from pre[] and make it
//        // root, and increment preIndex
//        Node root = new Node(pre[preIndex.index]);
//        preIndex.index = preIndex.index + 1;
//
//        // If the current subarray has only one element, no
//        // need to recur
//        if (low == high) {
//            return root;
//        }
//
//        // Search for the first element greater than root
//        int i;
//        for (i = low; i <= high; ++i) {
//            if (pre[i] > root.getBytes()) {
//                break;
//            }
//        }
//
//        // Use the index of element found in preorder to
//        // divide preorder array in two parts. Left subtree
//        // and right subtree
//        root.setLeftChild(constructTreeUtil(pre, preIndex, preIndex.index, i - 1, size));
//
//        root.setRightChild(constructTreeUtil(pre, preIndex, i,high, size));
//
//        return root;
//    }
//
//    // The main function to construct BST from given
//    // preorder traversal. This function mainly uses
//    // constructTreeUtil()
//    static Node constructTree(byte[] pre, int size)
//    {
//        return constructTreeUtil(pre, index, 0, size - 1, size);
//    }
}
