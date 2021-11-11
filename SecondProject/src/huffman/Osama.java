package huffman;

import java.util.Arrays;

public class Osama {


//    public static void main(String[] args) {
//        Byte[] bytes = {97, 98, 99, 100, 101, 102, null};
//        String[] huff = {"0", "101", "110", "1000", "111", "1001"};
//        //   Node root = buildTree(bytes, huff);int x= 13;
//        StatisticsTable[] st = new StatisticsTable[7];
//        st[0] = new StatisticsTable();
//        st[0].setASCII((byte) 97);
//        st[0].setHuffmanCode("0");
//        st[0].setHuffmanLength(1);
//
//        st[1] = new StatisticsTable();
//        st[1].setASCII((byte) 98);
//        st[1].setHuffmanCode("101");
//        st[1].setHuffmanLength(3);
//
//        st[2] = new StatisticsTable();
//        st[2].setASCII((byte) 99);
//        st[2].setHuffmanCode("110");
//        st[2].setHuffmanLength(3);
//
//        st[3] = new StatisticsTable();
//        st[3].setASCII((byte) 100);
//        st[3].setHuffmanCode("1000");
//        st[3].setHuffmanLength(4);
//
//        st[4] = new StatisticsTable();
//        st[4].setASCII((byte) 101);
//        st[4].setHuffmanCode("111");
//        st[4].setHuffmanLength(3);
//
//        st[5] = new StatisticsTable();
//        st[5].setASCII((byte) 102);
//        st[5].setHuffmanCode("1001");
//        st[5].setHuffmanLength(4);
//
//        byte[][] t = getHeader(st, 6);
//        for (int i = 0; i < t.length; i++) {
//            System.out.println(Arrays.toString(t[i]));
//        }
//
//
//    }

    public static Node buildTree(Byte[] bytes, String[] huff) {
        Node root;
        if (bytes.length > 0) {
            root = new Node((byte) '\0');
        } else return null;

        for (int i = 0; i < huff.length; ++i) {
            if (bytes[i] == null) break;
            Node current = root;
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

        return root;
    }

    public static byte[][] getHeader(StatisticsTable[] huffmanTable, int numberOfNodes) {

        byte[][] bytes = new byte[3][];

        // ***********************************************************************************

        // get  huffmanTable length as 4 bits of two node and set into new byte
        byte[] lengths;
        boolean isEven = (numberOfNodes & 1) == 0;

        int evenNumber = (numberOfNodes >> 1);
        if (isEven) lengths = new byte[evenNumber];
        else lengths = new byte[evenNumber + 1];

        String lengthForTwoByte = "";
        int sumOfBitsLength = 0; // using to create n bytes to store huffmanTable code in them
        byte flag = 0;
        int index = 0;
        String s1 = "";
        /*
        if huffmanTable[iLoop].getHuffmanLength() = 13 and s1 will be 00001101
        and huffmanTable[iLoop+1].getHuffmanLength() = 10 ans s2 will be 00001010,
        so I will take last 4 bit in right side for s1 and s2 and convert them into one byte
        newByte will be = 11011010
         */
        for (int iLoop = 0; iLoop < huffmanTable.length; iLoop++) {

            if (huffmanTable[iLoop] != null) {
                s1 = byteToString((byte) huffmanTable[iLoop].getHuffmanLength());
                lengthForTwoByte += s1.substring(4);
                flag++;

            }
            if (flag == 2) {
                sumOfBitsLength += ((getLengthOfHuffmanCode(lengthForTwoByte, true)) +
                        (getLengthOfHuffmanCode(lengthForTwoByte, false)));

                lengths[index++] = (byte) (int) Integer.valueOf(lengthForTwoByte, 2);
                flag = 0;
                lengthForTwoByte = "";
            }
        }

        if (lengthForTwoByte.length() == 4) {
            sumOfBitsLength += (getLengthOfHuffmanCode(lengthForTwoByte, true));
            lengths[index] = (byte) (int) Integer.valueOf(lengthForTwoByte + "0".repeat(4), 2); // 0101 => 01010000
        }

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
                if (iIndex == 254) {
                    System.out.println();
                }
                if (huff.length() >= 8) {
                    b = (byte) (int) Integer.valueOf(huff.substring(0, 8), 2);
                    huffmanCodeForLeaf[iIndex++] = b;
                    huff = huff.substring(8);
                }

            }
        }
        while (huff.length() > 0) {
            if (huff.length() >= 8) {
                b = (byte) (int) Integer.valueOf(huff.substring(0, 8), 2);
                huff = huff.substring(8);
                huffmanCodeForLeaf[iIndex++] = b;
            } else {
                b = (byte) (int) Integer.valueOf(huff + "0".repeat(8 - huff.length()), 2);
                huffmanCodeForLeaf[iIndex] = b;
                huff = "";
            }
        }

        bytes[2] = huffmanCodeForLeaf; // To store Huffman representation for all bytes in  leaf nodes

        // ***********************************************************************************

        return bytes;
    }

    private static int getLengthOfHuffmanCode(String lengthForTwoByte, boolean part) {
        // if part == true, I return first 4 bit as int
        // if part == false, I return second 4 bit as int
        //String huff = byteToString(b);
        byte[] as4Bits = {8, 4, 2, 1};
        int length = 0;
        if (part) {
            if (lengthForTwoByte.length() > 0) {
                for (byte i = 0; i < 4; i++) {
                    if (lengthForTwoByte.charAt(i) == '1') length += as4Bits[i];
                }
            }

        } else {
            if (lengthForTwoByte.length() >= 4) {
                for (byte i = 4; i < lengthForTwoByte.length(); i++) {
                    if (lengthForTwoByte.charAt(i) == '1') length += as4Bits[i - 4];
                }
            }

        }
        return length;
    }

    private static String byteToString(byte b) {
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
