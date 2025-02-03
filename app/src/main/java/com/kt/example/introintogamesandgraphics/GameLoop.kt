package com.kt.example.introintogamesandgraphics

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(private val game: Game, private val surfaceHolder: SurfaceHolder) : Thread() {
    private var isRunning = false
    private var averageUPS:Double = 0.0
    private var averageFPS:Double = 0.0
    private val MAX_UPS = 60.0
    private val UPS_PERIOD = 1E+3 / MAX_UPS

    fun startLoop(){
    isRunning = true
        start()
    }

    fun getAverageUPS(): Double {
        return averageUPS
    }

    fun getAverageFPS(): Double {
        return  averageFPS
    }

    override fun run() {
        super.run()
        var updateCount = 0
        var frameCount = 0

        var startTime:Long
        var elapsedTime:Long
        var sleepTime:Long

        var canvas:Canvas? = null
        startTime = System.currentTimeMillis()
        while(isRunning){
            try{
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder){
                    game.update()
                    updateCount++
                    game.draw(canvas)
                }
            }catch (e:IllegalArgumentException){
                e.printStackTrace()
            } finally {
                if(canvas != null)
                try{
                    surfaceHolder.unlockCanvasAndPost(canvas)
                    frameCount++
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            //update and render game

            //Pause game loop to not exceed target IPS
            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if(sleepTime > 0){
                try{
                    sleep(sleepTime)
                }catch (e: InterruptedException){
                    e.printStackTrace()
                }
            }
            //skip frames to keep up with target UPS
            while(sleepTime < 0  && updateCount < MAX_UPS - 1){
                game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis()- startTime
                sleepTime = ((updateCount * UPS_PERIOD - elapsedTime).toLong())
            }

            //calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime
            if(elapsedTime >= 1000){
                averageUPS = updateCount / (1E-3 * elapsedTime)
                averageFPS = frameCount / (1E-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }
}
