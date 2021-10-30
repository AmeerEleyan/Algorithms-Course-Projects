package huffman;

public class StatisticsTable {
    private byte ASCII;
    private int frequency;
    private String huffman;
    private int length;

    public StatisticsTable(){

    }

    public StatisticsTable(byte ASCII, int frequency, String huffman, int length) {
        this.ASCII = ASCII;
        this.frequency = frequency;
        this.huffman = huffman;
        this.length = length;
    }

    public byte getASCII() {
        return this.ASCII;
    }

    public void setASCII(byte ASCII) {
        this.ASCII = ASCII;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getHuffman() {
        return this.huffman;
    }

    public void setHuffman(String huffman) {
        this.huffman = huffman;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "StatisticsTable{" +
                "ASCII=" + ASCII +
                ", frequency=" + frequency +
                ", huffman='" + huffman + '\'' +
                ", length=" + length +
                '}';
    }
}
