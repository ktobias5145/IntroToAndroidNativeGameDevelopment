package com.kt.example.introintogamesandgraphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat

class Game(context: Context?) : SurfaceView(context) , SurfaceHolder.Callback{
    private val gameLoop: GameLoop
    private val internalContext:Context
    private val player:Player
    init{
        val surfaceHolder: SurfaceHolder = holder
        surfaceHolder.addCallback(this)
         gameLoop = GameLoop(this, surfaceHolder)
        player = Player(this.context, 500.0,500.0,30.0)
        internalContext = this.context
        isFocusable = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Handle touch events
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {player.setPosition(event.x.toDouble(), event.y.toDouble())
                return true}
            MotionEvent.ACTION_MOVE -> {
                player.setPosition(event.x.toDouble(), event.y.toDouble())
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        gameLoop.startLoop()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawUPS(canvas)
        drawFPS(canvas)
        player.draw(canvas)
    }
     fun drawUPS(canvas: Canvas){
        val averageUPS: String = gameLoop.getAverageUPS().toString()
        val paint: Paint = Paint()
        val color:Int = ContextCompat.getColor(internalContext, R.color.cyan)
         paint.color = color
         paint.textSize = 50f
        canvas.drawText("UPS: $averageUPS", 100f,100f, paint)
    }
     fun drawFPS(canvas: Canvas){
        val averageUPS: String = gameLoop.getAverageFPS().toString()
        val paint: Paint = Paint()
        val color:Int = ContextCompat.getColor(internalContext, R.color.cyan)
         paint.color = color
         paint.textSize = 50f
        canvas.drawText("FPS: $averageUPS", 100f,200f, paint)
    }

    fun update() {
    player.update()
    }
}