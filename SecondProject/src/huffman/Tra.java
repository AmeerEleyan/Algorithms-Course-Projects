package huffman;

public class Tra {


    // Class containing variable that keeps a track of overall
// calculated postindex
    static class Index {
        int postindex = 0;
    }

    // A recursive function to construct BST from post[].
    // postIndex is used to keep track of index in post[].
    static Node constructTreeUtil(int post[], Index postIndex,
                                  int key, int min, int max, int size) {
        // Base case
        if (postIndex.postindex < 0)
            return null;

        Node root = null;

        // If current element of post[] is in range, then
        // only it is part of current subtree
        if (key > min && key < max) {
            // Allocate memory for root of this subtree and decrement
            // *postIndex
            root = new Node((byte) key);
            postIndex.postindex = postIndex.postindex - 1;

            if (postIndex.postindex >= 0) {
                // All nodes which are in range {key..max} will go in
                // right subtree, and first such node will be root of right
                // subtree
                root.setRightChild(constructTreeUtil(post, postIndex, post[postIndex.postindex], key, max, size));

                // Construct the subtree under root
                // All nodes which are in range {min .. key} will go in left
                // subtree, and first such node will be root of left subtree.
                root.setLeftChild(constructTreeUtil(post, postIndex, post[postIndex.postindex], min, key, size));
            }
        }
        return root;
    }

    // The main function to construct BST from given postorder
    // traversal. This function mainly uses constructTreeUtil()
    static Node constructTree(int post[], int size) {
        Index index = new Index();
        index.postindex = size - 1;
        return constructTreeUtil(post, index, post[index.postindex],
                Integer.MIN_VALUE, Integer.MAX_VALUE, size);
    }

    // Driver program to test above functions
    public static void main(String[] args) {
        int post[] = new int[]{97, 100, 102, 0, 98, 0, 99, 101, 0, 0, 0};
        int size = post.length;

        Node root = constructTree(post, size);
        String s = Integer.toBinaryString(15243);
        s = "0".repeat(32 - s.length()) + s;
        int e = Integer.parseInt(s, 2);
       // String g =

        System.out.println(e);
    }


}
