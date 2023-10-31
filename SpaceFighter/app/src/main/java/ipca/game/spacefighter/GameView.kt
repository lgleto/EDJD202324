package ipca.game.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var isPlaying = false
    var gameThread : Thread? = null

    var player : Player

    var surfaceHolder : SurfaceHolder
    var paint : Paint
    var canvas : Canvas? = null

    constructor(context: Context, width : Int, height: Int) : super(context) {

        surfaceHolder = holder
        paint = Paint()

        player = Player(context, width, height)
    }

    override fun run() {
        while (isPlaying){
            update()
            draw()
            control()
        }
    }

    fun update(){
        player.update()
    }

    fun draw() {
        if (surfaceHolder.surface.isValid){
            canvas = surfaceHolder.lockCanvas()

            canvas?.drawColor(Color.BLACK)

            canvas?.drawBitmap(player.bitmap, player.x , player.y , paint)


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
                player.isBoosting = true
                return true
            }
            MotionEvent.ACTION_UP -> {
                player.isBoosting = false
                return true
            }
        }
        return false
    }

}