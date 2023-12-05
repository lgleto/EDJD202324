package ipca.budget.customcontrol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class JogoDoGaloView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    var pointArrayList = arrayListOf<Point>()

    private var pointsChanged : ((Int) -> Unit)? = null
    fun setOnClickPointListener (callback :(Int) -> Unit){
        pointsChanged = callback
    }

    var isBlocked = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()

        paint.color = Color.BLACK
        paint.strokeWidth = 20.0f

        val hSpacing : Float = width / 3.0f
        val vSpacing : Float = height / 3.0f

        canvas.drawLine(hSpacing,0f,hSpacing,height.toFloat(), paint)
        canvas.drawLine(hSpacing * 2,0f,hSpacing * 2,height.toFloat(), paint)
        canvas.drawLine(0f,vSpacing,width.toFloat(),vSpacing, paint)
        canvas.drawLine(0f,vSpacing*2,width.toFloat(),vSpacing*2, paint)

        paint.color = Color.RED

        for ( p in pointArrayList){
            canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), hSpacing.toFloat(), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchY : Float = event?.y?:0f
        val touchX: Float = event?.x?:0f
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                if (!isBlocked) {
                    pointArrayList.add(Point(touchX.toInt(), touchY.toInt()))
                    pointsChanged?.invoke(pointArrayList.count())
                    invalidate()
                }
            }
            else-> return false
        }

        return true
    }
}