package ipca.game.spacefighter

import android.content.Context
import java.util.Random

class Star {

    var x = 0
    var y = 0
    var speed = 0
    var maxX = 0
    var maxY = 0

    val generator = Random()
    var starWidth : Float = 0.0f
        get() {
            return (generator.nextFloat()*4)+1f
        }

    constructor(context: Context, width : Int, height:Int) {
        maxX = width
        maxY = height


        y = generator.nextInt(maxY)
        x = generator.nextInt(maxX)

        speed = generator.nextInt(15)
    }

    fun update(playerSpeed:Int) {
        x -= playerSpeed
        x -= speed

        if (x < 0){
            x = maxX
            y = generator.nextInt(maxY)
            speed = generator.nextInt(15)
        }
    }

}