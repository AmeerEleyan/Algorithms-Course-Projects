package huffman;

import java.util.Arrays;

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


        byte[] x = {-92, 0, 96, 0, -128, 0, -111, 0, -55, 0, -85, 0, 82, 0, 72, 0, -54, 0, 114, 0, -46, 0, 74, 0, -107, 0, 68, 0, 76, 0, 64, 0, 84, 0, -109, 0, -91, 0, 53, 0, 106, 0, -43, 0, 48, 0, 98, 0, 42, 0, 80, 0, 41, 0, 34, 0, -80, 0, 40, 0, -1, 0, -14, 0, 36, 0, 65, 0, 104, 0, 87, 0, 83, 0, 4, 0, -22, 0, -120, 0, -110, 0, -39, 0, 37, 0, 0, 0, 38, 0, 73, 0, 101, 0, 78, 0, -84, 0, 39, 0, -96, 0, 108, 0, -44, 0, -71, 0, 43, 0, -118, 0, 69, 0, 88, 0, -126, 0, 9, 0, 121, 0, -36, 0, -20, 0, -103, 0, -74, 0, 81, 0, -75, 0, -89, 0, 75, 0, 123, 0, 105, 0, 26, 0, -40, 0, -23, 0, 77, 0, 86, 0, -88, 0, 16, 0, -32, 0, 54, 0, 89, 0, -90, 0, 50, 0, -27, 0, 19, 0, 44, 0, -82, 0, -94, 0, -7, 0, 21, 0, -104, 0, 79, 0, 13, 0, -100, 0, 33, 0, -10, 0, 6, 0, 92, 0, 24, 0, -34, 0, 35, 0, 97, 0, -67, 0, -98, 0, 110, 0, 18, 0, 10, 0, 107, 0, 94, 0, -101, 0, -72, 0, -125, 0, 91, 0, 46, 0, 119, 0, 111, 0, -122, 0, -81, 0, 8, 0, 70, 0, -68, 0, -115, 0, 55, 0, -69, 0, 59, 0, 67, 0, 49, 0, -79, 0, 93, 0, 27, 0, -119, 0, -106, 0, 95, 0, 117, 0, -60, 0, -99, 0, 12, 0, -50, 0, -2, 0, -62, 0, -42, 0, -26, 0, 17, 0, 127, 0, -83, 0, -73, 0, 118, 0, 61, 0, 109, 0, -123, 0, -105, 0, -48, 0, -65, 0, -59, 0, -66, 0, 2, 0, 122, 0, 126, 0, -63, 0, -4, 0, -53, 0, -77, 0, -52, 0, -18, 0, 103, 0, -33, 0, 66, 0, -124, 0, -45, 0, 71, 0, -117, 0, 20, 0, -11, 0, -70, 0, 57, 0, -17, 0, 23, 0, -116, 0, -41, 0, 47, 0, 11, 0, -37, 0, 5, 0, -5, 0, -35, 0, 29, 0, -38, 0, -9, 0, -47, 0, 112, 0, -58, 0, -93, 0, 25, 0, 58, 0, -19, 0, -95, 0, -13, 0, 99, 0, 102, 0, 22, 0, 51, 0, 124, 0, 30, 0, -113, 0, 115, 0, -102, 0, -97, 0, -61, 0, -21, 0, -16, 0, 120, 0, -3, 0, 63, 0, -6, 0, -114, 0, -30, 0, 60, 0, -51, 0, -31, 0, -25, 0, 15, 0, 125, 0, 31, 0, 45, 0, 116, 0, 90, 0, -121, 0, -8, 0, -12, 0, 62, 0, 14, 0, 3, 0, -49, 0, 52, 0, 56, 0, -29, 0, 113, 0, -57, 0, 7, 0, -15, 0, -86, 0, 85, 0, 32, 0, -64, 0, 28, 0, -76, 0, -24, 0, -28, 0, -127, 0, -87, 0, -112, 0, -56, 0, -108, 0, 100, 0, -78, 0, 1};
        System.out.println(x.length);
        int []frequency = new int[256];
        for (int i=0;i<256;i++){
            frequency[x[i]+128]++;
        }
        int count =0;
        for(int i=0;i<256;i++){
            if(frequency[i]>0) count++;
        }
        System.out.println(count);

    }


}
