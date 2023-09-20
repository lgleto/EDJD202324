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
        setContentView(R.layout.activity_main)

        val editTextOp1 = findViewById<EditText>(R.id.editTextOp1)
        val editTextOp2 = findViewById<EditText>(R.id.editTextOp2)
        val buttonSoma = findViewById<Button>(R.id.buttonSoma)
        val textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        buttonSoma.setOnClickListener {
            val op1 = editTextOp1.text.toString().toInt()
            val op2 = editTextOp2.text.toString().toInt()
            val result = soma(op1,op2)
            textViewDisplay.text = result.toString()
        }
    }

    fun soma(op1: Int, op2:Int) : Int {
        return op1 + op2
    }
}