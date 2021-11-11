package huffman;


public class ReadFileToDecompress {
    private Byte[] fileBytes;
    private String[] huffRepresentation;

    private int originalFileLength;
    private Node root;

    private byte[] huffmanLengths;
    private byte[] huffmanRepresentationBytes;

    public int getHuffmanRootOfHuffmanTree(final byte[] buffer, StringBuilder fileExtension) {
        int beginning = decompress(buffer, fileExtension);
        this.huffRepresentation = new String[this.fileBytes.length];
        buildTwoMainArray();
        this.root = buildTree(this.fileBytes, this.huffRepresentation);
        return beginning;
    }

    public int getOriginalFileLength() {
        return this.originalFileLength;
    }

    public Node getRoot() {
        return this.root;
    }

    private void buildTwoMainArray() {
        int huffIndex = 0;
        String len;
        int firstHuffLength, secondHuffLength;
        String fullHuffCode = this.getHuffmanRepresentationBytesAsSting();
        for (int i = 0; i < this.huffmanLengths.length; ++i) {

            len = Utility.byteToString(this.huffmanLengths[i]);
            firstHuffLength = Utility.getLengthOfHuffmanCode(len, true);
            secondHuffLength = Utility.getLengthOfHuffmanCode(len, false);

            this.huffRepresentation[huffIndex++] = fullHuffCode.substring(0, firstHuffLength);
            fullHuffCode = fullHuffCode.substring(firstHuffLength);
            if (secondHuffLength == 0) break;
            this.huffRepresentation[huffIndex++] = fullHuffCode.substring(0, secondHuffLength);
            fullHuffCode = fullHuffCode.substring(secondHuffLength);

        }

    }

    private Node buildTree(Byte[] bytes, String[] huff) {
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

    private int decompress(final byte[] buffer, StringBuilder fileExtension) {

        String length = "";

        boolean isHuffmanLengthIHave = false;
        int hlSize = 0;
        int hlIndex = 0;

        int fbSize = 0;
        int fbIndex = 0;

        int hrSize;
        int hrIndex = 0;

        boolean getFileExtension = false;
        short i = 0;
        for (; i < buffer.length; i++) {

            // get file length from first 4 bytes
            if (i < 4) {
                length += Utility.byteToString(buffer[i]);
                continue;
            } else if (i == 4) {
                this.originalFileLength = Integer.parseInt(length, 2);
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
                this.huffmanLengths = new byte[hlSize];
                isHuffmanLengthIHave = true;
                continue;
            }
            if (hlIndex < hlSize) {
                this.huffmanLengths[hlIndex++] = buffer[i];
                continue;
            }

            // get file bytes
            fbSize = buffer[i];
            if (fbSize <= 0) fbSize += 256;
            this.fileBytes = new Byte[fbSize];
            break;
        }

        for (int k = 0; k < fbSize; k++) {
            this.fileBytes[fbIndex++] = buffer[++i];
        }

        byte b1 = buffer[++i];
        byte b2 = buffer[++i];
        String sHr = Utility.byteToString(b1) + Utility.byteToString(b2);
        hrSize = Integer.parseInt(sHr, 2);
        this.huffmanRepresentationBytes = new byte[hrSize];
        int j;
        for (j = 0; j < hrSize; j++) {
            this.huffmanRepresentationBytes[hrIndex++] = buffer[++i];
        }
        return i + 1;
    }

    private String getHuffmanRepresentationBytesAsSting() {
        StringBuilder s = new StringBuilder("");
        for (byte b : huffmanRepresentationBytes) {
            s.append(Utility.byteToString(b));
        }
        return s.toString();
    }




}
