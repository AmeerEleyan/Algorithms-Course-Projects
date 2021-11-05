package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileSplit {
    public static void main(String[] args) throws IOException {
        String d = "txt";
        byte[]b = d.getBytes();
        System.out.println(Arrays.toString(b));
        FileOutputStream fos = new FileOutputStream("output.txt");
        try {
            fos.write(b, 0, b.length);
            fos.write('\n');
            fos.write('y');
            fos.write(b, 0, b.length);
        }
        finally {
            fos.close();
        }
    }

    private void splitFile(File file) throws IOException {
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
                    // the end of the file was reached. If some bytes are in the buffer
                    // they are written to the last output file
                    if (remaining < buffer.length) {
                        writeBlock(blockNumber, buffer, buffer.length - remaining);
                    }
                    break;
                }
            }
        }
        finally {
            fis.close();
        }
    }

    private void writeBlock(int blockNumber, byte[] buffer, int length) throws IOException {
        FileOutputStream fos = new FileOutputStream("output_" + blockNumber + ".dat");
        try {
            fos.write(buffer, 0, length);

        }
        finally {
            fos.close();
        }
    }
}
