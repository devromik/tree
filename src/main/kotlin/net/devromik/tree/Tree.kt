package net.devromik.tree

/**
 * @author Shulnyaev Roman
 */
class Tree<D>(var root: TreeNode<D> = TreeNode()) {
    fun levelOrderIterator(isLeaf: (TreeNode<D>) -> Boolean = { it.isLeaf }): LevelOrderIterator<D> = LevelOrderIterator(root, isLeaf)

    fun traverseInLevelOrder(consume: (TreeNode<D>) -> Unit) {
        val iter = levelOrderIterator()

        while (iter.hasNext) {
            consume(iter.next())
        }
    }

    fun postOrderIterator(isLeaf: (TreeNode<D>) -> Boolean = { it.isLeaf }): PostOrderIterator<D> = PostOrderIterator(root, isLeaf)

    fun traverseInPostOrder(consume: (TreeNode<D>) -> Unit) {
        val iter = postOrderIterator()

        while (iter.hasNext) {
            consume(iter.next())
        }
    }

    fun index(): Tree<D> {
        var index: Int = 0

        traverseInPostOrder {
            it.index = index++
        }

        return this
    }

    /**
     * It is supposed that the tree is indexed.
     * No special check is performed.
     */
    fun countSubtreeSizes(): TreeInfo<D, Int> {
        val sizes = IndexedTreeInfo<D, Int>(this)
        val iter = postOrderIterator()

        while (iter.hasNext) {
            val node = iter.next()

            if (node.isLeaf) {
                sizes[node] = 1
            }
            else {
                var size = 1

                for (child in node) {
                    size += sizes[child]!!
                }

                sizes[node] = size
            }
        }

        return sizes
    }
}