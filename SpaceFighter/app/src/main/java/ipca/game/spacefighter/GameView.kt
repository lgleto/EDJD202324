package ipca.game.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var isPlaying = false
    var gameThread : Thread? = null

    var surfaceHolder : SurfaceHolder
    var paint : Paint
    var paintTxt : Paint
    var canvas : Canvas? = null

    var player : Player
    var stars = arrayListOf<Star>()
    var enemies = arrayListOf<Enemy>()
    var boom : Boom

    var score = 0

    constructor(context: Context, width : Int, height: Int) : super(context) {

        surfaceHolder = holder
        paint = Paint()

        paintTxt = Paint()
        paintTxt.textSize = 156f
        paintTxt.color = Color.GREEN

        player = Player(context, width, height)
        for ( i in 1..100){
            stars.add(Star(context,width, height))
        }
        for ( i in 1..3){
            enemies.add(Enemy(context,width, height))
        }
        boom = Boom(context, width, height)

    }

    override fun run() {
        while (isPlaying){
            update()
            draw()
            control()
        }
    }

    fun update(){
        boom.x = -300f
        boom.y = -300f

        player.update()
        for (s in stars) {
            s.update(player.speed)
        }

        for (e in enemies) {
            e.update(player.speed)
            if (e.detectCollision.intersect(player.detectCollision)){
                boom.x = e.x
                boom.y = e.y

                e.x = -300.toFloat()

                val explosionSound = MediaPlayer.create(context, R.raw.explosion)
                explosionSound.start()
                score += 10
            }
        }
    }

    fun draw() {
        if (surfaceHolder.surface.isValid){
            canvas = surfaceHolder.lockCanvas()

            canvas?.drawColor(Color.BLACK)

            canvas?.drawBitmap(player.bitmap, player.x , player.y , paint)

            paint.color = Color.WHITE
            for (s in stars){
                paint.strokeWidth = s.starWidth
                canvas?.drawPoint(s.x.toFloat(),s.y.toFloat(),paint)
            }

            for (e in enemies){
                canvas?.drawBitmap(e.bitmap, e.x , e.y , paint)
            }

            canvas?.drawBitmap(boom.bitmap, boom.x , boom.y , paint)

            canvas?.drawText("Score:$score",10f, 200f, paintTxt)
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