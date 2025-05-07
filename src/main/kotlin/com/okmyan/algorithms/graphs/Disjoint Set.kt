package com.okmyan.algorithms.graphs

/**
 * The idea is to store root nodes in a rootArray.
 * It gives us this time complexity:
 * * We find a root for O(1)
 * * We union nodes for O(N)
 * * We check if nodes are connected for O(1)
 */
private class QuickFind(size: Int) {

    // rootArray stores roots (heads) of vertices. The index is a vertex. The value is a root of the vertex
    private val rootArray = Array(size) { it }

    /**
     * O(1)
     *
     * Each call to find will require O(1) time since we are just accessing an element of the array at the given index
     */
    fun findRoot(vertex: Int): Int {
        require(vertex < rootArray.size) { "Vertex must be less than rootArray size" }
        return rootArray[vertex]
    }

    /**
     * O(N)
     *
     * Each call to union will require O(N) time because we need to traverse through the entire array and update
     * the root vertices for all the vertices of the set that is going to be merged into another set
     */
    fun union(a: Int, b: Int) {
        val aRoot = findRoot(a)
        val bRoot = findRoot(b)
        if (aRoot != bRoot) {
            rootArray.filter { vertex ->
                rootArray[vertex] == bRoot
            }.forEach { vertex ->
                rootArray[vertex] = aRoot
            }
        }
    }

    /**
     * The connected operation takes O(1) time since it involves the two find calls and the equality check operation
     */
    fun connected(a: Int, b: Int) = findRoot(a) == findRoot(b)
}

/**
 * The idea is to store parent nodes in a rootArray.
 * It gives us this time complexity:
 * * We find a root for O(N)
 * * We union nodes for O(1)
 * * We check if nodes are connected for O(N)
 */
private class QuickUnion(size: Int) {

    // rootArray stores parents of vertices. The index is a vertex. The value is a parent of the vertex
    private val rootArray = Array(size) { it }

    /**
     * O(N)
     *
     * For the find operation, in the worst-case scenario, we need to traverse every vertex to find the root for
     * the input vertex. The maximum number of operations to get the root vertex would be no more than the tree's height,
     * so it will take O(N) time
     */
    fun findRoot(vertex: Int): Int {
        require(vertex < rootArray.size) { "Vertex must be less than rootArray size" }

        var currentHead = vertex
        while (currentHead != rootArray[currentHead]) {
            currentHead = rootArray[currentHead]
        }
        return currentHead
    }

    /**
     * O(N)
     *
     * The union operation consists of two find operations which (only in the worst-case) will take O(N) time, and two
     * constant time operations, including the equality check and updating the array value at a given index. Therefore,
     * the union operation also costs O(N) in the worst-case
     */
    fun union(a: Int, b: Int) {
        val rootA = findRoot(a)
        val rootB = findRoot(b)
        if (rootA != rootB) {
            rootArray[rootB] = rootA
        }
    }

    /**
     * O(N)
     *
     * The connected operation also takes O(N) time in the worst-case since it involves two find calls
     */
    fun connected(a: Int, b: Int) = findRoot(a) == findRoot(b)

}

fun main() {
    val quickFind = QuickUnion(10)
    // 1-2-5-6-7 3-8-9-4
    quickFind.union(1, 2)
    quickFind.union(2, 5)
    quickFind.union(5, 6)
    quickFind.union(6, 7)
    quickFind.union(3, 8)
    quickFind.union(8, 9)

    require(quickFind.connected(1, 5))
    require(quickFind.connected(5, 7))
    require(!quickFind.connected(4, 9))

    quickFind.union(9, 4)
    require(quickFind.connected(4, 9))
}
