package ipca.utility.adder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editTextOp1 = EditText(this)
        val editTextOp2 = EditText(this)
        val linearLayout = LinearLayout(this)
        val button = Button(this)
        button.setText("Soma")
        val textViewDisplay = TextView(this)
        textViewDisplay.text = "0"

        linearLayout.addView(editTextOp1)
        linearLayout.addView(editTextOp2)
        linearLayout.addView(button)
        linearLayout.addView(textViewDisplay)

        button.setOnClickListener {

            val op1 = editTextOp1.text.toString().toInt()
            val op2 = editTextOp1.text.toString().toInt()

            val resultado = soma(op1,op2)

            textViewDisplay.text = resultado.toString()
        }

        setContentView(linearLayout)

    }

    fun soma(op1: Int, op2:Int) : Int {
        return op1 + op2
    }

}