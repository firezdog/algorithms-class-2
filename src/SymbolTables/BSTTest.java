package SymbolTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    private BST<String, Integer> bst;

    @BeforeEach
    void setup() {
        bst = new BST<>("A", 1, 1);
    }

    @Nested
    class testNodeCount {
        @Test
        void testNodeCountCheckForSingleNode() {
            assertTrue(BST.nodeCountCheck(bst));
        }

        @Test
        void testNodeCountCheckForNull() {
            assertTrue(BST.nodeCountCheck(null));
        }

        @Test
        void testNodeCountForCorrect() {
            bst.left = new BST<>("B", 1, 2);
            bst.left.left = new BST<>("C", 1, 1);
            bst.right = new BST<>("D", 1, 3);
            bst.right.left = new BST<>("E", 1, 1);
            bst.right.right = new BST<>("F", 1, 1);
            bst.size = 6;
            assertTrue(BST.nodeCountCheck(bst));
        }

        @Test
        void testNodeCountForIncorrectRoot() {
            bst.left = new BST<>("B", 1, 2);
            bst.left.left = new BST<>("C", 1, 1);
            bst.right = new BST<>("D", 1, 3);
            bst.right.left = new BST<>("E", 1, 1);
            bst.right.right = new BST<>("F", 1, 1);
            bst.size = 3;
            assertFalse(BST.nodeCountCheck(bst));
        }

        @Test
        void testNodeCountForIncorrectSub() {
            bst.left = new BST("B", 1, 2);
            bst.left.left = new BST("C", 1, 1);
            bst.right = new BST("D", 1, 2);
            bst.right.left = new BST("E", 1, 1);
            bst.right.right = new BST("F", 1, 1);
            bst.size = 6;
            assertFalse(BST.nodeCountCheck(bst));
        }

        @Test
        // this works, I'm not sure why :p
        void testNodeCountForCycle() {
            bst.left = new BST<>("B", 1, 2);
            bst.left.left = bst.right;
            bst.right = new BST<>("D", 1, 3);
            bst.right.left = new BST<>("E", 1, 1);
            bst.right.right = bst.left;
            bst.size = 6;
            assertFalse(BST.nodeCountCheck(bst));
        }
    }

}