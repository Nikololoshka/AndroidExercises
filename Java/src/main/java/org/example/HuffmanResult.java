package org.example;

import java.util.Map;

public class HuffmanResult {
    private final String text;
    private final Map<Character, String> codes;
    private final HuffmanNode huffmanTree;

    public HuffmanResult(String text, Map<Character, String> codes, HuffmanNode huffmanTree) {
        this.text = text;
        this.codes = codes;
        this.huffmanTree = huffmanTree;
    }

    public String getText() {
        return text;
    }

    public HuffmanNode getHuffmanTree() {return huffmanTree;}

    public Map<Character, String> getCodes() {
        return codes;
    }
}
