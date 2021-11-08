package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Decompress {

    private static Node root;
    private static short beginningIndexOfHuffmanCode;
    private static int originalFileLength;
    private static int numberOfNodes = 0;

    public static void main(String[] args) throws Exception {
        decompress(new File("datas.huf"), new File("102.txt"));
    }


    public static void getHeaderInfo(byte[] buffer, byte[] inOrder, byte[] prOrder, StringBuilder fileExtension) {

        short indexForBytes = 0;
        boolean getFileLength = false;
        boolean getFileExtension = false;
        boolean getInOrderTraversal = false;

        for (short i = 0; i < buffer.length; i++) {

            // new line('\n') in ascii is 10
            // to get the file length from first some inorder
            if (buffer[i] != (byte) 10 && !getFileLength) {
                originalFileLength = (originalFileLength * 10) + (int) buffer[i];
                continue;
            } else if (!getFileLength) { // file extension was obtained; because start a new line
                getFileLength = true;
                continue;
            }

            // new line('\n') in ascii is 10
            // to get the file extension from second some inorder
            if (buffer[i] != (byte) 10 && !getFileExtension) {
                fileExtension.append((char) buffer[i]);
                continue;
            } else if (!getFileExtension) { // file extension was obtained; because start a new line
                getFileExtension = true;
                continue;
            }

            // get the inOrder traversal
            if (buffer[i] != (byte) 94 && buffer[i + 1] != (byte) 96 && !getInOrderTraversal) {
                inOrder[indexForBytes++] = buffer[i];
                numberOfNodes++;
                continue;
            } else if (!getInOrderTraversal) {
                getInOrderTraversal = true;
                indexForBytes = 0;
                i++; // to skip special char('`')
                continue;
            }

            // get the preOrder traversal
            if (buffer[i] != (byte) 94 && buffer[i + 1] != (byte) 96) {
                prOrder[indexForBytes++] = buffer[i];
            } else {
                beginningIndexOfHuffmanCode = (short) (i + 2);// beginning of the Huffman code
                break;
            }

        }
    }

    public static Node buildHuffmanTree(byte[] preorder, byte[] inorder) {
        Set<Node> set = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        Node root = null;
        for (int pre = 0, in = 0; pre < preorder.length; ) {

            Node node;
            do {
                node = new Node(preorder[pre]);
                if (root == null) {
                    root = node;
                }
                if (!stack.isEmpty()) {
                    if (set.contains(stack.peek())) {
                        set.remove(stack.peek());
                        stack.pop().setRightChild(node);
                    } else {
                        stack.peek().setLeftChild(node);
                    }
                }
                stack.push(node);
            } while (preorder[pre++] != inorder[in] && pre < preorder.length);

            node = null;
            while (!stack.isEmpty() && in < inorder.length &&
                    stack.peek().getBytes() == inorder[in]) {
                node = stack.pop();
                in++;
            }

            if (node != null) {
                set.add(node);
                stack.push(node);
            }
        }

        return root;
    }


    public static void decompress(final File sourceFile, final File destinationFile) {

        // the number of node in huffman tree is 2n -1
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destinationFile);

            // 1050 because in the first time, if all bytes are repeated, the number of inorder traversal nodes is 511
            // and the preorder 511 and file length 6 and special char between them 4 and max file extension  can be 20
            // So I select 1050 to not call this method again
            byte[] buffer = new byte[1050]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            int read = fis.read(buffer, 0, remaining);


            byte[] tempInOrder = new byte[511]; // 511;  because it is maximum number of nodes in huffman tree
            byte[] tempPrOrder = new byte[511];
            StringBuilder fileExtension = new StringBuilder("");
            getHeaderInfo(buffer, tempInOrder, tempPrOrder, fileExtension);

            // I coped them, because when i
            byte[] inOrder = new byte[numberOfNodes];
            System.arraycopy(tempInOrder,0,inOrder,0,numberOfNodes);

            byte[] prOrder = new byte[numberOfNodes];
            System.arraycopy(tempPrOrder,0,prOrder,0,numberOfNodes);

            int myLength = 0;
            root = buildHuffmanTree(prOrder, inOrder);

            byte[] bufferWriter = new byte[1050];
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
                                    binaryString = binaryString.substring(i + 1);
                                }
                                if (indexOfBufferWriter == 1050) {
                                    fos.write(bufferWriter, 0, 1050);
                                    indexOfBufferWriter = 0;
                                }
                                if (myLength == originalFileLength) break; // end of huffman code
                            }
                        } while (beginningIndexOfHuffmanCode < 1050);

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
                                if (i == 0) binaryString = binaryString.substring(i + 1);
                                else binaryString = binaryString.substring(i);
                                i = -1;
                            }
                            if (indexOfBufferWriter == 1050) {
                                fos.write(bufferWriter, 0, indexOfBufferWriter);
                                indexOfBufferWriter = 0;
                            }
                            if (myLength == originalFileLength) break; // end of huffman code
                        }
                    } while (beginningIndexOfHuffmanCode < 1050 - remaining);
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

    //    public static Node constructTree(short numberOfNodes, byte[] header, char[] isLeaf) {
//
//        // Code here
//        Stack<Node> s = new Stack<>();
//        Node root = new Node(header[0]);
//        s.push(root);
//        int i = 1;
//        while (i < numberOfNodes) {
//            if (isLeaf[i] == '\0')
//                break;
//            Node curr = s.peek();
//            if (curr.getLeftChild() == null) {
//                curr.setLeftChild(new Node(header[i]));
//                if (isLeaf[i] == 'n') {
//                    s.push(curr.getLeftChild());
//                }
//                i++;
//            } else if (curr.getRightChild() == null) {
//                curr.setRightChild(new Node(header[i]));
//                if (isLeaf[i] == 'n') {
//                    s.push(curr.getRightChild());
//                }
//                i++;
//            } else {
//                s.pop();
//            }
//        }
//        return root;
//    }

}
