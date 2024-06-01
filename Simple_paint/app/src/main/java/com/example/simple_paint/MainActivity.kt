package com.example.simple_paint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val LINE = 1
        const val CIRCLE = 2
        const val RECT = 3
        var curShape = LINE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SimplePainter(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Draw Line")
        menu?.add(0, 2, 0, "Draw Circle")
        menu?.add(0, 3, 0, "Draw Rectangle")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                curShape = LINE
                return true
            }
            2 -> {
                curShape = CIRCLE
                return true
            }
            3 -> {
                curShape = RECT
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SimplePainter(context: Context) : View(context) {
        private var startX = -1f
        private var startY = -1f
        private var stopX = -1f
        private var stopY = -1f
        private val paint = Paint()
        private var bitmap: Bitmap? = null
        private var canvasBitmap: Canvas? = null
        private val drawingHistory = mutableListOf<DrawingAction>()
        private var currentDrawingAction: DrawingAction? = null

        init {
            paint.isAntiAlias = true
            paint.strokeWidth = 5f
            paint.style = Paint.Style.STROKE
            paint.color = Color.RED
        }

        data class DrawingAction(
            val shape: Int,
            val startX: Float,
            val startY: Float,
            val stopX: Float,
            val stopY: Float
        )

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    stopX = event.x
                    stopY = event.y
                    currentDrawingAction = DrawingAction(curShape, startX, startY, stopX, stopY)
                    this.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    stopX = event.x
                    stopY = event.y
                    drawingHistory.add(DrawingAction(curShape, startX, startY, stopX, stopY))
                    currentDrawingAction = null
                    this.invalidate()
                }
            }
            return true
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                canvasBitmap = Canvas(bitmap!!)
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvasBitmap?.drawColor(Color.WHITE) // Clear the canvas
            drawingHistory.forEach { action ->
                drawShape(canvasBitmap!!, action)
            }
            currentDrawingAction?.let {
                drawShape(canvasBitmap!!, it)
            }
            canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
        }

        private fun drawShape(canvas: Canvas, action: DrawingAction) {
            when (action.shape) {
                LINE -> {
                    canvas.drawLine(action.startX, action.startY, action.stopX, action.stopY, paint)
                }
                CIRCLE -> {
                    val radius = Math.sqrt(
                        Math.pow((action.stopX - action.startX).toDouble(), 2.0) + Math.pow((action.stopY - action.startY).toDouble(), 2.0)
                    ).toFloat()
                    canvas.drawCircle(action.startX, action.startY, radius, paint)
                }
                RECT -> {
                    canvas.drawRect(action.startX, action.startY, action.stopX, action.stopY, paint)
                }
            }
        }
    }
}