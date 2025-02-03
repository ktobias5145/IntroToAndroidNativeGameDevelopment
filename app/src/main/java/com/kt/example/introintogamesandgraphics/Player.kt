package com.kt.example.introintogamesandgraphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Player(private val context: Context, private var positionx: Double, private var positiony:Double, private var radius:Double) {
    private val paint: Paint
    init{
        this.paint = Paint()
        var color = ContextCompat.getColor(context, R.color.player)
        paint.color = color
    }
    fun draw(canvas: Canvas) {
        canvas.drawCircle(positionx.toFloat(), positiony.toFloat(), radius.toFloat(), paint)
    }

    fun update() {

    }

    fun setPosition(positionX: Double, positionY: Double) {
    this.positionx = positionX
        this.positiony = positionY
    }

}
