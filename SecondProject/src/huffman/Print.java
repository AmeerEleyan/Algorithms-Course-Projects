package huffman;


public class Print {
    private static Byte[] fileBytes;
    private static String[] huffRepresentation;

    public static int originalFileLength;
    public static Node root;

    private static byte[] huffmanLengths;
    private static byte[] huffmanRepresentationBytes;

    public static int getHuffmanRootOfHuffmanTree(final byte[] buffer, StringBuilder fileExtension) {
        int beginning = decompress(buffer, fileExtension);
        huffRepresentation = new String[fileBytes.length];
        buildTwoMainArray();
        root = buildTree(fileBytes, huffRepresentation);
        return beginning;
    }


    private static void buildTwoMainArray() {
        int hrIndex = 0;
        int huffIndex = 0;
        String len, huff = "";
        int firstHuffLength, secondHuffLength;
        String fullHuffCode = getHuffmanRepresentationBytesAsSting();
        for (int i = 0; i < huffmanLengths.length; ++i) {

            len = byteToString(huffmanLengths[i]);
            firstHuffLength = getLengthOfHuffmanCode(len, true);
            secondHuffLength = getLengthOfHuffmanCode(len, false);


            huffRepresentation[huffIndex++] = fullHuffCode.substring(0, firstHuffLength);
            fullHuffCode = fullHuffCode.substring(firstHuffLength);
            if (secondHuffLength == 0) break;
            huffRepresentation[huffIndex++] = fullHuffCode.substring(0, secondHuffLength);
            fullHuffCode = fullHuffCode.substring(secondHuffLength);

           /* if (secondHuffLength == 0) {
                huffRepresentation[huffIndex] = huff.substring(0, firstHuffLength);
                break; // the byte have only first 4 bits
            }

            while (hrIndex < fileBytes.length) {
                huff += byteToString(huffmanRepresentationBytes[hrIndex++]);

                if (firstHuffLength <= huff.length()) {
                    huffRepresentation[huffIndex++] = huff.substring(0, firstHuffLength);
                    huff = huff.substring(firstHuffLength);

                    if (secondHuffLength <= huff.length()) {
                        huffRepresentation[huffIndex++] = huff.substring(0, secondHuffLength);
                        huff = huff.substring(secondHuffLength);
                        break;
                    }

                }
            }

            */

        }


    }

    public static Node buildTree(Byte[] bytes, String[] huff) {
        Node root;
        if (bytes.length > 0) {
            root = new Node((byte) '\0');
        } else return null;

        for (int i = 0; i < huff.length; ++i) {
            if (bytes[i] == null) break;
            Node current = root;
            if (huff[i] != null) {
                for (int j = 0; j < huff[i].length(); ++j) {
                    if (huff[i].charAt(j) == '0') {
                        if (current.getLeftChild() == null) {
                            current.setLeftChild(new Node((byte) '\0'));
                        }
                        current = current.getLeftChild();
                    } else {
                        if (current.getRightChild() == null) {
                            current.setRightChild(new Node((byte) '\0'));
                        }
                        current = current.getRightChild();
                    }
                }
                current.setBytes(bytes[i]);
            }

        }

        return root;
    }

    public static int decompress(final byte[] buffer, StringBuilder fileExtension) {

        String length = "";

        boolean isHuffmanLengthIHave = false;
        int hlSize = 0;
        int hlIndex = 0;


        boolean isFileBytesIHave = false;
        int fbSize = 0;
        int fbIndex = 0;


        boolean isHuffmanRepresentationIHave = false;
        int hrSize = 0;
        int hrIndex = 0;

        boolean getFileExtension = false;
        short i = 0;
        for (; i < buffer.length; i++) {

            // get file length from first 4 bytes
            if (i < 4) {
                length += byteToString(buffer[i]);
                continue;
            } else if (i == 4) {
                originalFileLength = Integer.parseInt(length, 2);
                length = null;
            }

            // get file extension from the 4th byte even to find the first '\n'
            // because no file extension contains  '\n'
            if (buffer[i] != (byte) 10 && !getFileExtension) {
                fileExtension.append((char) buffer[i]);
                continue;
            } else if (buffer[i] == (byte) 10) { // end of file extension and skip current byte that represent \n
                getFileExtension = true;

                continue;
            }

            // starting to get the bytes and are Huffman representation for them;

            // get # of lengths bytes
            if (!isHuffmanLengthIHave) {
                hlSize = buffer[i];
                if (hlSize <= 0) hlSize += 256; // to handle negative value in bytes
                huffmanLengths = new byte[hlSize];
                isHuffmanLengthIHave = true;
                continue;
            }
            if (hlIndex < hlSize) {
                huffmanLengths[hlIndex++] = buffer[i];
                continue;
            }

            // get file bytes
            if (!isFileBytesIHave) {
                fbSize = buffer[i];
                if (fbSize <= 0) fbSize += 256;
                fileBytes = new Byte[fbSize];
                isFileBytesIHave = true;
                break;
            }
//            if (fbIndex < fbSize) {
//                fileBytes[fbIndex] = buffer[i];
//                if (buffer[i] == 124)
//                    System.out.println();
//                fbIndex++;
//            } else break;

        }

        for (int k = 0; k < fbSize; k++) {
            fileBytes[fbIndex++] = buffer[++i];
        }

        byte b1 = buffer[++i];
        byte b2 = buffer[++i];
        String sHr = byteToString(b1) + byteToString(b2);
        hrSize = Integer.parseInt(sHr, 2);
        huffmanRepresentationBytes = new byte[hrSize];
        int j;
        for (j = 0; j < hrSize; j++) {
            huffmanRepresentationBytes[hrIndex++] = buffer[++i];
        }
        return i + 1;
    }

    private static String getHuffmanRepresentationBytesAsSting() {
        StringBuilder s = new StringBuilder("");
        for (byte b : huffmanRepresentationBytes) {
            s.append(byteToString(b));
        }
        return s.toString();
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

    private static int getLengthOfHuffmanCode(String lengthForTwoByte, boolean part) {
        // if part == true, I return first 4 bit as int
        // if part == false, I return second 4 bit as int
        //String huff = byteToString(b);
        byte[] as4Bits = {8, 4, 2, 1};
        int length = 0;
        if (part) {
            for (byte i = 0; i < 4; i++) {
                if (lengthForTwoByte.charAt(i) == '1') length += as4Bits[i];
            }
        } else {
            for (byte i = 4; i < 8; i++) {
                if (lengthForTwoByte.charAt(i) == '1') length += as4Bits[i - 4];
            }
        }
        return length;
    }

}
