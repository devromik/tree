package net.devromik.tree

import net.devromik.tree.TreeNode.Companion.NOT_INDEXED

/**
 * @author Shulnyaev Roman
 */
class TreeNode<D>(
    parent: TreeNode<D>? = null,
    private val children: MutableList<TreeNode<D>> = mutableListOf(),
    var data: D? = null,
    internal var index: Int = NOT_INDEXED) : Iterable<TreeNode<D>> {

    // ****************************** //

    companion object {
        const val NOT_INDEXED: Int = -1
    }

    // ****************************** //

    var parent: TreeNode<D>? = parent
        internal set(value) { field = value }

    val isIndexed: Boolean
        get() = index != NOT_INDEXED

    // ****************************** //

    init {
        children.forEach { it.parent = this }
    }

    // ****************************** //

    val isRoot: Boolean get() = parent == null

    fun child(data: D? = null, init: TreeNode<D>.() -> Unit = {}) {
        val child: TreeNode<D> = TreeNode(data = data)
        child.init()
        appendChild(child)
    }

    fun appendChild(child: TreeNode<D>) {
        children.add(child)
        child.parent = this
    }

    val childCount: Int get() = children.size
    val isLeaf: Boolean get() = children.isEmpty()
    val hasOnlyChild: Boolean get() = childCount == 1

    override fun iterator(): Iterator<TreeNode<D>> = children.iterator()
    fun childAt(pos: Int): TreeNode<D> = children[pos]
    val leftmostChild: TreeNode<D>? get() = if (!isLeaf) childAt(0) else null
    val rightmostChild: TreeNode<D>? get() = if (!isLeaf) childAt(childCount - 1) else null

    fun removeChild(child: TreeNode<D>) {
        children.remove(child)
        child.parent = null
    }

    fun removeAllChildren() {
        children.forEach { it.parent = null }
        children.clear()
    }
}

fun <D> root(data: D? = null, init: TreeNode<D>.() -> Unit = {}): TreeNode<D> {
    val root: TreeNode<D> = TreeNode(data = data)
    root.init()
    return root
}
