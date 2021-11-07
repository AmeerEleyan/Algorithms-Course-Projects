package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FileSplit {
    public static void main(String[] args) throws IOException {
        String d = "txt";
         int x = 100000;
         try{

             FileOutputStream fos = new FileOutputStream("sb.txt");
             fos.write(String.valueOf(x).getBytes());
             fos.close();
             byte[] df = ByteBuffer.allocate(4).putInt(x).array();
             FileInputStream fis = new FileInputStream("sb.txt");
             fis.read(df,0,4);
             int value = 0;
             for (byte b : df) {
                 value = (value << 8) + (b & 0xFF);
             }
             fis.close();
             System.out.println(value);

         }catch(IOException e){

         }
       // byte[]b  = getFileLengthAsBytes(x);
       // System.out.println(Arrays.toString(b));

    }
    private static byte[] getFileLengthAsBytes(int length) {
        int NumberOfDigits = (int) (Math.floor(Math.log10(length)) + 1);
        byte[] bytes = new byte[NumberOfDigits]; // number of digits
        for (int i = NumberOfDigits - 1; i >= 0; i--) {
            bytes[i] = (byte) (length % 10);
            length /= 10;
        }
        return bytes;
    }

    private static  byte[]  splitFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try {
            byte[] buffer = new byte[1024];
            // remaining is the number of bytes to read to fill the buffer
            int remaining = buffer.length;
            // block number is incremented each time a block of 1024 bytes is read
            //and written
            int blockNumber = 1;
            while (true) {
                int read = fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                        writeBlock(blockNumber, buffer, buffer.length - remaining);
                        blockNumber++;
                        remaining = buffer.length;
                    }
                }
                else {
                    return buffer;

                }
            }
        }
        finally {
            fis.close();
        }
    }

    private static void writeBlock(int blockNumber, byte[] buffer, int length)  {
        try {
            FileOutputStream fos = new FileOutputStream("testFunc.txt");
            fos.write(buffer, 0, length);
            fos.close();
        }catch (IOException e) {
            System.out.println();
        }

    }
}
