package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileSplit {
    public static void main(String[] args) throws IOException {
        String d = "txt";
        byte[]b = splitFile(new File("cv.huf"));
System.out.println(Arrays.toString(b));

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
