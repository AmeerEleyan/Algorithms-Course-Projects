package huffman;

import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;
    public static final StatisticsTable[] huffmanTable = new StatisticsTable[ALPHABET_SIZE];


    public static void encoding(final File sourceFile) {
        Node root = buildHuffmanTree(buildFrequenciesOfTheBytes(sourceFile));
        assert root != null;
        getHuffmanCode(root, "");
    }

    public static void decoding(final File sourceFile) {

    }

    public static int[] buildFrequenciesOfTheBytes(final File file) {
        int[] frequencies = new int[ALPHABET_SIZE];
        byte[] bytesOfFile = getBytesFromFile(file);
        assert bytesOfFile != null;
        for (byte b : bytesOfFile) {
            frequencies[b + 128]++;
        }
        return frequencies;
    }

    private static byte[] getBytesFromFile(final File file) {
        try {
            FileInputStream fileInputStream;
            byte[] bytesOfFile = new byte[(int) file.length()];
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesOfFile);
            fileInputStream.close();
            return bytesOfFile;
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
        return null;
    }

    public static Node buildHuffmanTree(final int[] frequencies) {

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

    public static void compress(File fileSource, File fileDestination) {
//        try {
//            PrintWriter writer = new PrintWriter(file);
//            /*for (StatisticsTable statisticsTable : huffmanTable) {
//                if (statisticsTable != null && statisticsTable.getFrequency() > 0) {
//                    writer.println(statisticsTable.getHuffmanCode() + " " + statisticsTable.getFrequency());
//
//                } else writer.println(0);
//
//            }*/
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

    // Level traversal (Breadth-first search)
    public static void levelOrderTraverse(Node node) {
        if (node == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        // we add start node
        queue.add(node);

        try {
            FileOutputStream fos = new FileOutputStream("level.txt");
            PrintWriter writer = new PrintWriter("level.txt");
            //     FileStream outputFS = new FileStream()
            //iterate while queue not empty
            while (!queue.isEmpty()) {

                // dequeue and print data
                Node next = queue.remove();
                if (next.isLeaf()) {
                    fos.write(next.getBytes());
                    writer.println(next.getFrequency());
                }

                // System.out.print(next.data + " ");

                // we add children nodes if not null
                if (next.getLeftChild() != null)
                    queue.add(next.getLeftChild());

                if (next.getRightChild() != null)
                    queue.add(next.getRightChild());
            }
            fos.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
