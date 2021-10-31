package huffman;

import java.io.*;
import java.nio.file.Files;
import java.util.PriorityQueue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;
    public static final StatisticsTable[] huffmanTable = new StatisticsTable[ALPHABET_SIZE];
    private static byte[] bytes;

    public static void compress(File file) {
        int[] frequencies = buildFrequenciesOfTheBytes(file);
        buildHuffmanTree(frequencies);
    }

    public static int[] buildFrequenciesOfTheBytes(final File file) {
        try {
            // Files.readAllBytes: an array of the required size cannot be allocated, for example the file is larger that 2GB
            bytes = Files.readAllBytes(file.toPath());
            int[] frequencies = new int[ALPHABET_SIZE];
            for (int frequency : bytes) {
                frequencies[frequency + 128]++;
            }
            return frequencies;
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
            printCode(priorityQueue.peek(), "");
            return priorityQueue.peek();
        }
        return null;
    }

    public static void printCode(Node node, String code) {
        if (node.isLeaf()) {
            huffmanTable[node.getBytes() + 128] = new StatisticsTable(node.getBytes(), node.getFrequency(), code, code.length());
        } else {
            printCode(node.getLeftChild(), code + "0");
            printCode(node.getRightChild(), code + "1");
        }
    }

    public static void update(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            for (StatisticsTable statisticsTable : huffmanTable) {
                if (statisticsTable != null && statisticsTable.getFrequency() > 0) {
                    writer.println(statisticsTable.getHuffmanCode() + " " + statisticsTable.getFrequency());

                } else writer.println(0);

            }
//            String binaryRepresentation = "";
//            for (int j = 0; j < bytes.length; j++) {
//                if (huffmanTable[bytes[j] + 128] != null && huffmanTable[bytes[j] + 128].getFrequency() > 0) {
//                    writer.print(huffmanTable[bytes[j] + 128].getHuffmanCode() + " ");
//                   /* binaryRepresentation += huffmanTable[bytes[j] + 128].getHuffmanCode();
//                    if (binaryRepresentation.length() >= 8) {
//                        writer.print(Integer.parseInt(binaryRepresentation, 2) + " ");
//                        binaryRepresentation = "";
//                    }*/
//                }
//
//            }
            writer.close();
        } catch (IOException exception) {
            Message.displayMessage("Warning", exception.getMessage());
        }
    }

}
