package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

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
            (height - (10 + random.nextInt(((height / 1f) - 25).toInt()))).toFloat(),
            getDimensionInPixel(3).toFloat(),
            (height - 30).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(10).toFloat(),
            (height - (10 + random.nextInt(((height / 1f) - 25).toInt()))).toFloat(),
            getDimensionInPixel(13).toFloat(),
            (height - 30).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(20).toFloat(),
            (height - (10 + random.nextInt(((height / 1f) - 25).toInt()))).toFloat(),
            getDimensionInPixel(23).toFloat(),
            (height - 30).toFloat(),
            paint
        )
        canvas.drawRect(
            getDimensionInPixel(30).toFloat(),//left,
            (height - (10 + random.nextInt(((height / 1f) - 25).toInt()))).toFloat(),//top,
            getDimensionInPixel(33).toFloat(),// right,
            (height - 30).toFloat(),//bottom
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