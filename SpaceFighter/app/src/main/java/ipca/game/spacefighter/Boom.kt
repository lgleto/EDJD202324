package ipca.game.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Boom {

    var x = -300f
    var y = -300f

    lateinit var bitmap: Bitmap
    constructor(context: Context, width:Int, height: Int){
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.boom)
    }

}