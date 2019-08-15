package SymbolTables;

public class BST<Key extends Comparable<Key>, Value> {
    protected Key key;
    protected Value value;
    protected BST<Key, Value> left, right;
    protected int size;

    BST(Key k, Value v, int s) {
        key = k;
        value = v;
        size = s;
    }

    public static int size(BST node) {
        if (node == null) return 0;
        if (node.value == null) return size(node.left) + size(node.right);
        return node.size;
    }

    public static boolean nodeCountCheck(BST node) {
        if (node == null) return true;
        if (node.size != BST.size(node.left) + BST.size(node.right) + 1) return false;
        return nodeCountCheck(node.left) && nodeCountCheck(node.right);
    }


}