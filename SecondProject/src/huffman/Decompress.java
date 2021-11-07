package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class Decompress {

    private static Node root;

    public static void main(String[] args) throws Exception {
        byte[] x = {3, 5, 1};
        System.out.println(getNumberOfNodes(x));
    }


    public static int getHeader(byte[] buffer, byte[] bytes, char[] isLeaf, StringBuilder fileExtension) {
        short indexForBytes = 0, indexForIsLeaf = 0;
        int fileLength = 0;
        boolean getFileLength = false;
        boolean getFileExtension = false;
        for (int i = 0; i < buffer.length; i++) {

            // new line('\n') in ascii is 10
            // to get the file length from first some bytes
            if (buffer[i] != (byte) 10 && !getFileLength) {
                fileLength = (fileLength * 10) + (int) buffer[i];
                continue;
            } else if (!getFileLength) { // file extension was obtained; because start a new line
                getFileLength = true;
                continue;
            }

            // new line('\n') in ascii is 10
            // to get the file extension from second some bytes
            if (buffer[i] != (byte) 10 && !getFileExtension) {
                fileExtension.append((char) buffer[i]);
                continue;
            } else if (!getFileExtension) { // file extension was obtained; because start a new line
                getFileExtension = true;
                continue;
            }

            // y in ascii is 121
            // n in ascii is 110
            // new line('\n') in ascii is 10
            // get the header from the others bytes
            if (buffer[i] != (byte) 121 && buffer[i] != (byte) 110 && buffer[i] != (byte) 10) {
                bytes[indexForBytes++] = buffer[i];
            } else if (buffer[i] != (byte) 10) { // the end of the buffer; because start a new line
                isLeaf[indexForIsLeaf++] = (char) buffer[i];
            } else { // The buffer is not finished yet because a new line is not started and this byte is y or n
                return i + 1; // beginning of the Huffman code
            }
        }
        return 0;
    }


    public static Node constructTree(short numberOfNodes, byte[] header, char[] isLeaf) {

        // Code here
        Stack<Node> s = new Stack<>();
        Node root = new Node(header[0]);
        s.push(root);
        int i = 1;
        while (i < numberOfNodes) {
            if (isLeaf[i] == '\0')
                break;
            Node curr = s.peek();
            if (curr.getLeftChild() == null) {
                curr.setLeftChild(new Node(header[i]));
                if (isLeaf[i] == 'n') {
                    s.push(curr.getLeftChild());
                }
                i++;
            } else if (curr.getRightChild() == null) {
                curr.setRightChild(new Node(header[i]));
                if (isLeaf[i] == 'n') {
                    s.push(curr.getRightChild());
                }
                i++;
            } else {
                s.pop();
            }
        }
        return root;
    }


    public static void buildHuffmanTree(final File sourceFile, final File destinationFile) {

        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            int read = fis.read(buffer, 0, remaining);

            // get number of nodes from first some bytes in the buffer
            short numberOfNodes = getNumberOfNodes(buffer);
            byte[] bytes = new byte[numberOfNodes];
            char[] isLeaf = new char[numberOfNodes];
            StringBuilder fileExtension = new StringBuilder("");
            short beginningIndexOfHuffmanCode = (short) getHeader(buffer, bytes, isLeaf, fileExtension);
            root = constructTree(numberOfNodes, bytes, isLeaf);

            byte[] bufferWriter = new byte[1024];
            int indexOfBufferWriter = 0;

            Node current = root;
            String binaryString;

            boolean headerWasBuilt = false;
            while (true) {
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        if (headerWasBuilt) {
                            beginningIndexOfHuffmanCode = 0;
                        } else {
                            headerWasBuilt = true;
                        }
                        do {
                            binaryString = byteToString(buffer[beginningIndexOfHuffmanCode++]);
                            for (byte i = 0; i < binaryString.length(); i++) {
                                if (current.isLeaf()) {
                                    bufferWriter[indexOfBufferWriter++] = current.getBytes();
                                    current = root;
                                } else if (binaryString.charAt(i) == '0') {
                                    current = current.getLeftChild();
                                } else {
                                    current = current.getRightChild();
                                }
                            }
                        } while (beginningIndexOfHuffmanCode < 1024);

                        if (indexOfBufferWriter == 1024) {
                            fos.write(bufferWriter, 0, 1024);
                            indexOfBufferWriter = 0;
                        }

                        remaining = (short) buffer.length;
                    }
                } else {
                    // the end of the file was reached. If some bytes are in the buffer
                    if (headerWasBuilt) {
                        beginningIndexOfHuffmanCode = 0;
                    }
                    do {
                        binaryString = byteToString(buffer[beginningIndexOfHuffmanCode++]);
                        for (byte i = 0; i < binaryString.length(); i++) {
                            if (current.isLeaf()) {
                                bufferWriter[indexOfBufferWriter++] = current.getBytes();
                                current = root;
                            } else if (binaryString.charAt(i) == '0') {
                                current = current.getLeftChild();
                            } else {
                                current = current.getRightChild();
                            }
                        }
                    } while (beginningIndexOfHuffmanCode < 1024 - remaining);

                    fos.write(bufferWriter, 0, indexOfBufferWriter);

                    break;
                }
                read = fis.read(buffer, buffer.length - remaining, remaining);
            }
            if (current != root && !current.isLeaf()) {

            }
            fis.close();
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
    }

    private static short getNumberOfNodes(byte[] buffer) {
        short n = 0;
        for (byte b : buffer) {
            // new line('\n') in ascii is 10
            if (b != (byte) 10) {
                n = (short) ((n * 10) + b);
            } else break;
        }
        return n;
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
}
