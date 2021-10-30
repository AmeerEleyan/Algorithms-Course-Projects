/**
 * @author: Ameer Eleyan
 * ID: 1191076
 * At: 10/30/2021      4:57 AM
 */
package huffman;

public class Node implements Comparable<Node> {
    private byte bytes;
    private int frequency;
    private Node leftChild;
    private Node rightChild;

    public Node() {

    }

    public Node(final byte bytes, final int frequency, final Node left, final Node right) {
        this.bytes = bytes;
        this.frequency = frequency;
        this.leftChild = left;
        this.rightChild = right;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    public byte getBytes() {
        return this.bytes;
    }

    public void setBytes(byte bytes) {
        this.bytes = bytes;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(Node that) {
        int frequencyComparison = Integer.compare(this.frequency, that.frequency);
        if (frequencyComparison != 0)
            return frequencyComparison;
        return Byte.compare(this.bytes, that.bytes);
    }

    @Override
    public String toString() {
        return "Node{" +
                "bytes=" + bytes +
                ", frequency=" + frequency +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}
