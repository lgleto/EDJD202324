package ipca.game.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player {

    var bitmap : Bitmap

    var x = 0f
    var y = 0f

    var speed = 0
    var isBoosting = false
    final val GRAVITY = -10
    final val MAX_SPEED = 20
    final val MIN_SPEED = 1

    var maxX = 0
    var maxY = 0

    constructor(context: Context, width : Int, height:Int ) {
        x = 70f
        y = 50f
        speed = 10
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        maxY = height - bitmap.height

    }

    fun update() {
        if (isBoosting) speed += 1
        else speed -=2
        if (speed > MAX_SPEED) speed = MAX_SPEED
        else if (speed < MIN_SPEED) speed = MIN_SPEED


        y -= speed + GRAVITY

        if (y > maxY) y = maxY.toFloat()
        else if (y < 0)  y = 0f


    }
}