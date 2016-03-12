package net.devromik.tree

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Shulnyaev Roman
 */
class TreeNodeTest {

    @Test fun canBeMadeOfParentChildrenAndData() {
        // A root, with no children, with no data.
        var node = TreeNode<Int>()

        assertTrue(node.isRoot)
        assertNull(node.parent)

        assertEquals(0, node.childCount)
        assertTrue(node.isLeaf)
        assertFalse(node.hasOnlyChild)

        assertNull(node.leftmostChild)
        assertNull(node.rightmostChild)

        assertNull(node.data)

        // A root, with an only child, with data.
        val child_1 = TreeNode<Int>()
        node = TreeNode(children = mutableListOf(child_1), data = 1)

        assertTrue(node.isRoot)
        assertNull(node.parent)

        assertEquals(1, node.childCount)
        assertFalse(node.isLeaf)
        assertTrue(node.hasOnlyChild)

        assertEquals(child_1, node.childAt(0))

        assertEquals(child_1, node.leftmostChild)
        assertEquals(child_1, node.rightmostChild)

        assertEquals(node, child_1.parent)

        assertEquals(1, node.data)

        // A root, with several children, with no data.
        val child_2 = TreeNode<Int>()
        node = TreeNode(children = mutableListOf(child_1, child_2))

        assertTrue(node.isRoot)
        assertNull(node.parent)

        assertEquals(2, node.childCount)
        assertFalse(node.isLeaf)
        assertFalse(node.hasOnlyChild)

        assertEquals(child_1, node.childAt(0))
        assertEquals(child_2, node.childAt(1))

        assertEquals(child_1, node.leftmostChild)
        assertEquals(child_2, node.rightmostChild)

        assertEquals(node, child_1.parent)
        assertEquals(node, child_2.parent)

        assertNull(node.data)

        // Not a root, with no children, with data.
        val parent = TreeNode<Int>()
        node = TreeNode(parent, data = 2)

        assertFalse(node.isRoot)
        assertEquals(parent, node.parent)

        assertEquals(0, node.childCount)
        assertTrue(node.isLeaf)
        assertFalse(node.hasOnlyChild)

        assertNull(node.leftmostChild)
        assertNull(node.rightmostChild)

        assertEquals(2, node.data)

        // Not a root, with an only child, with no data.
        node = TreeNode(parent, children = mutableListOf(child_1))

        assertFalse(node.isRoot)
        assertEquals(parent, node.parent)

        assertEquals(1, node.childCount)
        assertFalse(node.isLeaf)
        assertTrue(node.hasOnlyChild)

        assertEquals(child_1, node.childAt(0))

        assertEquals(child_1, node.leftmostChild)
        assertEquals(child_1, node.rightmostChild)

        assertEquals(node, child_1.parent)

        assertNull(node.data)

        // Not a root, with several children, with data.
        node = TreeNode(parent, children = mutableListOf(child_1, child_2), data = 3)

        assertFalse(node.isRoot)
        assertEquals(parent, node.parent)

        assertEquals(2, node.childCount)
        assertFalse(node.isLeaf)
        assertFalse(node.hasOnlyChild)

        assertEquals(child_1, node.childAt(0))
        assertEquals(child_2, node.childAt(1))

        assertEquals(child_1, node.leftmostChild)
        assertEquals(child_2, node.rightmostChild)

        assertEquals(node, child_1.parent)
        assertEquals(node, child_2.parent)

        assertEquals(3, node.data)
    }

    @Test fun childCanBeAppended_ParentIsSetAutomatically() {
        val parent = TreeNode<String>()

        assertEquals(0, parent.childCount)
        assertTrue(parent.isLeaf)
        assertFalse(parent.hasOnlyChild)

        assertNull(parent.leftmostChild)
        assertNull(parent.rightmostChild)

        val child_1 = TreeNode<String>()
        parent.appendChild(child_1)

        assertEquals(1, parent.childCount)
        assertFalse(parent.isLeaf)
        assertTrue(parent.hasOnlyChild)

        assertEquals(child_1, parent.childAt(0))

        assertEquals(child_1, parent.leftmostChild)
        assertEquals(child_1, parent.rightmostChild)

        assertEquals(parent, child_1.parent)

        val child_2 = TreeNode<String>()
        parent.appendChild(child_2)

        assertEquals(2, parent.childCount)
        assertFalse(parent.isLeaf)
        assertFalse(parent.hasOnlyChild)

        assertEquals(child_1, parent.childAt(0))
        assertEquals(child_2, parent.childAt(1))

        assertEquals(child_1, parent.leftmostChild)
        assertEquals(child_2, parent.rightmostChild)

        assertEquals(parent, child_1.parent)
    }

