package ipca.game.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player {

    var bitmap : Bitmap

    var x = 0f
    var y = 0f

    var speed = 0

    constructor(context: Context) {
        x = 70f
        y = 50f
        speed = 10
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
    }

    fun update() {
        x += speed
        y += speed
    }
}