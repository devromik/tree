package net.devromik.tree

import java.util.*

/**
 * @author Shulnyaev Roman
 */
class PostOrderIterator<D>(root: TreeNode<D>, private val isLeaf: (TreeNode<D>) -> Boolean = { it.isLeaf }) {

    private val traversalOrder: Deque<TreeNode<D>>
    private val traversedNodes = mutableSetOf<TreeNode<D>>()

    // ****************************** //

    init {
        traversalOrder = ArrayDeque(256)
        traversalOrder.add(root)
    }

    // ****************************** //

    val hasNext: Boolean get() = !traversalOrder.isEmpty()

    fun next(): TreeNode<D> {
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