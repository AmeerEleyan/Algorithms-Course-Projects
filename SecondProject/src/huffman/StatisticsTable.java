package huffman;

public class StatisticsTable {
    private byte ASCII;
    private int frequency;
    private String huffmanCode;
    private byte huffmanLength;


    public StatisticsTable(byte ASCII, int frequency, String variableLength, byte huffmanLength) {
        this.ASCII = ASCII;
        this.frequency = frequency;
        this.huffmanCode = variableLength;
        this.huffmanLength = huffmanLength;
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

    public String getHuffmanCode() {
        return this.huffmanCode;
    }

    public byte getHuffmanLength() {
        return this.huffmanLength;
    }

    public void setHuffmanLength(byte huffmanLength) {
        this.huffmanLength = huffmanLength;
    }

    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }


    @Override
    public String toString() {
        return "StatisticsTable{" +
                "ASCII=" + ASCII +
                ", frequency=" + frequency +
                ", huffmanCode='" + huffmanCode + '\'' +
                ", huffmanLength=" + huffmanLength +
                '}';
    }
}
