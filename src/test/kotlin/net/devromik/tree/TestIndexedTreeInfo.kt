package net.devromik.tree

import org.junit.Test
import org.junit.Assert.*

/**
 * @author Shulnyaev Roman
 */
class TestIndexedTreeInfo {

    @Test fun providesInfoOnTreeNodes() {
        val tree: Tree<String> = Tree(
            root("root") {
                child("child_1")
                child("child_2")
            }
        ).index()

        val root = tree.root
        val child_1 = root.childAt(0)
        val child_2 = root.childAt(1)

        val info = IndexedTreeInfo<String, Int>(tree)

        assertFalse(info.has(root))
        assertNull(info[root])

        info[root] = 0

        assertTrue(info.has(root))
        assertEquals(0, info[root])

        assertFalse(info.has(child_1))
        assertNull(info[child_1])

        info[child_1] = 1

        assertTrue(info.has(child_1))
        assertEquals(1, info[child_1])

        assertFalse(info.has(child_2))
        assertNull(info[child_2])

        info[child_2] = 2

        assertTrue(info.has(child_2))
        assertEquals(2, info[child_2])
    }
}