package huffman;

public class StatisticsTable {
    private byte ASCII;
    private int frequency;
    private String variableLength;


    public StatisticsTable(){

    }

    public StatisticsTable(byte ASCII, int frequency, String variableLength) {
        this.ASCII = ASCII;
        this.frequency = frequency;
        this.variableLength = variableLength;
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

    public String getVariableLength() {
        return this.variableLength;
    }

    public void setVariableLength(String variableLength) {
        this.variableLength = variableLength;
    }



    @Override
    public String toString() {
        return "StatisticsTable{" +
                "ASCII=" + ASCII +
                ", frequency=" + frequency +
                ", huffman='" + variableLength + '\'' +
                '}';
    }
}
