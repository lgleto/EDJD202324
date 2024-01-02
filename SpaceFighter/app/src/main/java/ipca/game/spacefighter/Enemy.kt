package ipca.game.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

class Enemy {
    var bitmap : Bitmap

    var x = 0f
    var y = 0f
    var speed = 0
    var maxX = 0
    var maxY = 0

    val generator = Random()

    lateinit var detectCollision : Rect
    constructor(context: Context, width : Int, height:Int ) {
        maxY = height
        maxX = width

        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy)
        x = maxX.toFloat()
        y = (generator.nextInt(maxY - bitmap.height) ).toFloat()
        speed = generator.nextInt(6) + 10

        detectCollision = Rect(x.toInt() ,y.toInt(), bitmap.width, bitmap.height)
    }

    fun update(playerSpeed :Int) {
        x -= playerSpeed
        x -= speed

        if (x < (0 - bitmap.width)){
            x = maxX.toFloat()
            y = (generator.nextInt(maxY - bitmap.height) ).toFloat()
            speed = generator.nextInt(6) + 10
        }

        detectCollision.left = x.toInt()
        detectCollision.top = y.toInt()
        detectCollision.right = x.toInt() + bitmap.width
        detectCollision.bottom = y.toInt() + bitmap.height
    }

}