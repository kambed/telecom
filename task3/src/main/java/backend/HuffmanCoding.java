package backend;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {
    static private Map<Character, String> codes = new HashMap<>();

    public static PriorityQueue<HuffmanNode> inicializeNodes(char[] chars, int[] priorities){
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(chars.length, new HuffmanComparator());
        for (int i = 0; i < chars.length; i++) {
            queue.add(new HuffmanNode(priorities[i],chars[i]));
        }
        return queue;
    }

    public static Map<Character, String> getCodes(HuffmanNode root) {
        createMap(root,"");
        return codes;
    }

    /**
     * Decode huffman algorithm
     * @param bits bits of the message
     * @param root root of huffman tree
     * @return decoded string
     */
    public static String decode(char[] bits, HuffmanNode root) {
        StringBuilder sb = new StringBuilder();
        HuffmanNode node = root; //We start from root of the tree
        for (int i = 0; i < bits.length; i++) {
            if (node.getRight() == null && node.getLeft() == null) { //If we find leaf
                sb.append(node.getSign()); //We add leaf char to result
                node = root; //And go back to root
            }
            if (bits[i] == '0') { //If it’s 0 we go left in tree
                node = node.getLeft();
            } else { //If it’s 1 we go right in tree
                node = node.getRight();
            }
        }
        sb.append(node.getSign()); //We add last char of the text
        return sb.toString();
    }

    /**
     * Creates tree with huffman algorithm based on probabilities
     * @param queue nodes sorted by priority
     * @return root node
     */
    public static HuffmanNode createTree(PriorityQueue<HuffmanNode> queue) {
        HuffmanNode root = null;
        while (queue.size() > 1) {
            // extract two min nodes
            HuffmanNode first = queue.remove();
            HuffmanNode second = queue.remove();

            // new node which probability is a sum of previous ones.
            HuffmanNode node = new HuffmanNode(first.getProbability() + second.getProbability(), null);

            //set left and right leaf on the root
            node.setLeft(first);
            node.setRight(second);

            //save as new root and add to queue
            root = node;
            queue.add(node);
        }
        return root;
    }

    /**
     * Change tree to map to make coding easier
     * @param root root huffman tree node
     * @param s sign code (recursion)
     */
    private static void createMap(HuffmanNode root, String s)
    {
        // base case; if the left and right are null
        // then its a leaf node and we print
        // the code s generated by traversing the tree.
        if (root.getLeft() == null && root.getRight() == null) {

            codes.put(root.getSign(),s);
            return;
        }
        // if we go to left then add "0" to the code.
        // if we go to the right add "1" to the code.

        // recursive calls for left and
        // right sub-tree of the generated tree.
        createMap(root.getLeft(), s + "0");
        createMap(root.getRight(), s + "1");
    }
}