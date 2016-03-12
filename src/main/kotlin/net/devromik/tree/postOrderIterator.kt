package net.devromik.tree

import java.util.*

/**
 * @author Shulnyaev Roman
 */
class PostOrderIterator<T>(root: TreeNode<T>, private val isLeaf: (TreeNode<T>) -> Boolean = { it.isLeaf }) {

    private val traversalOrder: Deque<TreeNode<T>>
    private val traversedNodes = mutableSetOf<TreeNode<T>>()

    // ****************************** //

    init {
        traversalOrder = ArrayDeque(256)
        traversalOrder.add(root)
    }

    // ****************************** //

    val hasNext: Boolean get() = !traversalOrder.isEmpty();

    fun next(): TreeNode<T> {
        while (true) {
            val first = traversalOrder.peek()

            if (!isLeaf(first)) {
                for (child in first) {
                    if (child !in traversedNodes) {
                        traversalOrder.addFirst(child)
                        break
                    }
                }
            }

            if (traversalOrder.peek() === first) {
                traversedNodes.add(first)
                traversalOrder.poll()

                return first
            }
        }
    }
}