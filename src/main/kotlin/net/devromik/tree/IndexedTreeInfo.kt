package net.devromik.tree

/**
 * @author Shulnyaev Roman
 */
class IndexedTreeInfo<D, I>(tree: Tree<D>) : TreeInfo<D, I> {

    private val nodeIndexToInfo = arrayOfNulls<Any>(tree.root.index + 1)

    // ****************************** //

    override fun has(node: TreeNode<D>): Boolean = nodeIndexToInfo[node.index] != null

    override operator fun set(node: TreeNode<D>, info: I?) {
        nodeIndexToInfo[node.index] = info
    }

    @Suppress("UNCHECKED_CAST")
    override operator fun get(node: TreeNode<D>): I? = nodeIndexToInfo[node.index] as I?
}