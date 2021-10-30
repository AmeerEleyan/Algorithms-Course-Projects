package huffman;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;
    private static final StatisticsTable[] huffmanTable = new StatisticsTable[ALPHABET_SIZE];


    public static void compress(File file) {
        int[] frequencies = buildFrequenciesOfTheBytes(file);
        buildHuffmanTree(frequencies);
    }

    public static int[] buildFrequenciesOfTheBytes(final File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
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
            for (byte i = -128; i < 127; i++) { // start from -128 to the 127 because it's the range of th byte
                if (frequencies[i + 128] > 0) {
                    priorityQueue.add(new Node(i, frequencies[i + 128], null, null));
                }
            }
//            if (priorityQueue.size() == 1) {
//                priorityQueue.add(new Node((byte) '\0', 1, null, null));
//            }
            while (priorityQueue.size() > 1) {
                Node left = priorityQueue.poll();
                Node right = priorityQueue.poll();
                assert right != null;
                Node parent = new Node((byte) '\0', left.getFrequency() + right.getFrequency(), left, right);
                priorityQueue.add(parent);
            }
            assert priorityQueue.peek() != null;
            printCode(priorityQueue.peek(), "");

            System.out.println(Arrays.toString(huffmanTable));
            return priorityQueue.peek();
        }
        return null;
    }

    public static void printCode(Node node, String code) {
        if (node.isLeaf()) {
            huffmanTable[node.getBytes() + 128] = new StatisticsTable(node.getBytes(), node.getFrequency(), code);
        } else {
            printCode(node.getLeftChild(), code + "0");
            printCode(node.getRightChild(), code + "1");
        }
    }

    public static void update(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            for (int i = 0; i < huffmanTable.length; i++) {
                if (huffmanTable[i] != null && huffmanTable[i].getFrequency() > 0) writer.print(huffmanTable[i].getVariableLength());
            }
            writer.close();
        } catch (IOException exception) {
            Message.displayMessage("Warning", exception.getMessage());
        }
    }
}
