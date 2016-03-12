package net.devromik.tree

/**
 * @author Shulnyaev Roman
 */
class TreeNode<T>(
    parent: TreeNode<T>? = null,
    private val children: MutableList<TreeNode<T>> = mutableListOf(),
    var data: T? = null) {

    // ****************************** //

    var parent: TreeNode<T>? = parent
        internal set(value) { field = value }

    // ****************************** //

    init {
        children.forEach { it.parent = this }
    }

    // ****************************** //

    val isRoot: Boolean get() = parent == null

    fun child(data: T? = null, init: TreeNode<T>.() -> Unit = {}) {
        val child: TreeNode<T> = TreeNode(data = data)
        child.init()
        appendChild(child)
    }

    fun appendChild(child: TreeNode<T>) {
        children.add(child)
        child.parent = this
    }

    val childCount: Int get() = children.size
    val isLeaf: Boolean get() = children.isEmpty()
    val hasOnlyChild: Boolean get() = childCount == 1

    fun childAt(pos: Int): TreeNode<T> = children[pos]
    val leftmostChild: TreeNode<T>? get() = if (!isLeaf) childAt(0) else null
    val rightmostChild: TreeNode<T>? get() = if (!isLeaf) childAt(childCount - 1) else null

    fun removeChild(child: TreeNode<T>) {
        children.remove(child)
        child.parent = null
    }

    fun removeAllChildren() {
        children.forEach { it.parent = null }
        children.clear()
    }
}

fun <T> root(data: T? = null, init: TreeNode<T>.() -> Unit = {}): TreeNode<T> {
    val root: TreeNode<T> = TreeNode(data = data)
    root.init()
    return root
}
