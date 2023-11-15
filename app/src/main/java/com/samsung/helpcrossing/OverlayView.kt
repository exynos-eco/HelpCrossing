// Copyright (c) 2023 Samsung Electronics Co. LTD. Released under the MIT License.

package com.samsung.helpcrossing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.samsung.helpCrossing.R
import com.samsung.helpcrossing.data.DetectionResult
import com.samsung.helpcrossing.executor.CrossingEvent
import kotlin.math.max
import kotlin.math.min


class OverlayView(
    context: Context?, attrs: AttributeSet?
) : View(context, attrs) {
    private var results: List<DetectionResult> = emptyList()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var scale = 1F
    private var offset = 0F

    private val crossingEvent = CrossingEvent(context!!)
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context!!)
    private val valueObjects: List<String>? =
        sharedPreferences.getStringSet("objects", emptySet())?.toList()

    init {
        initPaints()
        crossingEvent.setTTS()
    }

    @SuppressLint("ResourceAsColor")
    private fun initPaints() {
        with(textBackgroundPaint) {
            color = R.color.samsung_light
            style = Paint.Style.FILL
            textSize = 50f
        }

        with(textPaint) {
            color = Color.CYAN
            style = Paint.Style.FILL
            textSize = 40f
        }

        with(boxPaint) {
            color = ContextCompat.getColor(context!!, R.color.samsung)
            strokeWidth = 8F
            style = Paint.Style.STROKE
        }
    }

    fun setResults(detectionResults: List<DetectionResult>) {
        results = detectionResults
        scale = min(width.toFloat(), height.toFloat())
        offset = (max(width.toFloat(), height.toFloat()) - min(width.toFloat(), height.toFloat())) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        results.forEach { result ->
            val boundingBox = result.requireBoundingBox()
            val scaledBoundingBox = getScaledCoordinates(boundingBox)

            if (valueObjects!!.contains(result.score.first)) {
                canvas.drawRoundRect(scaledBoundingBox, 40F, 40F, boxPaint)
                drawText(canvas, result, scaledBoundingBox)
            }
        }
    }

    private fun getScaledCoordinates(boundingBox: RectF): RectF {
        val left = boundingBox.left * scale
        val top = boundingBox.top * scale + offset
        val right = boundingBox.right * scale
        val bottom = boundingBox.bottom * scale + offset

        return RectF(left, top, right, bottom)
    }

    private fun drawText(
        canvas: Canvas, result: DetectionResult, boundingBox: RectF
    ) {
        val left = boundingBox.left
        val top = boundingBox.top
        val drawableText = result.score.first + " " + String.format("%.2f", result.score.second)
        val textBounds = Rect()

        textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, textBounds)
        canvas.drawRoundRect(
            left,
            top,
            left + textBounds.width() + 8,
            top + textBounds.height() + 8,
            40F,
            40F,
            textBackgroundPaint
        )
        canvas.drawText(drawableText, left, top + textBounds.height(), textPaint)
    }

    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        results = emptyList()
        invalidate()
        initPaints()
    }

    // Vehicle Detection
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> crossingEvent.countCars2Sec(countCar())
        }

        return super.onTouchEvent(event)
    }

    fun countCar(): Int {
        return results.count { valueObjects?.contains(it.score.first) == true }
    }
}