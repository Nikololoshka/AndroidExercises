package com.vereshchagin.nikolay.android.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import kotlin.random.Random

/**
 * Реализовать небольшую кастомную вью в виде прямоугольника.
 * При каждом нажатии на него он заполняется на 10%, цвет залитой части рандомно меняется.
 * При достижении 100% все начинается заново.
 */
class RandomFilledView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var currentFillProgress = 0
    private val fillColors: MutableList<Int> = MutableList(MAX_FILL_PROGRESS) { Color.TRANSPARENT }

    init {
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 4f
        borderPaint.color = Color.BLACK

        fillPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = paddingLeft + paddingRight + suggestedMinimumWidth
        val w = resolveSize(minWidth, widthMeasureSpec)

        val minHeight = paddingBottom + paddingTop + suggestedMinimumHeight
        val h = resolveSize(minHeight, heightMeasureSpec)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()

        val coloredPathWidth = width / fillColors.size
        for ((index, color) in fillColors.withIndex()) {
            if (color == Color.TRANSPARENT) {
                break
            }

            fillPaint.color = color

            canvas.drawRect(
                index * coloredPathWidth,
                0f,
                (index + 1) * coloredPathWidth,
                height,
                fillPaint
            )
        }

        canvas.drawRect(0f, 0f, width, height, borderPaint)
    }

    override fun performClick(): Boolean {
        super.performClick()
        onViewClicked()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                performClick(); true
            }

            else -> false
        }
    }

    private fun onViewClicked() {
        if (currentFillProgress == MAX_FILL_PROGRESS) {
            currentFillProgress = 0
            fillColors.fill(Color.TRANSPARENT)
            invalidate()

            return
        }

        fillColors[currentFillProgress++] = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        invalidate()
    }

    override fun onSaveInstanceState(): Parcelable {
        val state = super.onSaveInstanceState()
        return bundleOf(
            BASE_STATE to state,
            FILL_PROGRESS to currentFillProgress,
            COLORS to fillColors
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            currentFillProgress = state.getInt(FILL_PROGRESS)
            state.getIntegerArrayList(COLORS)?.forEachIndexed { index, value ->
                fillColors[index] = value
            }

            val baseState = BundleCompat.getParcelable(state, BASE_STATE, Parcelable::class.java)
            super.onRestoreInstanceState(baseState)

        } else {
            super.onRestoreInstanceState(state)
        }
    }

    companion object {
        private const val MAX_FILL_PROGRESS = 10

        private const val BASE_STATE = "base_state"
        private const val FILL_PROGRESS = "fill_progress"
        private const val COLORS = "colors"
    }
}