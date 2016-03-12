package net.devromik.tree

/**
 * @author Shulnyaev Roman
 */
interface TreeInfo<D, I> {
    fun has(node: TreeNode<D>): Boolean
    operator fun set(node: TreeNode<D>, info: I?)
    operator fun get(node: TreeNode<D>): I?
}