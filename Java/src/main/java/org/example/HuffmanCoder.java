package org.example;

import java.util.*;

// Реализуйте алгоритм Хаффмана для компрессии данных
public class HuffmanCoder {

    public HuffmanResult encode(String text) {
        Map<Character, Integer> frequencies = computeCharactersFrequency(text);
        HuffmanNode huffmanTree = buildHuffmanTree(frequencies);
        Map<Character, String> codes = generateCodes(huffmanTree);

        StringBuilder encodedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            String code = codes.get(character);
            encodedText.append(code);
        }

        return new HuffmanResult(encodedText.toString(), codes, huffmanTree);
    }

    public String decode(String encodedText, HuffmanNode huffmanTree) {
        StringBuilder decodedText = new StringBuilder();

        HuffmanNode current = huffmanTree;
        for (int i = 0; i < encodedText.length(); i++) {
            char bit = encodedText.charAt(i);

            if (bit == '0') {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }

            if (current.isLeaf()) {
                decodedText.append(current.getCharacter());
                current = huffmanTree;
            }
        }

        return decodedText.toString();
    }

    private Map<Character, Integer> computeCharactersFrequency(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            frequencies.compute(character, (k, v) -> (v == null) ? 1 : v + 1);
        }

        return frequencies;
    }

    private HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        Queue<HuffmanNode> nodes = new PriorityQueue<>();
        frequencies.forEach((k, v) -> nodes.add(new HuffmanNode(k, v)));

        while (nodes.size() > 1) {
            HuffmanNode first = nodes.poll();
            HuffmanNode second = nodes.poll();

            HuffmanNode combined = new HuffmanNode(first, second);
            nodes.add(combined);
        }

        return nodes.poll();
    }

    private Map<Character, String> generateCodes(HuffmanNode root) {
        Map<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        return codes;
    }

    private void generateCodes(HuffmanNode node, String code, Map<Character, String> codes) {
        if (node == null) return;

        if (node.isLeaf()) {
            codes.put(node.getCharacter(), code);
            return;
        }

        generateCodes(node.getLeft(), code + '0', codes);
        generateCodes(node.getRight(), code + '1', codes);
    }

}
