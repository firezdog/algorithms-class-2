package SymbolTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    private BST<String, Integer> bst;

    @BeforeEach
    void setup() {
        // TODO should just have overloaded method for no args?
        bst = new BST<>(null, null, 1);
    }

    @Nested
    class TestNodeCount {
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
            bst.left = new BST<>("B", 1, 2);
            bst.left.left = new BST<>("C", 1, 1);
            bst.right = new BST<>("D", 1, 2);
            bst.right.left = new BST<>("E", 1, 1);
            bst.right.right = new BST<>("F", 1, 1);
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

    @Nested
    class TestNodeOrder {
        @Test
        void testOrderForNull() {
            assertTrue(BST.nodeOrderCheck("a", "z", null));
        }

        @Test
        void testOrderForCorrect() {
            bst = new BST<>("G", 0, 1);
            bst.left = new BST<>("F", 0, 1);
            bst.left.left = new BST<>("B", 0, 1);
            bst.right = new BST<>("N", 0, 1);
            bst.right.right = new BST<>("Z", 0, 1);
            assertTrue(bst.nodeOrderCheck());
        }

        @Test
        void testOrderForIncorrect() {
            bst = new BST<>("G", 0, 1);
            bst.left = new BST<>("F", 0, 1);
            bst.left.left = new BST<>("Z", 0, 1);
            bst.right = new BST<>("N", 0, 1);
            bst.right.right = new BST<>("B", 0, 1);
            assertFalse(bst.nodeOrderCheck());
        }
    }

    @Nested
    class TestNodeDuplication {
        @Test
        void testWithDuplicates() {
            bst = new BST<>("A", 0, 1);
            bst.right = new BST<>("A", 0, 1);
            bst.right.right = new BST<>("R", 0, 1);
            bst.right.right.left = new BST<>("D", 0, 1);
            bst.right.right.right = new BST<>("V", 0, 1);
            bst.right.right.right.left = new BST<>("R", 0, 1);
            bst.right.right.left.left = new BST<>("A", 0, 1);
            bst.right.right.left.right = new BST<>("K", 0, 1);
            assertFalse(bst.nodeNoDuplicateCheck());
        }

        @Test
        void testWithoutDuplicates() {
            bst = new BST<>("K", 0, 1);
            bst.left = new BST<>("D", 0, 1);
            bst.left.left = new BST<>("A", 0, 1);
            bst.right = new BST<>("V", 0, 1);
            bst.right.left = new BST<>("R", 0, 1);
            assertTrue(bst.nodeNoDuplicateCheck());
        }

        @Test
        void testWithoutDuplicatesOutOfOrder() {
            bst = new BST<>("B", 0, 1);
            bst.left = new BST<>("A", 0, 1);
            bst.right = new BST<>("A", 0, 1);
            assertTrue(bst.nodeNoDuplicateCheck());
        }

    }

}