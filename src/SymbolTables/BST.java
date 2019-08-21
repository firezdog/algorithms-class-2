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

    public boolean nodeCountCheck() {
        return nodeCountCheck(this);
    }

    public static boolean nodeCountCheck(BST node) {
        if (node == null) return true;
        if (node.size != BST.size(node.left) + BST.size(node.right) + 1) return false;
        return nodeCountCheck(node.left) && nodeCountCheck(node.right);
    }

    public boolean nodeOrderCheck() {
        BST lo, hi;
        lo = hi = this;
        while (lo.left != null) lo = lo.left;
        while (hi.right != null) hi = hi.right;
        return nodeOrderCheck((Key) lo.key, (Key) hi.key, this);
    }

    public static <Key extends Comparable<Key>, Value> boolean
    nodeOrderCheck(Key lo, Key hi, BST<Key,Value> node) {
        // to make this work without excessive checks and pointer errors, looks like unfortunately I have to pass
        // responsibility for the initial lo, hi to the caller (getting max and min for that tree)
        if (node == null) return true;
        if (lo.compareTo(node.key) <= 0 && hi.compareTo(node.key) >=0)
            return nodeOrderCheck(lo, node.key, node.left) && nodeOrderCheck(node.key, hi, node.right);
        return false;
    }

    public boolean nodeNoDuplicateCheck(boolean found) {
    }

}