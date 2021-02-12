package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class MusicVisualizer(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {


    private val random = Random()

    private val paint = Paint()
    private val animateView: Runnable = object : Runnable {
        override fun run() {

            //run every 100 ms
            postDelayed(this, 120)
            invalidate()
        }
    }

    init {
        removeCallbacks(animateView)
        post(animateView)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        canvas.drawRect(
            getDimensionInPixel(0).toFloat(),
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),
            getDimensionInPixel(3).toFloat(),
            (height - 5).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(4).toFloat(),
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),
            getDimensionInPixel(7).toFloat(),
            (height - 5).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(8).toFloat(),
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),
            getDimensionInPixel(11).toFloat(),
            (height - 5).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(12).toFloat(),//left,
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),//top,
            getDimensionInPixel(15).toFloat(),// right,
            (height - 5).toFloat(),//bottom
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(16).toFloat(),//left,
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),//top,
            getDimensionInPixel(19).toFloat(),// right,
            (height - 5).toFloat(),//bottom
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(20).toFloat(),//left,
            (height - (10 + random.nextInt(((height / 1.5f) - 5).toInt()))).toFloat(),//top,
            getDimensionInPixel(23).toFloat(),// right,
            (height - 5).toFloat(),//bottom
            paint
        )
    }

    fun setColor(color: Int) {
        paint.color = color
        invalidate()
    }


    private fun getDimensionInPixel(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == VISIBLE) {
            removeCallbacks(animateView)
            post(animateView)
        } else if (visibility == GONE) {
            removeCallbacks(animateView)
        }
    }


}