package net.devromik.tree

/**
 * @author Shulnyaev Roman
 */
class Tree<T>(var root: TreeNode<T> = TreeNode()) {
    fun levelOrderIterator(isLeaf: (TreeNode<T>) -> Boolean = { it.isLeaf }): LevelOrderIterator<T> = LevelOrderIterator(root, isLeaf)
    fun postOrderIterator(isLeaf: (TreeNode<T>) -> Boolean = { it.isLeaf }): PostOrderIterator<T> = PostOrderIterator(root, isLeaf)
}