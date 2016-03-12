package net.devromik.tree

import java.util.*

/**
 * @author Shulnyaev Roman
 */
class LevelOrderIterator<T>(root: TreeNode<T>, private val isLeaf: (TreeNode<T>) -> Boolean = { it.isLeaf }) {

    private val traversalOrder: Queue<TreeNode<T>>

    // ****************************** //

    init {
        traversalOrder = ArrayDeque(256)
        traversalOrder.add(root)
    }

    // ****************************** //

    val hasNext: Boolean get() = !traversalOrder.isEmpty();

    fun next(): TreeNode<T> {
        val next = traversalOrder.poll()!!

        if (!isLeaf(next)) {
            for (child in next) {
                traversalOrder.add(child)
            }
        }

        return next
    }
}