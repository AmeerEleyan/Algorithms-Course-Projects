/**
 * @author: Ameer Eleyan
 * ID: 1191076
 * At: 11/5/2021   10:8 PM
 */
package huffman;

public abstract class Utility {

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

    public static byte stringToByte(String s){
        return (byte) (int) Integer.valueOf(s, 2);
    }

    public static int getLengthOfHuffmanCode(String lengthForTwoByte, boolean part) {
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

    public static byte[] getFileLengthAsBytes(String binaryString) {

        byte[] bytes = new byte[4]; // number of digits
        bytes[0] = (byte) (int) Integer.valueOf(binaryString.substring(0, 8), 2);
        bytes[1] = (byte) (int) Integer.valueOf(binaryString.substring(8, 16), 2);
        bytes[2] = (byte) (int) Integer.valueOf(binaryString.substring(16, 24), 2);
        bytes[3] = (byte) (int) Integer.valueOf(binaryString.substring(24, 32), 2);
        return bytes;
    }

    public static String getFileLengthAsString(byte[] length) {
        StringBuilder len = new StringBuilder();
        for (byte b : length) {
            len.append((char) b);
        }
        return len.toString();
    }
}
