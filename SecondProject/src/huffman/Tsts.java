package huffman;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

public class Tsts {
    public static void main(String[] args) {

        byte[] pre = {0, 97, 0, 0, 0, 100, 102, 98, 0, 99, 101};
        char[] preL = {'N', 'L', 'N', 'N', 'N', 'L', 'L', 'L', 'N', 'L', 'N'};
        // byte huffmanByte = (byte) (int) Integer.valueOf("100101", 2);
        // System.out.println(huffmanByte);
        // System.out.println(byteToString(huffmanByte));
//        int x = 8456132;
//        String s = String.valueOf(x);
//        System.out.println(s);
//        Node d = constructTree(pre.length, pre, preL);
//        byte byteValue =-128 ;
//        byte huffmanByte = (byte) (int) Integer.valueOf("00001011", 2);
//        System.out.println(huffmanByte);
//        huffmanByte = (byte) (int) Integer.valueOf("10101110", 2);
//        System.out.println(huffmanByte);
//        huffmanByte = (byte) (int) Integer.valueOf("10001111", 2);
//        System.out.println(huffmanByte);
//        huffmanByte = (byte) (int) Integer.valueOf("11100100", 2);
//        System.out.println(huffmanByte);
        String s = "01111001";
        s = s.substring(7);
        System.out.println(s);
    }

    public static String byteToString(byte b) {
        byte[] masks = {-128, 64, 32, 16, 8, 4, 2, 1};
        StringBuilder builder = new StringBuilder();
        for (byte m : masks) {
            if ((b & m) == m) {
                builder.append('1');
            } else {
                builder.append('0');
            }
        }
        return builder.toString();
    }

    // Recursive function to perform inorder traversal on a given binary tree
    public static void inorderTraversal(Node root) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.getLeftChild());
        System.out.print(root.getBytes() + " ");
        inorderTraversal(root.getRightChild());
    }

    // Recursive function to perform postorder traversal on a given binary tree
    public static void preorderTraversal(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(root.getBytes() + " ");
        preorderTraversal(root.getLeftChild());
        preorderTraversal(root.getRightChild());
    }


    // Recursive function to construct a binary tree from a given
    // inorder and preorder sequence
//    public static Node construct(int start, int end,
//                                 int[] preorder, AtomicInteger pIndex,
//                                 Map<Integer, Integer> map) {
//        // base case
//        if (start > end) {
//            return null;
//        }
//
//        // The next element in `preorder[]` will be the root node of subtree
//        // formed by sequence represented by `inorder[start, end]`
//        Node root = new Node(preorder[pIndex.getAndIncrement()]);
//
//        // get the root node index in sequence `inorder[]` to determine the
//        // left and right subtree boundary
//        int index = map.get(root.key);
//
//        // recursively construct the left subtree
//        root.left = construct(start, index - 1, preorder, pIndex, map);
//
//        // recursively construct the right subtree
//        root.right = construct(index + 1, end, preorder, pIndex, map);
//
//        // return current node
//        return root;
//    }
//
//    // Construct a binary tree from inorder and preorder traversals.
//    // This function assumes that the input is valid, i.e., given
//    // inorder and preorder sequence forms a binary tree.
//    public static Node construct(int[] inorder, int[] preorder)
//    {
//        // create a map to efficiently find the index of any element in
//        // a given inorder sequence
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < inorder.length; i++) {
//            map.put(inorder[i], i);
//        }
//
//        // `pIndex` stores the index of the next unprocessed node in a preorder
//        // sequence. We start with the root node (present at 0th index).
//        AtomicInteger pIndex = new AtomicInteger(0);
//
//        return construct(0, inorder.length - 1, preorder, pIndex, map);
//    }

    public static Node constructTree(int n, byte pre[], char preLN[]) {

        // Code here
        Stack<Node> s = new Stack<>();
        Node root = new Node(pre[0]);
        s.push(root);
        int i = 1;
        while (i < n) {
            Node curr = s.peek();
            if (curr.getLeftChild() == null) {
                curr.setLeftChild(new Node(pre[i]));
                if (preLN[i] == 'N') {
                    s.push(curr.getLeftChild());
                }
                i++;
            } else if (curr.getRightChild() == null) {
                curr.setRightChild(new Node(pre[i]));
                if (preLN[i] == 'N') {
                    s.push(curr.getRightChild());
                }
                i++;
            } else {
                s.pop();
            }
        }
        return root;
    }
//        byte bb = 0b00000000;
//        // byte cc = 0b10000000;
//
//        byte b = (byte) (int) Integer.valueOf("10000000", 2);
//        System.out.println(bb | b);// output -> 85
    //   System.gc();


//
//        try {
//            FileOutputStream fos = new FileOutputStream("amm.txt");
//            byte x = -128;
//            fos.write(x);
//            x = 120;
//            fos.write(x);
//            x = 97;
//            fos.write(x);
//            x = 15;
//            fos.write(x);
//           /* System.out.println(x);
//            long startTime = System.currentTimeMillis();
//            BufferedReader br = new BufferedReader(new FileReader("amm.txt"));
//            while (true){
//                int line= br.read();
//                if(line==-1) break;
//                byte result = (byte)line;
//                System.out.println(~result);
//            }
//
//            long end = System.currentTimeMillis();
//            System.out.println(end-startTime);*/
//            byte []bytes = Files.readAllBytes(new File("amm.txt").toPath());
//            System.out.println(Arrays.toString(bytes));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        FileInputStream fileInputStream = null;
//        byte[] bFile = new byte[265];
//        try
//        {
//            //convert file into array of bytes
//            fileInputStream = new FileInputStream("amm.txt");
//            fileInputStream.read(bFile);
//            fileInputStream.close();
//            for (byte b : bFile) {
//                System.out.print(b+" ");
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }

}