    @Test fun canBeBuiltDeclaratively() {
        val root: TreeNode<String> =
            root("root") {
                child("child_1") {
                    child("child_1_1")
                    child()
                }
                child("child_2") {
                    child("child_2_1")
                    child("child_2_2")
                    child() {
                        child("child_2_3_1")
                    }
                }
            }

        assertTrue(root.isRoot)
        assertEquals(2, root.childCount)
        assertEquals("root", root.data)

        // child_1
        val child_1 = root.childAt(0)
        assertEquals(root, child_1.parent)
        assertEquals(2, child_1.childCount)
        assertEquals("child_1", child_1.data)

        val child_1_1 = child_1.childAt(0)
        assertEquals(child_1, child_1_1.parent)
        assertTrue(child_1_1.isLeaf)
        assertEquals("child_1_1", child_1_1.data)

        val child_1_2 = child_1.childAt(1)
        assertEquals(child_1, child_1_2.parent)
        assertTrue(child_1_2.isLeaf)
        assertNull(child_1_2.data)

        // child_2
        val child_2 = root.childAt(1)
        assertEquals(root, child_2.parent)
        assertEquals(3, child_2.childCount)
        assertEquals("child_2", child_2.data)

        val child_2_1 = child_2.childAt(0)
        assertEquals(child_2, child_2_1.parent)
        assertTrue(child_2_1.isLeaf)
        assertEquals("child_2_1", child_2_1.data)

        val child_2_2 = child_2.childAt(1)
        assertEquals(child_2, child_2_2.parent)
        assertTrue(child_2_2.isLeaf)
        assertEquals("child_2_2", child_2_2.data)

        val child_2_3 = child_2.childAt(2)
        assertEquals(child_2, child_2_3.parent)
        assertTrue(child_2_3.hasOnlyChild)
        assertNull(child_2_3.data)

        val child_2_3_1 = child_2_3.childAt(0)
        assertEquals(child_2_3, child_2_3_1.parent)
        assertTrue(child_2_3_1.isLeaf)
        assertEquals("child_2_3_1", child_2_3_1.data)
    }

    @Test fun childCanBeRemoved() {
        val root: TreeNode<String> =
            root() {
                child()
                child()
                child()
            }

        assertEquals(3, root.childCount)

        val child_1 = root.childAt(0)
        assertEquals(root, child_1.parent)

        val child_2 = root.childAt(1)
        assertEquals(root, child_2.parent)

        val child_3 = root.childAt(2)
        assertEquals(root, child_3.parent)

        root.removeChild(child_2)

        assertEquals(2, root.childCount)
        assertEquals(child_1, root.childAt(0))
        assertTrue(child_2.isRoot)
        assertEquals(child_3, root.childAt(1))

        root.removeChild(child_3)
        assertTrue(root.hasOnlyChild)
        assertEquals(child_1, root.childAt(0))
        assertTrue(child_3.isRoot)

        root.removeChild(child_1)
        assertTrue(root.isLeaf)
        assertTrue(child_3.isRoot)
    }

    @Test fun allChildrenCanBeRemoved() {
        val root: TreeNode<String> =
            root() {
                child()
                child()
                child()
            }

        assertFalse(root.isLeaf)

        val child_1 = root.childAt(0)
        assertEquals(root, child_1.parent)

        val child_2 = root.childAt(1)
        assertEquals(root, child_2.parent)

        val child_3 = root.childAt(2)
        assertEquals(root, child_3.parent)

        root.removeAllChildren()

        assertTrue(root.isLeaf)
        assertTrue(child_1.isRoot)
        assertTrue(child_2.isRoot)
        assertTrue(child_3.isRoot)
    }
}








