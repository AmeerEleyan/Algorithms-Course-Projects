package huffman;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;
    public static final StatisticsTable[] huffmanTable = new StatisticsTable[ALPHABET_SIZE];
    // private Node root;

    public static void encoding(final File sourceFile) {
        // root = buildHuffmanTree(buildFrequenciesOfTheBytes(sourceFile));
        // assert root != null;
        // getHuffmanCode(root, "");

//        iterativePreorder(root);
//        byte[] pre = {0, 97, 0, 0, 0, 100, 102, 98, 0, 99, 101};
//        char[] preL = {'N', 'L', 'N', 'N', 'N', 'L', 'L', 'L', 'N', 'L', 'N'};
//        Node d = constructTree(pre.length, pre, preL);


    }

    public static void decoding(final File sourceFile) {

    }

    public static int[] buildFrequenciesOfTheBytes(final File file) {
        int[] frequencies = new int[ALPHABET_SIZE];
        try {
            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            int remaining = buffer.length;

            while (true) {
                int read = fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        for (byte b : buffer) {
                            frequencies[b + 128]++;
                        }
                        remaining = buffer.length;
                    }
                } else {
                    // the end of the file was reached. If some bytes are in the buffer
                    // they are written to the last output file
                    if (remaining < buffer.length) {
                        for (byte b : buffer) {
                            frequencies[b + 128]++;
                        }
                    }
                    break;
                }
            }
            fis.close();
            return frequencies;
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
        return null;
    }


    private static Node buildHuffmanTree(final int[] frequencies) {

        if (frequencies != null) {

            PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < ALPHABET_SIZE; i++) { // start from -128 to the 127 because it's the range of th byte
                if (frequencies[i] > 0) {
                    priorityQueue.add(new Node((byte) (i - 128), frequencies[i], null, null));
                }
            }

            while (priorityQueue.size() > 1) {
                Node left = priorityQueue.poll();
                Node right = priorityQueue.poll();
                assert right != null;
                Node parent = new Node((byte) '\0', left.getFrequency() + right.getFrequency(), left, right);
                priorityQueue.add(parent);
            }
            assert priorityQueue.peek() != null;
            return priorityQueue.peek();
        }
        return null;
    }

    public static void getHuffmanCode(Node node, String code) {
        if (node.isLeaf()) {
            huffmanTable[node.getBytes() + 128] = new StatisticsTable(node.getBytes(), node.getFrequency(), code, code.length());
        } else {
            getHuffmanCode(node.getLeftChild(), code + "0");
            getHuffmanCode(node.getRightChild(), code + "1");
        }
    }

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


    //O(N)
    private static void iterativePreorder(Node node) {

        try {
            // Base Case
            if (node == null) {
                return;
            }

            // Create an empty stack and push root to it
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(node);
            PrintWriter writer = new PrintWriter("newPreorder.txt");

        /* Pop all items one by one. Do following for every popped item
         a) print it
         b) push its right child
         c) push its left child
         Note that right child is pushed first so that left is processed first */
            while (!nodeStack.empty()) {

                // Pop the top item from stack and print it
                Node current = nodeStack.peek();
                //  writer.println(current.getBytes());
                nodeStack.pop();

                // Push right and left children of the popped node to stack
                if (current.getRightChild() != null) {
                    nodeStack.push(current.getRightChild());
                }
                if (current.getLeftChild() != null) {
                    nodeStack.push(current.getLeftChild());
                }
            }
            writer.close();
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }

    }

    public static void printToFile(final File sourceFile, final File destinationFile) {
        int indexOfDot = sourceFile.getName().lastIndexOf('.');
        String fileExtension = sourceFile.getName().substring(indexOfDot);
        byte[] fileExtensionBytes = fileExtension.getBytes();
        try {

            // ******* print the extension to the destination file *********************
            FileOutputStream fos = new FileOutputStream(destinationFile);

            // print the file extension
            fos.write(fileExtensionBytes, 0, fileExtensionBytes.length);
            fos.write('\n');


            Node root = buildHuffmanTree(buildFrequenciesOfTheBytes(sourceFile));
            assert root != null;
            getHuffmanCode(root, "");

            // ******** print the header of the file using PreOrder traversal for the huffman tree iteratively *******

            // Create an empty stack and push root to it
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(root);

            /* Pop all items one by one. Do following for every popped item
             a) print it
             b) push its right child
             c) push its left child
             Note that right child is pushed first so that left is processed first */
            while (!nodeStack.empty()) {

                // Pop the top item from stack and print it
                Node current = nodeStack.peek();
                fos.write(current.getBytes());
                fos.write((current.isLeaf()) ? 'y' : 'n');
                nodeStack.pop();

                // Push right and left children of the popped node to stack
                if (current.getRightChild() != null) {
                    nodeStack.push(current.getRightChild());
                }
                if (current.getLeftChild() != null) {
                    nodeStack.push(current.getLeftChild());
                }
            }
            // ********** end of the header ********************
            fos.write('\n');

            // ************** encode the file and print to the output file ************

            FileInputStream fis = new FileInputStream(sourceFile);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            int remaining = buffer.length;

            byte[] huffman = new byte[8];
            int index = 0; // used for the above huffman array
            while (true) {
                int read = fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        String remainingBits = "";
                        for (byte b : buffer) {
                            String huffmanBits = remainingBits + huffmanTable[b + 128].getHuffmanCode();
                            if (huffmanBits.length() >= 8) {
                                remainingBits = huffmanBits.substring(huffmanBits.length() - 8);
                                huffmanBits = huffmanBits.substring(0, 9);
                                byte huffmanByte = (byte) (int) Integer.valueOf(huffmanBits, 2);
                                huffman[index++] = huffmanByte;
                            }

                        }
                        remaining = buffer.length;
                    }
                } else {
                    // the end of the file was reached. If some bytes are in the buffer
                    // they are written to the last output file
                    if (remaining < buffer.length) {
                        for (byte b : buffer) {

                        }
                    }
                    break;
                }
            }


            fis.close();
            fos.close();
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }

    }

    public static void compress(final File fileSource, final File destinationFile) {

        try {
            int indexOfDot = fileSource.getName().lastIndexOf('.');
            String fileExtension = fileSource.getName().substring(indexOfDot);
            printHeader(destinationFile, fileExtension);
            PrintWriter writer = new PrintWriter(destinationFile);
            //  byte[] bytes = getBytesFromFile(fileSource);
            FileOutputStream fos = new FileOutputStream(destinationFile);
            fos.write((byte) 12);
        } catch (IOException e) {
            Message.displayMessage("Warinig", e.getMessage());
        }

        //writer.append()
            /*for (StatisticsTable statisticsTable : huffmanTable) {
                if (statisticsTable != null && statisticsTable.getFrequency() > 0) {
                    writer.println(statisticsTable.getHuffmanCode() + " " + statisticsTable.getFrequency());

                } else writer.println(0);

            }*/
//            String binaryRepresentation = "";
//            for (int j = 0; j < bytes.length; j++) {
//                if (huffmanTable[bytes[j] + 128] != null && huffmanTable[bytes[j] + 128].getFrequency() > 0) {
//                    writer.print(huffmanTable[bytes[j] + 128].getHuffmanCode());
//                   /* binaryRepresentation += huffmanTable[bytes[j] + 128].getHuffmanCode();
//                    if (binaryRepresentation.length() >= 8) {
//                        writer.print(Integer.parseInt(binaryRepresentation, 2) + " ");
//                        binaryRepresentation = "";
//                    }*/
//                }
//
//            }
//            writer.close();
//        } catch (IOException exception) {
//            Message.displayMessage("Warning", exception.getMessage());
//        }

    }


    public static void decompress(final File sourceFile, final File destinationFile) {

    }

    private static void printHeader(final File destinationFile, final String fileExtension) {

//        // Base Case
//        if (root == null) {
//            return;
//        }
//
//        try {
//
//            PrintWriter writer = new PrintWriter(destinationFile);
//            writer.println(fileExtension);
//
//            // Create an empty stack and push root to it
//            Stack<Node> nodeStack = new Stack<Node>();
//            nodeStack.push(root);
//
//        /* Pop all items one by one. Do following for every popped item
//         a) print it
//         b) push its right child
//         c) push its left child
//         Note that right child is pushed first so that left is processed first */
//            while (!nodeStack.empty()) {
//
//                // Pop the top item from stack and print it
//                Node current = nodeStack.peek();
//                // y: is leaf
//                // n: not leaf
//                writer.print(current.getBytes() + "" + ((current.getBytes() != '\0') ? 'y' : 'n'));
//                nodeStack.pop();
//
//                // Push right and left children of the popped node to stack
//                if (current.getRightChild() != null) {
//                    nodeStack.push(current.getRightChild());
//                }
//                if (current.getLeftChild() != null) {
//                    nodeStack.push(current.getLeftChild());
//                }
//            }
//            writer.print('`');// end the header
//            writer.close();
//        } catch (IOException e) {
//            Message.displayMessage("Warning", e.getMessage());
//        }
    }


}
