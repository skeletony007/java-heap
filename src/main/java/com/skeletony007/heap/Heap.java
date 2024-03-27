package com.skeletony007.heap;

/**
 * Maxheap data structure, as taught in COMP202 Complexity of Algorithms at The
 * University of Liverpool
 *
 * @author skeletony007
 */
public class Heap<T extends Comparable<T>> {
    /**
     * Sort the heap using heap sort O(nlogn)
     *
     * @param nodes the nodes to be sorted
     * @return the sorted nodes
     */
    public static <T extends Comparable<T>> T[] sort(final T[] nodes) {
        final Heap<T> heap = new Heap<>(nodes);
        final int length = nodes.length;
        final T[] sorted = nodes.clone();
        for (int i = 0; i < length; i++) {
            sorted[i] = heap.pop();
        }
        return sorted;
    }

    /**
     * Swap root node between two heaps
     *
     * @param heap1 the first subtree
     * @param heap2 the second subtree
     */
    private static <T extends Comparable<T>> void swap(final Heap<T> heap1, final Heap<T> heap2) {
        final T temp = heap1.root;
        heap1.root = heap2.root;
        heap2.root = temp;
    }

    /**
     * The size of the heap
     *
     * <p>
     * Nodes existing in the heap.
     * </p>
     *
     * @var size
     */
    private int size;

    /**
     * The root node
     *
     * @var root
     */
    private T root;

    /**
     * The left subheap
     *
     * @var left
     */
    private Heap<T> left;

    /**
     * The right subheap
     *
     * @var right
     */
    private Heap<T> right;

    /**
     * Constructor with maximum capacity
     *
     * @param capacity the maximum capacity
     */
    public Heap(final int capacity) {
        this.size = 0;
        this.root = null;
        if (capacity > 1) {
            final int leftSize = capacity / 2 + capacity % 2;
            this.left = new Heap<>(leftSize);
            this.right = new Heap<>(capacity - leftSize);
        }
    }

    /**
     * Constructor with nodes
     *
     * @param nodes the initial nodes
     * @see Heap#Heap(T[])
     */
    public Heap(final T[] nodes) {
        this(nodes.length);
        for (final T node : nodes) {
            this.insert(node);
        }
    }

    /**
     * Insert a node into the heap
     *
     * <p>
     * New nodes are populated from the left as a nearly complete binary tree.
     * </p>
     *
     * @param node the node to be inserted
     * @return the heap after insertion
     */
    public Heap<T> insert(final T node) {
        if (this.size == 0) {
            this.root = node;
        } else {
            if (this.left.size == this.right.size || !this.left.isComplete()) {
                this.left.insert(node);
                this.heapify("left");
            } else {
                this.right.insert(node);
                this.heapify("right");
            }
        }
        this.size++;

        return this;
    }

    /**
     * Pop the root node from the heap
     *
     * <p>
     * Swap the root node with the last node and remove, then heapify.
     * </p>
     *
     * @return the root node
     */
    public T pop() {
        if (this.size == 0) {
            return null;
        }
        final T root = this.root;
        this.root = this.removeLastNode();
        this.heapify();

        return root;
    }

    /**
     * Pretty print the heap
     */
    public String toString() {
        return this.toString(0, "");
    }

    /**
     * Remove the last node from the heap
     *
     * @return the last node
     */
    private T removeLastNode() {
        if (this.size == 0 || this.size == 1) {
            final T root = this.root;
            this.root = null;
            this.size--;
            return root;
        }
        this.size--;
        if (this.left.size > this.right.size) {
            return this.left.removeLastNode();
        } else {
            return this.right.removeLastNode();
        }
    }

    /**
     * Heapify (valmorpharize) the heap
     *
     * <p>
     * Bubble (sort) down, swap with the larger child.
     * </p>
     *
     * @return the heap
     */
    private Heap<T> heapify() {
        try {
            if (this.right.size == 0 || this.left.root.compareTo(this.right.root) > 0) {
                if (this.left.root.compareTo(this.root) > 0) {
                    swap(this, this.left);
                    this.left.heapify();
                }
            } else {
                if (this.right.root.compareTo(this.root) > 0) {
                    swap(this, this.right);
                    this.right.heapify();
                }
            }
        } catch (NullPointerException e) {
        }

        return this;
    }

    /**
     * Get the capacity of the heap
     *
     * @return the difference between the complete size of the binary tree and the
     *         current size
     */
    private boolean isComplete() {
        return Math.pow(2, this.getDepth()) - 1 == this.size;
    }

    /**
     * Get the depth of the heap
     *
     * @return the depth
     */
    private int getDepth() {
        return (int) Math.floor(Math.log(this.size) / Math.log(2)) + 1;
    }

    /**
     * Heapify the heap
     *
     * <p>
     * Bubble (sort) down, swap with the specified ("left", "right") child.
     * </p>
     *
     * @param side the side (left or right of the root)
     */
    private Heap<T> heapify(final String side) {
        try {
            switch (side) {
                case "left":
                    if (this.left.root.compareTo(this.root) > 0) {
                        swap(this, this.left);
                    }
                    break;
                case "right":
                    if (this.right.root.compareTo(this.root) > 0) {
                        swap(this, this.right);
                    }
                    break;
            }
        } catch (NullPointerException e) {
        }

        return this;
    }

    /**
     * Pretty print the heap
     *
     * @param indent the indentation
     * @param side   the side (left or right of the root)
     */
    private String toString(final int indent, final String side) {
        final StringBuilder sb = new StringBuilder();

        if (this.root == null) {
            return "";
        }
        if (this.right != null) {
            sb.append(this.right.toString(indent + 4, "┌ "));
        }
        sb.append(" ".repeat(indent))
                .append(side)
                .append(this.root)
                .append("\n");
        if (this.left != null) {
            sb.append(this.left.toString(indent + 4, "└ "));
        }

        return sb.toString();
    }
}
