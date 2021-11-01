package huffman;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Tsts {
    public static void main(String[] args) {
//        byte bb = 0b00000000;
//        // byte cc = 0b10000000;
//
//        byte b = (byte) (int) Integer.valueOf("10000000", 2);
//        System.out.println(bb | b);// output -> 85
     //   System.gc();



        try {
            FileOutputStream fos = new FileOutputStream("amm.txt");
            byte x = (byte)-127;
            fos.write(x);
            long startTime = System.currentTimeMillis();
            BufferedReader br = new BufferedReader(new FileReader("amm.txt"));
            StringBuilder sb;


            while (true){
                String line= br.readLine();
                if(line== null) break;
                byte[] result = line.getBytes();
                System.out.println(Arrays.toString(result));
            }


            long end = System.currentTimeMillis();
            System.out.println(end-startTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
