package com.example.binary_tree_visualization

class MaxHeap {
    private val heap = mutableListOf<Int>()

    fun push(value: Int) {
        heap.add(value)
        heapifyUp(heap.size - 1)
    }

    fun pop(): Int? {
        if (heap.isEmpty()) return null
        val root = heap[0]
        heap[0] = heap.removeAt(heap.size - 1)
        heapifyDown(0)
        return root
    }

    fun getHeap(): List<Int> {
        return heap
    }

    private fun heapifyUp(index: Int) {
        var i = index
        while (i > 0) {
            val parentIndex = (i - 1) / 2
            if (heap[i] > heap[parentIndex]) {
                heap[i] = heap[parentIndex].also { heap[parentIndex] = heap[i] }
                i = parentIndex
            } else break
        }
    }

    private fun heapifyDown(index: Int) {
        var i = index
        while (true) {
            val leftChildIndex = 2 * i + 1
            val rightChildIndex = 2 * i + 2
            var largest = i

            if (leftChildIndex < heap.size && heap[leftChildIndex] > heap[largest]) {
                largest = leftChildIndex
            }
            if (rightChildIndex < heap.size && heap[rightChildIndex] > heap[largest]) {
                largest = rightChildIndex
            }
            if (largest == i) break

            heap[i] = heap[largest].also { heap[largest] = heap[i] }
            i = largest
        }
    }
}