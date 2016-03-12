package net.devromik.tree

import java.util.*

/**
 * @author Shulnyaev Roman
 */
class LevelOrderIterator<D>(root: TreeNode<D>, private val isLeaf: (TreeNode<D>) -> Boolean = { it.isLeaf }) {

    private val traversalOrder: Queue<TreeNode<D>>

    // ****************************** //

    init {
        traversalOrder = ArrayDeque(256)
        traversalOrder.add(root)
    }

    // ****************************** //

    val hasNext: Boolean get() = !traversalOrder.isEmpty()

    fun next(): TreeNode<D> {
        val next = traversalOrder.poll()!!

        if (!isLeaf(next)) {
            for (child in next) {
                traversalOrder.add(child)
            }
        }

        return next
    }
}