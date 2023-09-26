package ipca.utility.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)

        button7.setOnClickListener {
            val num = (it as Button).text.toString()
            val display = textViewDisplay.text.toString()
            if (display == "0") {
                textViewDisplay.text = num
            }else{
                textViewDisplay.text = display + num
            }
        }

        button8.setOnClickListener {
            val num = (it as Button).text.toString()
            val display = textViewDisplay.text.toString()
            if (display == "0") {
                textViewDisplay.text = num
            }else{
                textViewDisplay.text = display + num
            }
        }


    }

}