package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanDecompress {

    public static void decompress(final File sourceFile) {

        byte indexOfDot = (byte) sourceFile.getAbsolutePath().lastIndexOf('.');

        // the number of node in huffman tree is 2n -1
        try {
            FileInputStream fis = new FileInputStream(sourceFile);

            // 1024 because in the first time, if all bytes are repeated, the number of inorder traversal nodes is 511
            // and the preorder 511 and file length 6 and special char between them 4 and max file extension  can be 20
            // So I select 1024 to not call this method again
            byte[] buffer = new byte[1024]; // number of bytes can be read

            // remaining is the number of bytes to read to fill the buffer
            short remaining = (short) buffer.length;

            int read = fis.read(buffer, 0, remaining);


            StringBuilder fileExtension = new StringBuilder();

            ReadFileToDecompress decompress = new ReadFileToDecompress();

            short beginningIndexOfHuffmanCode = (short) decompress.getStartIndexOfHuffmanCode(buffer, fileExtension);

            Node root = decompress.getRoot();
            int originalFileLength = decompress.getOriginalFileLength();


            String newFilePath = sourceFile.getAbsolutePath().substring(0, indexOfDot + 1) + fileExtension;
            FileOutputStream fos = new FileOutputStream(newFilePath);

            int myLength = 0;


            byte[] bufferWriter = new byte[1024];
            int indexOfBufferWriter = 0;

            Node current = root;
            StringBuilder binaryString = new StringBuilder();

            byte tempI = 0;
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
                            binaryString.append(Utility.byteToString(buffer[beginningIndexOfHuffmanCode++]));
                            for (byte i = tempI; i < binaryString.length(); i++) {
                                if (binaryString.charAt(i) == '0') {
                                    current = current.getLeftChild();
                                } else {
                                    current = current.getRightChild();
                                }
                                if (myLength == originalFileLength) {
                                    break; // end of huffman codef
                                }
                                if (current.isLeaf()) {
                                    bufferWriter[indexOfBufferWriter++] = current.getTheByte();
                                    current = root;
                                    myLength++;
                                    binaryString = new StringBuilder(binaryString.substring(i + 1));
                                    i = -1;
                                }
                                if (indexOfBufferWriter == 1024) {
                                    fos.write(bufferWriter, 0, 1024);
                                    indexOfBufferWriter = 0;
                                }

                            }
                            tempI = (byte) binaryString.length();
                        } while (beginningIndexOfHuffmanCode < 1024);

                        remaining = (short) buffer.length;
                    }
                } else {

                    boolean flag = true; // for length

                    // the end of the file was reached. If some bytes are in the buffer
                    if (headerWasBuilt) {
                        beginningIndexOfHuffmanCode = 0;
                    }
                    do {
                        binaryString.append(Utility.byteToString(buffer[beginningIndexOfHuffmanCode++]));
                        for (byte i = tempI; i < binaryString.length(); i++) {
                            if (binaryString.charAt(i) == '0') {
                                current = current.getLeftChild();
                            } else {
                                current = current.getRightChild();
                            }
                            if (current.isLeaf()) {
                                bufferWriter[indexOfBufferWriter++] = current.getTheByte();
                                current = root;
                                myLength++;
                                binaryString = new StringBuilder(binaryString.substring(i + 1));
                                i = -1; // because when again loop will increment
                            }
                            if (indexOfBufferWriter == 1024) {
                                fos.write(bufferWriter, 0, indexOfBufferWriter);
                                indexOfBufferWriter = 0;
                            }
                            if (myLength == originalFileLength) {
                                flag = false; // break while loop
                                break; // end of huffman code
                            }
                        }
                        tempI = (byte) binaryString.length();
                    } while ((beginningIndexOfHuffmanCode < 1024 - remaining) && flag);

                    break;
                }
                read = fis.read(buffer, buffer.length - remaining, remaining);
            }

            if (indexOfBufferWriter > 0) {
                fos.write(bufferWriter, 0, indexOfBufferWriter);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
    }

}
