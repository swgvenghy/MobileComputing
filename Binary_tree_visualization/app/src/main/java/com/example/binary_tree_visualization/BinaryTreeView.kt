package com.example.binary_tree_visualization

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BinaryTreeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var heap = emptyList<Int>()
    private val nodePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.YELLOW
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    fun setHeap(heap: List<Int>) {
        this.heap = heap
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (heap.isEmpty()) return

        val width = width
        val nodeRadius = 50f

        fun drawNode(value: Int, x: Float, y: Float) {
            // Draw circle
            canvas.drawCircle(x, y, nodeRadius, nodePaint)

            // Draw text in the center
            val textY = y - ((textPaint.descent() + textPaint.ascent()) / 2)
            canvas.drawText(value.toString(), x, textY, textPaint)
        }

        fun drawTree(index: Int, x: Float, y: Float, dx: Float, dy: Float) {
            if (index >= heap.size) return
            drawNode(heap[index], x, y)
            val leftIndex = 2 * index + 1
            val rightIndex = 2 * index + 2
            if (leftIndex < heap.size) {
                canvas.drawLine(x, y, x - dx, y + dy, nodePaint)
                drawTree(leftIndex, x - dx, y + dy, dx / 2, dy)
            }
            if (rightIndex < heap.size) {
                canvas.drawLine(x, y, x + dx, y + dy, nodePaint)
                drawTree(rightIndex, x + dx, y + dy, dx / 2, dy)
            }
        }

        drawTree(0, width / 2f, nodeRadius * 2, width / 4f, nodeRadius * 3)
    }
}