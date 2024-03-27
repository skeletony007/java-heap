package com.skeletony007.heap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the Heap class.
 *
 * @see Heap
 * @author skeletony007
 */
public class HeapTest {
    private final int size = 10;
    private final Integer[] nodes = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3 };
    private final Integer[] sorted = { 9, 6, 5, 5, 4, 3, 3, 2, 1, 1 };

    /**
     * Test the insert and pop methods.
     */
    @Test
    public void ioTest() {
        final Heap<Integer> heap = new Heap<>(size);
        for (int i = 0; i < this.size; i++) {
            heap.insert(this.nodes[i]);
        }

        for (int i = 0; i < this.size; i++) {
            assertEquals(heap.pop(), this.sorted[i]);
        }
    }

    /**
     * Test the print method.
     *
     * <p>
     * Also confirms the heap is a patially complete binary tree.
     * </p>
     *
     */
    @Test
    public void printTest() {
        final Heap<Integer> heap = new Heap<>(this.nodes);

        assertTrue(heap.toString().matches(
                "        ┌ \\d+\n    ┌ \\d+\n        └ \\d+\n9\n        ┌ \\d+\n            └ \\d+\n    └ \\d+\n            ┌ \\d+\n        └ \\d+\n            └ \\d+\n"));
    }

    /**
     * Test the heap sort method.
     */
    @Test
    public void sortTest() {
        final Integer[] sorted = Heap.sort(this.nodes);

        for (int i = 0; i < this.size; i++) {
            assertEquals(sorted[i], this.sorted[i]);
        }
    }
}
