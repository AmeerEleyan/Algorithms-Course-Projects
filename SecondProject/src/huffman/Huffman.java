package huffman;

import java.io.*;
import java.util.PriorityQueue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;
    public static final StatisticsTable[] huffmanTable = new StatisticsTable[ALPHABET_SIZE];


    public static void compress(File file) {
        buildHuffmanTree(buildFrequenciesOfTheBytes(file));
        try {
            PrintWriter writer = new PrintWriter(file);

            String binaryRepresentation = "";
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
            writer.close();
        } catch (IOException exception) {
            Message.displayMessage("Warning", exception.getMessage());
        }
    }

    public static int[] buildFrequenciesOfTheBytes(final File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int[] frequencies = new int[ALPHABET_SIZE];
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                byte[] bytes = line.getBytes();
                for(byte b: bytes)  frequencies[b+128]++;
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

}
