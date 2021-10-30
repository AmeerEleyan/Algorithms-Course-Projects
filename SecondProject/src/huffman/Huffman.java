package huffman;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.PriorityQueue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;

    public static int[] buildFrequenciesOfTheBytes(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            int[] frequencies = new int[ALPHABET_SIZE];
            for (int frequency : bytes) {
                frequency += (128);
                frequencies[frequency]++;
            }
            return frequencies;
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
        return null;
    }

    public static void buildHuffmanTree(File file) {
        int[] frequencies = buildFrequenciesOfTheBytes(file);
        if (frequencies != null) {
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
            for (byte i = -128; i < 127; i++) {
                if (frequencies[i + 128] > 0) priorityQueue.add(new Node(i, frequencies[i + 128], null, null));
            }
           
        }

    }
}
