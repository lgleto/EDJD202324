package ipca.invaders.aXXXXX

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import java.util.Random

class Circle {

    var x = 0f
    var y = 0f

    var maxX = 0
    var maxY = 0

    lateinit var detectCollision : RectF


    var speedX = 0
    var speedY = 0

    val generator = Random()

    val circleDim = 100f

    var shouldBeRemoved = false
    constructor(context: Context, width : Int, height:Int , x :Float, y:Float) {
        this.x = x
        this.y = y

        maxY = height
        maxX = width

        detectCollision = RectF(x ,y, circleDim, circleDim)

        speedX = generator.nextInt(20) - 9
        speedY = generator.nextInt(20) - 9
    }

    fun update() {
        x += speedX
        y += speedY

        if (x < 0 ){
            speedX = -speedX
        }
        if (x > maxX ){
            speedX = -speedX
        }

        if (y < 0 ){
            speedY = -speedY
        }
        if (y > maxY ){
            speedY = -speedY
        }

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x+ circleDim
        detectCollision.bottom = y + circleDim
    }


}