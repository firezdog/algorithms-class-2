package SymbolTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackBSTTest extends BSTTest {

    @BeforeEach
    void setup() {
        bst = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
    }

    @Test
    void testIsBalancedForUnbalanced() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        assertFalse(((RedBlackBST) bst).isBalanced());
    }

    @Test
    void testIsBalancedForBalanced() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        assertTrue(((RedBlackBST) bst).isBalanced());
    }

    @Test
    // case: it would be balanced if it were just path lengths, but there are red links
    void testIsBalancedForUnbalancedWithRed() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        assertFalse(((RedBlackBST) bst).isBalanced());
    }

    @Test
    // case: it would unbalanced, but there are red links.
    void testIsBalancedForBalancedWithRed() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right.right = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        assertTrue(((RedBlackBST) bst).isBalanced());
    }

    @Test
    void testIs23WhenAllBlack() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        assertTrue(((RedBlackBST) bst).is23());
    }

    @Test
    void testIs23WhenRedRight() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.right = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        assertFalse(((RedBlackBST) bst).is23());
    }

    @Test
    void testIs23WhenOneRedLeft() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.left = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        bst.left.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        assertTrue(((RedBlackBST) bst).is23());
    }

    @Test
    void testIs23WhenTwoRedLefts() {
        bst.left = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        bst.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        bst.left.left = new RedBlackBST<>(null, null, 1, RedBlackBST.RED);
        bst.left.right = new RedBlackBST<>(null, null, 1, RedBlackBST.BLACK);
        assertFalse(((RedBlackBST) bst).is23());
    }

}