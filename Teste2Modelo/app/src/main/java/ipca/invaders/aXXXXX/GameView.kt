package ipca.invaders.aXXXXX

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView  : SurfaceView, Runnable {

    var isPlaying = false
    var gameThread : Thread? = null
    var surfaceHolder : SurfaceHolder
    var paint : Paint
    var canvas : Canvas? = null

    var circles : MutableList<Circle> = arrayListOf()

    constructor(context: Context, width : Int, height: Int) : super(context) {

        surfaceHolder = holder
        paint = Paint()
    }

    override fun run() {
        while (isPlaying){
            update()
            draw()
            control()
        }
    }

    fun update(){
        for ( c in circles) {
            c.update()
            for (cOther in circles ){
                if (c != cOther) {
                   if (c.detectCollision.intersect(cOther.detectCollision)){
                       c.shouldBeRemoved = true
                       cOther.shouldBeRemoved = true
                   }
                }
            }
        }

        circles = circles.filter { c ->
            !c.shouldBeRemoved
        }.toMutableList()
    }

    fun draw() {
        if (surfaceHolder.surface.isValid){
            canvas = surfaceHolder.lockCanvas()
            canvas?.drawColor(Color.BLACK)
            paint.color = Color.RED
            for ( c in circles) {
                canvas?.drawOval(c.detectCollision,paint)
            }
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control(){
        Thread.sleep(17)
    }

    fun resume(){
        gameThread = Thread(this)
        gameThread?.start()
        isPlaying = true
    }

    fun pause() {
        isPlaying = false
        gameThread?.join()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                circles.add(Circle(context,width,height, event.x, event.y))
                return true
            }
            MotionEvent.ACTION_UP -> {

                return true
            }
        }
        return false
    }

}