/**
 * @author: Ameer Eleyan
 * ID: 1191076
 * At: 11/5/2021   10:30 PM
 */
package huffman;

import java.io.*;
import java.util.PriorityQueue;

public class HuffmanCompress {
    private final int ALPHABET_SIZE = 256;
    private StatisticsTable[] huffmanTable;
    private Node root;
    private String fullHeaderAsString = "";
    private short numberOfNodes = 0;


    public void compress(final File sourceFile) {
        root = buildHuffmanTree(buildFrequenciesOfTheBytes(sourceFile));
        assert root != null;
        huffmanTable = new StatisticsTable[ALPHABET_SIZE];
        getHuffmanCode(root, "");
        printToFile(sourceFile);

    }


    public StatisticsTable[] getHuffmanTable() {
        return this.huffmanTable;
    }

    public String getFullHeaderAsString() {
        return this.fullHeaderAsString;
    }

    private int[] buildFrequenciesOfTheBytes(final File sourceFile) {
        int[] frequencies = new int[ALPHABET_SIZE];
        try {
            FileInputStream fis = new FileInputStream(sourceFile);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            while (true) {
                int read = fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        for (byte b : buffer) {
                            frequencies[b + 128]++;
                        }
                        remaining = (short) buffer.length;
                    }
                } else {
                    // the end of the sourceFile was reached. If some bytes are in the buffer

                    for (int i = 0; i < buffer.length - remaining; i++) {
                        frequencies[buffer[i] + 128]++;
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


    private Node buildHuffmanTree(final int[] frequencies) {

        if (frequencies != null) {

            PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < ALPHABET_SIZE; i++) { // start from -128 to the 127 because it's the range of th byte
                if (frequencies[i] > 0) {
                    priorityQueue.add(new Node((byte) (i - 128), frequencies[i], null, null));
                    numberOfNodes++;
                }
            }

            while (priorityQueue.size() > 1) { // O(nlogn)
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

    // build huffman code for each byte recursive
    private void getHuffmanCode(Node node, String code) {
        if (node.isLeaf()) {
            huffmanTable[node.getBytes() + 128] = new StatisticsTable(node.getBytes(), node.getFrequency(), code, (byte) code.length());
        } else {
            getHuffmanCode(node.getLeftChild(), code + "0");
            getHuffmanCode(node.getRightChild(), code + "1");
        }
    }


    private void printToFile(final File sourceFile) {

        byte indexOfDot = (byte) sourceFile.getAbsolutePath().lastIndexOf('.');
        String newFilePath = sourceFile.getAbsolutePath().substring(0, indexOfDot + 1) + "huf";
        String fileExtension = sourceFile.getAbsolutePath().substring(indexOfDot + 1);
        byte[] fileExtensionBytes = fileExtension.getBytes();
        int lengthOfFile = (int) sourceFile.length();
        try {

            // ******* print the extension to the destination file *********************
            FileOutputStream fos = new FileOutputStream(newFilePath);

            // print the file length in bytes ( 4 bytes)
            String l = Integer.toBinaryString(lengthOfFile);
            l = "0".repeat(32 - l.length()) + l;
            byte[] lengthInBytes = Utility.getFileLengthAsBytes(l);
            fos.write(lengthInBytes, 0, 4);


            // print the file extension
            fos.write(fileExtensionBytes, 0, fileExtensionBytes.length);
            fos.write('\n');


            // new
            byte[][] header = this.getHeader(huffmanTable, numberOfNodes);

            this.fullHeaderAsString = Utility.getFileLengthAsString(lengthInBytes); // file length
            this.fullHeaderAsString += (fileExtension + "\n");// file extension
            this.fullHeaderAsString += getHeaderAsString(header);

            fos.write(header[0].length); // lengths
            fos.write(header[0], 0, header[0].length);

            fos.write(header[1].length); // bytes
            fos.write(header[1], 0, header[1].length);

            int data2length = header[2].length;
            String sLength = Integer.toBinaryString(data2length);
            sLength = "0".repeat(16 - sLength.length()) + sLength;
            byte[] tempByte = new byte[2];
            tempByte[0] = Utility.stringToByte(sLength.substring(0, 8));
            tempByte[1] = Utility.stringToByte(sLength.substring(8));

            fos.write(tempByte, 0, 2); /// huffman
            fos.write(header[2], 0, data2length);

            // ********** end of the header ********************


            // ************** encode the file and print to the output file ************

            InputStream fis = new FileInputStream(sourceFile);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            byte[] huffman = new byte[1024];
            short index = 0; // used for the above huffman array
            String remainingBits = "";
            String huffmanBits;
            while (true) {
                short read = (short) fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        for (byte b : buffer) {
                            huffmanBits = remainingBits + huffmanTable[b + 128].getHuffmanCode();

                            if (huffmanBits.length() >= 8) {
                                remainingBits = huffmanBits.substring(8); // to store bit above than index 7
                                byte huffmanByte = Utility.stringToByte(huffmanBits.substring(0, 8));
                                huffman[index++] = huffmanByte;
                                if (index == 1024) {
                                    fos.write(huffman, 0, 1024);
                                    index = 0;
                                }
                            } else {
                                remainingBits = huffmanBits;
                            }

                        }
                        remaining = (short) buffer.length;
                    }
                } else {
                    // the end of the file was reached. If some bytes are in the buffer

                    for (int i = 0; i < buffer.length - remaining; i++) { // for the remaining bytes
                        huffmanBits = remainingBits + huffmanTable[buffer[i] + 128].getHuffmanCode();

                        if (huffmanBits.length() >= 8) {
                            remainingBits = huffmanBits.substring(8); // to store bit above than index 7
                            byte huffmanByte = Utility.stringToByte(huffmanBits.substring(0, 8));
                            huffman[index++] = huffmanByte;
                            if (index == 1024) {
                                fos.write(huffman, 0, 1024);
                                index = 0;
                            }
                        } else {
                            remainingBits = huffmanBits;
                        }
                    }

                    String temp;
                    while (remainingBits.length() != 0) {
                        int length = remainingBits.length();
                        if (length < 8) {
                            temp = remainingBits.substring(length);
                            remainingBits += ("0".repeat(8 - length));
                        } else {
                            temp = remainingBits.substring(8);
                            remainingBits = remainingBits.substring(0, 8);
                        }
                        byte huffmanByte = Utility.stringToByte(remainingBits);
                        huffman[index++] = huffmanByte;
                        if (index == 1024) {
                            fos.write(huffman, 0, 1024);
                            index = 0;
                        }
                        remainingBits = temp;
                    }
                    break;
                }
            }
            if (index > 0) {
                fos.write(huffman, 0, index);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }

    }

    private byte[][] getHeader(StatisticsTable[] huffmanTable, int numberOfNodes) {

        byte[][] bytes = new byte[3][];

        // ***********************************************************************************

        // get  huffmanTable length for each leaf node
        byte[] lengths = new byte[numberOfNodes];
        int index = 0;
        int sumOfBitsLength = 0;
        for (StatisticsTable b : huffmanTable) {
            if (b != null) {
                lengths[index++] = b.getHuffmanLength();
                sumOfBitsLength += b.getHuffmanLength();
            }
        }

        System.err.println(sumOfBitsLength);
        bytes[0] = lengths; // to store the length of each Huffman code in the leaf node

        // ***********************************************************************************

        // fill byte of each leaf node
        byte[] asciiArray = new byte[numberOfNodes];
        for (int iLoop = 0, iIndex = 0; iLoop < huffmanTable.length; iLoop++) {
            if (huffmanTable[iLoop] != null) {
                asciiArray[iIndex++] = huffmanTable[iLoop].getASCII();
            }
        }
        bytes[1] = asciiArray; // store all byte of leaf nodes

        // ***********************************************************************************

        // to calculate how many bytes exactly I need  to store Huffman code for each leaf node
        byte[] huffmanCodeForLeaf;
        if (sumOfBitsLength % 8 == 0)
            huffmanCodeForLeaf = new byte[sumOfBitsLength / 8];
        else huffmanCodeForLeaf = new byte[(sumOfBitsLength / 8) + 1];

        String huff = "";
        byte b;
        int iIndex = 0;
        for (StatisticsTable statisticsTable : huffmanTable) {
            if (statisticsTable != null) {
                huff += statisticsTable.getHuffmanCode();
                if (huff.length() >= 8) {
                    b = Utility.stringToByte(huff.substring(0, 8));
                    huffmanCodeForLeaf[iIndex++] = b;
                    huff = huff.substring(8);
                }

            }
        }
        while (huff.length() > 0) {
            if (huff.length() >= 8) {
                b = Utility.stringToByte(huff.substring(0, 8));
                huff = huff.substring(8);
                huffmanCodeForLeaf[iIndex++] = b;
            } else {
                b = Utility.stringToByte(huff + "0".repeat(8 - huff.length()));
                huffmanCodeForLeaf[iIndex] = b;
                huff = "";
            }
        }

        bytes[2] = huffmanCodeForLeaf; // To store Huffman representation for all bytes in  leaf nodes

        // ***********************************************************************************

        return bytes;
    }

    public void returnDefault() {
        huffmanTable = new StatisticsTable[ALPHABET_SIZE];
        root = null;
        fullHeaderAsString = "";
        numberOfNodes = 0;
    }

    private String getHeaderAsString(byte[][] header) {

        StringBuilder head = new StringBuilder();

        for (byte[] bytes : header) {
            head.append(bytes.length); // size of each
            // first row: represent lengths for huffman code
            // second row : represent all bytes
            // third row: represent all huffman code
            for (byte aByte : bytes) {
                head.append((char) aByte); // huffman code for each byte
            }
        }

        return head.toString();
    }

}
