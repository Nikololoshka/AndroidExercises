package org.example;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private char character;
    private int frequency;

    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(HuffmanNode first, HuffmanNode second) {
        this.character = '\0';
        this.frequency = first.frequency + second.frequency;
        this.left = first;
        this.right = second;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.frequency - o.frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
