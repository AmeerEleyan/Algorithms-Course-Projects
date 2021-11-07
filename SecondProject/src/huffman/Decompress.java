package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class Decompress {

    private static Node root;
    private static short beginningIndexOfHuffmanCode;
    private static int originalFileLength;

    public static void main(String[] args) throws Exception {
        decompress(new File("data.huf"), new File("wqqq.txt"));
    }


    public static void getHeaderInfo(byte[] buffer, byte[] bytes, char[] isLeaf, StringBuilder fileExtension) {

        short indexForBytes = 0, indexForIsLeaf = 0;
        boolean getFileLength = false;
        boolean getFileExtension = false;


        for (short i = 0; i < buffer.length; i++) {

            // new line('\n') in ascii is 10
            // to get the file length from first some bytes
            if (buffer[i] != (byte) 10 && !getFileLength) {
                originalFileLength = (originalFileLength * 10) + (int) buffer[i];
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
                beginningIndexOfHuffmanCode = (short) (i + 1);// beginning of the Huffman code
                break;
            }
        }
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


    public static void decompress(final File sourceFile, final File destinationFile) {

        // the number of node in huffman tree is 2n -1
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            int read = fis.read(buffer, 0, remaining);


            byte[] bytes = new byte[512]; // 512;  because it is maximum number of nodes in huffman tree
            char[] isLeaf = new char[512];
            StringBuilder fileExtension = new StringBuilder("");
            getHeaderInfo(buffer, bytes, isLeaf, fileExtension);

            int myLength = 0;

            root = constructTree((short) 512, bytes, isLeaf);

            byte[] bufferWriter = new byte[1024];
            int indexOfBufferWriter = 0;

            Node current = root;
            String binaryString = "";

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
                            if (current == null) break;
                            binaryString = binaryString + byteToString(buffer[beginningIndexOfHuffmanCode++]);
                            for (byte i = 0; i < binaryString.length(); i++) {
                                if (current == null)
                                    break;
                                if (binaryString.charAt(i) == '0') {
                                    current = current.getLeftChild();
                                } else {
                                    current = current.getRightChild();
                                }
                                if (current.isLeaf()) {
                                    bufferWriter[indexOfBufferWriter++] = current.getBytes();
                                    current = root;
                                    myLength++;
                                    binaryString = binaryString.substring(i+1);
                                }
                                if (indexOfBufferWriter == 1024) {
                                    fos.write(bufferWriter, 0, 1024);
                                    indexOfBufferWriter = 0;
                                }
                                if (myLength == originalFileLength) break; // end of huffman code
                            }
                        } while (beginningIndexOfHuffmanCode < 1024);

                        remaining = (short) buffer.length;
                    }
                } else {
                    // the end of the file was reached. If some bytes are in the buffer
                    if (headerWasBuilt) {
                        beginningIndexOfHuffmanCode = 0;
                    }
                    do {
                        binaryString = binaryString + byteToString(buffer[beginningIndexOfHuffmanCode++]);
                        if (current == null) break;
                        for (byte i = 0; i < binaryString.length(); i++) {
                            if (current == null) break;
                            if (binaryString.charAt(i) == '0') {
                                current = current.getLeftChild();
                            } else {
                                current = current.getRightChild();
                            }
                            if (current.isLeaf()) {
                                bufferWriter[indexOfBufferWriter++] = current.getBytes();
                                current = root;
                                myLength++;
                                if(i==0) binaryString = binaryString.substring(i+1);
                                else binaryString = binaryString.substring(i);
                                i=-1;
                            }
                            if (indexOfBufferWriter == 1024) {
                                fos.write(bufferWriter, 0, indexOfBufferWriter);
                                indexOfBufferWriter = 0;
                            }
                            if (myLength == originalFileLength) break; // end of huffman code
                        }
                    } while (beginningIndexOfHuffmanCode < 1024 - remaining);
                    break;
                }
                read = fis.read(buffer, buffer.length - remaining, remaining);
            }

            if (indexOfBufferWriter > 0) {
                fos.write(bufferWriter, 0, indexOfBufferWriter);
            }
            fis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // Message.displayMessage("Warning", e.getMessage());
        }
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
