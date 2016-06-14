package net.devromik.tree

import org.junit.Test
import org.junit.Assert.*

/**
 * @author Shulnyaev Roman
 */
class TreeTest {

    @Test fun canBeMadeOfRoot() {
        val root = root<Int> {}
        var tree = Tree(root)

        assertEquals(root, tree.root)

        tree = Tree()
        assertTrue(tree.root.isRoot)
        assertTrue(tree.root.isLeaf)
    }

    @Test fun canBeBuiltDeclaratively() {
        val tree: Tree<String> = Tree(
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
        )

        val root = tree.root

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

    @Test fun canBeIteratedInLevelOrder() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1") {
                        child("child_1_1_1")
                    }
                    child("child_1_2")
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                    child("child_2_2")
                }
            }
        )

        val iter = tree.levelOrderIterator()

        assertTrue(iter.hasNext)
        assertEquals("root", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_1_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2_1_1", iter.next().data)

        assertFalse(iter.hasNext)
    }

    @Test fun canBeIteratedInLevelOrderWithCustomLeafPredicate() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1") {
                        child("child_1_1_1")
                    }
                    child("child_1_2")
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                    child("child_2_2")
                }
            }
        )

        val iter = tree.levelOrderIterator({ it.data in setOf("child_1", "child_2") })

        assertTrue(iter.hasNext)
        assertEquals("root", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2", iter.next().data)

        assertFalse(iter.hasNext)
    }

    @Test fun canBeTraversedInLevelOrder() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1") {
                        child("child_1_1_1")
                    }
                    child("child_1_2")
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                    child("child_2_2")
                }
            }
        )

        val traversed = mutableListOf<String>()

        tree.traverseInLevelOrder {
            traversed.add(it.data!!)
        }

        assertEquals(
            listOf("root", "child_1", "child_2", "child_1_1", "child_1_2", "child_2_1", "child_2_2", "child_1_1_1", "child_2_1_1"),
            traversed)
    }

    @Test fun canBeIteratedInPostOrder() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1")
                    child("child_1_2") {
                        child("child_1_2_1") {
                            child("child_1_2_1_1")
                            child("child_1_2_1_2")
                        }
                    }
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                }
            }
        )

        val iter = tree.postOrderIterator()

        assertTrue(iter.hasNext)
        assertEquals("child_1_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_2_1_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_2_1_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_2_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2_1_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("root", iter.next().data)

        assertFalse(iter.hasNext)
    }

    @Test fun canBeIteratedInPostOrderWithCustomLeafPredicate() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1")
                    child("child_1_2") {
                        child("child_1_2_1") {
                            child("child_1_2_1_1")
                            child("child_1_2_1_2")
                        }
                    }
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                }
            }
        )

        val iter = tree.postOrderIterator({ it.data in setOf("child_1", "child_2") })

        assertTrue(iter.hasNext)
        assertEquals("child_1", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("child_2", iter.next().data)

        assertTrue(iter.hasNext)
        assertEquals("root", iter.next().data)

        assertFalse(iter.hasNext)
    }

    @Test fun canBeTraversedInPostOrder() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1")
                }
                child("child_2") {
                    child("child_2_1")
                }
            }
        )

        val traversed = mutableListOf<String>()

        tree.traverseInPostOrder {
            traversed.add(it.data!!)
        }

        assertEquals(
            listOf("child_1_1", "child_1", "child_2_1", "child_2", "root"),
            traversed)
    }

    @Test fun canBeIndexed() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1") {
                        child("child_1_1_1")
                    }
                    child("child_1_2")
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                    child("child_2_2")
                }
            }
        )

        tree.traverseInLevelOrder {
            assertFalse(it.isIndexed)
        }

        tree.index()

        val indexes = mutableListOf<Int>()

        tree.traverseInPostOrder {
            indexes.add(it.index)
        }

        assertEquals((0..8).toList(), indexes)
    }

    @Test fun canCountSizesOfSubtreesOfIndexedTree() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1") {
                    child("child_1_1") {
                        child("child_1_1_1")
                    }
                    child("child_1_2")
                }
                child("child_2") {
                    child("child_2_1") {
                        child("child_2_1_1")
                    }
                    child("child_2_2")
                }
            }
        )

        tree.index()
        val sizes = tree.countSubtreeSizes()

        assertEquals(9, sizes[tree.root])

        val child_1 = tree.root.childAt(0)
        assertEquals(4, sizes[child_1])

        val child_1_1 = child_1.childAt(0)
        assertEquals(2, sizes[child_1_1])

        val child_1_1_1 = child_1_1.childAt(0)
        assertEquals(1, sizes[child_1_1_1])

        val child_1_2 = child_1.childAt(1)
        assertEquals(1, sizes[child_1_2])

        val child_2 = tree.root.childAt(1)
        assertEquals(4, sizes[child_2])

        val child_2_1 = child_2.childAt(0)
        assertEquals(2, sizes[child_2_1])

        val child_2_2 = child_2.childAt(1)
        assertEquals(1, sizes[child_2_2])
    }
}
















